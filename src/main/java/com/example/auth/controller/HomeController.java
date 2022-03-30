package com.example.auth.controller;

import com.example.auth.infra.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
  private final AuthenticationFacade authFacade;

  public HomeController(AuthenticationFacade authFacade) {
    this.authFacade = authFacade;
  }

  //  @GetMapping
//  public String home(Principal principal){
//    // 로그인한 user확인
//    try{
//      logger.info("connected user: {}", principal.getName());
//    } catch (NullPointerException e){
//      logger.info("no user logged in");
//    }
//    return "index";
//  }

//  @GetMapping
//  public String home(Authentication authentication){
//    // 로그인한 user확인
//    try{
//      logger.info("connected user: {}", authentication.getName());
//    } catch (NullPointerException e){
//      logger.info("no user logged in");
//    }
//    return "index";
//  }

  @GetMapping
  public String home(){
    // 로그인한 user확인
    try{
      // SecurityContextHolder는 자신이 실행되고 있는 스레드를 기준으로 현재의 context를 가져옴
      // 따라서, 동시에 여러개의 요청이 들어왔다하더라도 context에 동일한 user가 있을 수 없다. 병렬처리가 보장됨
      logger.info("connected user: {}", authFacade.getAuthentication().getName());
    } catch (NullPointerException e){
      logger.info("no user logged in");
    }
    return "index";
  }
}
