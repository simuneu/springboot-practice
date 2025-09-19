package spring.spring_boot_security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import spring.spring_boot_security.security.JwtAuthenticationFilter;

import java.util.Arrays;

@Slf4j
@Configuration //스프링 컨테이너에게 해당 클래스가 Bean 정의를 포함한 설정 클래스임을 알림
@EnableWebSecurity //spring security 활성화
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()) //cors기본 설정
                .csrf(CsrfConfigurer::disable) //csrf disable
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //session기반이 아니므로 무상태(stateless설정)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/auth/**")
                        .permitAll() // /, /api/auth/**경로는 인증 안 헤도 됨
                        .anyRequest().authenticated()); // 그 이외의 모든 결로는 인증 해야 함

        //filter등록 : 매 요청마다 CrosFilter를 실행한 후에 JwtAuthenticationFilter 실행
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        return http.build();
    }

    //cors 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        //모든 출처나 메소드, 헤더에 대해 허용하는 cors설정
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH"));
        config.setAllowedHeaders((Arrays.asList("*")));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
