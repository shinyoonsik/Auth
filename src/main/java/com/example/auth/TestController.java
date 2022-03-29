package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {
  private Logger logger = LoggerFactory.getLogger(TestController.class);

  @GetMapping
  @ResponseBody
  public String test(){
    return "Hello Spring";
  }
}
