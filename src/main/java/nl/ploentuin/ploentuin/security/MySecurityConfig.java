package nl.ploentuin.ploentuin.security;

import javax.sql.DataSource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableMethodSecurity
public class MySecurityConfig {

    private final DataSource dataSource;
    private final JwtRequestFilter jwtRequestFilter;

    public MySecurityConfig(DataSource dataSource, JwtRequestFilter jwtRequestFilter) {
        this.dataSource = dataSource;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            connector.setMaxPartCount(200);
            connector.setMaxPostSize(200 * 1024 * 1024);
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, true FROM ploentuin_user WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, CONCAT('ROLE_', role) FROM ploentuin_user WHERE username=?")
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/info/categories/**", "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/info/categories/**", "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/info/categories/**", "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers("/users/*/role", "/users/*/delete", "/users/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/planner/catalog").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/planner/catalog").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/forums/categories/**", "/forums/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/forums/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/forums/**").authenticated()
                        .requestMatchers("/users/*/change-password", "/users/me", "/users/email", "/users/user/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/*").authenticated()
                        .requestMatchers("/planner/*/claim", "/planner/save").authenticated()

                        .requestMatchers("/users/register", "/users/verify-email", "/users/login", "/users/forgot-password", "/users/reset-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/public/**", "/users/profile/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/info/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/planner/catalog/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planner/*/export/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/planner/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}