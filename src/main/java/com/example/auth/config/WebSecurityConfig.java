package com.example.auth.config;

import com.example.auth.infra.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // Spring Security를 사용하겠다
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;

  public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
    this.userDetailsService = customUserDetailsService;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    user정보를 inMemoryAuthentication()을 활용하여 만듬, DB:h2사용
//    auth.inMemoryAuthentication()
//        .withUser("user1")
//        .password(passwordEncoder().encode( "user1pass"))
//        .roles("USER")
//        .and()
//        .withUser("admin1")
//        .password(passwordEncoder().encode("admin1pass"))
//        .roles("ADMIN");

    // 개발자가 커스텀한 방식(다른DB를 사용하거나 다른 모듈을 사용하는 경우)대로 user정보를 저장하고 싶을때 사용
    auth.userDetailsService(this.userDetailsService);
  }

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
          .antMatchers("/home/**", "/user/signup/**") // URL의 패턴을 정함 => 응용 "/board/*/post/**"
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

//  @Bean
//  public PasswordEncoder passwordEncoder(){
//    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  }
}
