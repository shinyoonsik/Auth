package com.example.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // Spring Security를 사용하겠다
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    * 어떤 요청이든 로그인 상관없이 누구나
//    http.authorizeRequests()
//        .anyRequest()
//        .permitAll();

//    * 어떤 요청에대해서든 사용자의 인증을 요구함
//    http.authorizeRequests()
//        .anyRequest()
//        .authenticated();

//    * 로그인하지 않은 사용자를 허용
//    http.authorizeRequests()
//        .anyRequest()
//        .anonymous();

      // "/home"으로 접근하는 경우 인증을 요하지 않으나 그 밖의 URL은 인증을 요함
      http.authorizeRequests()
          .antMatchers("/home/**", "/user/**") // URL의 패턴을 정함 => 응용 "/board/*/post/**"
          .anonymous()
          .anyRequest()
          .authenticated()
        .and()
          .formLogin() // 로그인 페이지가 생김. 인증을 요구하는 URL은 이쪽으로 넘어옴
          .loginPage("/user/login") // formLogin페이지가 렌더링될 요청URL
          .defaultSuccessUrl("/home")
          .permitAll()
        .and()
          .logout()
          .logoutUrl("/user/logout")
          .logoutSuccessUrl("/home")
          .deleteCookies("JSEESIONID")
          .invalidateHttpSession(true)
          .permitAll()
      ;
  }
}
