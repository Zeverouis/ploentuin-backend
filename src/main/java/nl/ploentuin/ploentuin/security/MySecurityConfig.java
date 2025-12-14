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

                        .requestMatchers(HttpMethod.GET, "/info/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planners/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/planners/**").permitAll()
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/users/forgot-password").permitAll()

                        .requestMatchers("/admins/**").hasRole("ADMIN")

                        .requestMatchers("/users/reset-password").authenticated()
                        .requestMatchers("/users/email/change").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/forums/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/forums/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/forums/**").authenticated()
                        .requestMatchers("/planners/**/claim").authenticated()
                        .requestMatchers("/planners/save").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/planners/**").authenticated()


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                ));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}