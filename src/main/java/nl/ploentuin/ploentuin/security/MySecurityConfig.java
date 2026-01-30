package nl.ploentuin.ploentuin.security;

import javax.sql.DataSource;
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

                        .requestMatchers(HttpMethod.GET, "/info/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/public/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planner/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planner/*").permitAll()
                        .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/users/verify-email").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers("/users/forgot-password").permitAll()

                        .requestMatchers(HttpMethod.GET, "/info/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/info/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/info/pages/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/info/pages/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/info/pages/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/planner/catalog").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/planner/catalog").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/planner/catalog/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planner").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planner/*/place").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planner/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planner/*/export/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/planner/**").permitAll()

                        .requestMatchers("/users/reset-password").authenticated()
                        .requestMatchers("/users/*/change-password").authenticated()
                        .requestMatchers("/users/email").authenticated()
                        .requestMatchers("/users/*/role").hasRole("ADMIN")
                        .requestMatchers("/users/*/delete").hasRole("ADMIN")
                        .requestMatchers("/users/user/*").authenticated()
                        .requestMatchers("/users/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/forums/*").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/forums/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/forums/*").authenticated()
                        .requestMatchers("/planner/*/claim").authenticated()
                        .requestMatchers("/planner/save").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/planner/*").authenticated()


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                ));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}