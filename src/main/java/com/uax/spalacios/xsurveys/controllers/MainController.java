package com.uax.spalacios.xsurveys.controllers;

import com.uax.spalacios.xsurveys.services.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
  private final SurveyService surveyService;

  @GetMapping("/index")
  public String viewHomePage(Model model) {
    model.addAttribute("surveys", surveyService.list());
    return "index";
  }

  @GetMapping("/")
  public String viewHomePageRoot(Model model) {
    return viewHomePage(model);
  }
}
