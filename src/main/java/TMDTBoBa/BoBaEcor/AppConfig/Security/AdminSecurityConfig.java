//package TMDTBoBa.BoBaEcor.AppConfig.Security;
//
//import TMDTBoBa.BoBaEcor.Service.User.CustomUserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class AdminSecurityConfig {
//    @Bean
//    CustomUserDetailsService customUserDetailsService(){
//        return new CustomUserDetailsService();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(this.customUserDetailsService());
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//
//        return daoAuthenticationProvider;
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().disable();
//        http.csrf().disable();
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//                .authorizeHttpRequests()
//                .anyRequest().hasRole("ADMIN")
//                .and().formLogin().loginPage("/admin/dang-nhap").loginProcessingUrl("/admin/login").failureUrl("/admin/dang-nhap?error=true").permitAll()
//                .and().logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/admin")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID"))
////                .authorizeHttpRequests()
////                .requestMatchers("/api/v1/auth/**").permitAll()
////                .requestMatchers("/api/v1/payment/**").permitAll()
////                .requestMatchers("/api/v1/cart/**").permitAll()
////                .requestMatchers("/api/v1/otp/**").permitAll()
////                .requestMatchers("/api/v1/private/addon/**").permitAll()
////                .requestMatchers("/demo/**").permitAll()
////                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
////        http.exceptionHandling()
////                .authenticationEntryPoint(
////                        (request, response, ex) -> {
////                            Map<String,String> er =new HashMap<>();
////                            er.put("timestamp", LocalDate.now().toString());
////                            er.put("status", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
////                            response.setContentType(APPLICATION_JSON_VALUE);
////                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////                            new ObjectMapper().writeValue(response.getOutputStream(),er);
////                        }
//////                );
////        http.exceptionHandling()
////                .authenticationEntryPoint(
////                        (request, response, ex) -> {
////                            response.sendError(
////                                    response.SC_UNAUTHORIZED,
////                                    ex.getMessage()
////                            );
////                        }
////                );
////        http
////                .authenticationProvider(authenticationProvider)
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
