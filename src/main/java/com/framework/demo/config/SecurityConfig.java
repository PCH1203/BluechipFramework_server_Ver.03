package com.framework.demo.config;

import com.framework.demo.jwt.JwtAuthenticationFilter;
import com.framework.demo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/html","/framework/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용 권한 체크
                .antMatchers("/v2/api/security/user/test").authenticated()
                .antMatchers("/framework/**").permitAll()
                .antMatchers("/**").permitAll()
                        .and()
                                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                                        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                                        UsernamePasswordAuthenticationFilter.class);
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin()
                .disable();

        http.logout()
                .disable();

//        http.exceptionHandling()
//                .accessDeniedPage("/denied");
    }


}
