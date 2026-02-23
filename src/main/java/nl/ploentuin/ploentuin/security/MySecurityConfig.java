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

                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/info/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/info/pages/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/forums/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/forums/categories/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/forums/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/forums/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/forums/**").authenticated()

                        .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/users/verify-email").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers("/users/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/public/**").permitAll()
                        .requestMatchers("/users/reset-password").permitAll()
                        .requestMatchers("users/profile/**").permitAll()
                        .requestMatchers("/users/*/change-password").authenticated()
                        .requestMatchers("/users/me").authenticated()
                        .requestMatchers("/users/email").authenticated()
                        .requestMatchers("/users/user/*").authenticated()
                        .requestMatchers("/users/*/role").hasRole("ADMIN")
                        .requestMatchers("/users/*/delete").hasRole("ADMIN")
                        .requestMatchers("/users/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/*").authenticated()

                        .requestMatchers(HttpMethod.GET, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/planner/**").permitAll()
                        .requestMatchers("/planner/*/claim").authenticated()
                        .requestMatchers("/planner/save").authenticated()
                        .requestMatchers(HttpMethod.POST, "/planner/catalog").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/planner/catalog").hasRole("ADMIN")



                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                ));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}