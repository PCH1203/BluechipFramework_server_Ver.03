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

    /**
     * WebSecurity가 HttpSecurity 보다 우선 순위를 갖는다.
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/html","/framework/**");
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용 권한 체크
//                .antMatchers("/framework/api/user/**").authenticated()
//                .antMatchers("http://localhost:3000/management/**").authenticated()
                //해당 URL에 대한 토큰 검증을 필요로 한다.
//                .antMatchers("/framework/api/util/**").authenticated()
                .antMatchers("/framework/**").permitAll()
//                .antMatchers("/**").permitAll()
                        .and()
                                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                                        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                                        UsernamePasswordAuthenticationFilter.class);
//                                        .sessionManagement()
//                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                            .maximumSessions(1);
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1);

        http.formLogin()
                .disable();

        http.logout()
                .disable();

//        http.exceptionHandling()
//                .accessDeniedPage("/denied");
    }


}
