package com.rigapi.web.controller;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/docs")
public class SwaggerController {

  @GetMapping(headers = ACCEPT + "=" + TEXT_HTML_VALUE)
  public String html() {
    return "redirect:/swagger-ui.html";
  }

  @GetMapping
  public String json() {
    return "redirect:/docs/json";
  }
}
