package TMDTBoBa.BoBaEcor.AppConfig.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests()
                .requestMatchers("/**","/resources/static/**","/admin/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
//                .requestMatchers("/demo/**").hasAnyAuthority("ROLE_MANAGER")
                .requestMatchers("/api/v1/payment/**").permitAll()
                .requestMatchers("/api/v1/otp/**").permitAll()
                .requestMatchers("/api/v1/private/addon/**").permitAll()
                .requestMatchers("/demo/**").permitAll()

                .anyRequest().authenticated();
//        http.exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            Map<String,String> er =new HashMap<>();
//                            er.put("timestamp", LocalDate.now().toString());
//                            er.put("status", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
//                            response.setContentType(APPLICATION_JSON_VALUE);
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            new ObjectMapper().writeValue(response.getOutputStream(),er);
//                        }
//                );
        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    response.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );
        http
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
