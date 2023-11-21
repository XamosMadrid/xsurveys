package com.uax.spalacios.xsurveys.controllers;

import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.Survey;
import com.uax.spalacios.xsurveys.model.SurveyStatus;
import com.uax.spalacios.xsurveys.services.QuestionService;
import com.uax.spalacios.xsurveys.services.SurveyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SurveyController {
  private final SurveyService surveyService;
  private final QuestionService questionService;

  @GetMapping("/addNewSurvey")
  public String addNewSurvey(Model model) {
    Survey survey = new Survey();
    model.addAttribute("survey", survey);
    return "newSurvey";
  }

  @PostMapping("/saveSurvey")
  public String saveSurvey(@ModelAttribute("survey") Survey survey) {
    Survey surveySaved;
    if (survey.getId() == null) {
      surveySaved = surveyService.createSurvey(survey);
    } else {
      surveySaved = surveyService.updateSurvey(survey);
    }
    return "redirect:/showFormUpdateSurvey/" + surveySaved.getId();
  }

  @GetMapping("/showFormUpdateSurvey/{id}")
  public String updateForm(@PathVariable(value = "id") long id, Model model) {
    surveyService.fillModelWithCompleteSurvey(id, model);
    return "updateSurvey";
  }

  @GetMapping("/previewSurvey/{id}")
  public String previewSurvey(@PathVariable(value = "id") long id, Model model) {
    surveyService.fillModelWithCompleteSurvey(id, model);
    return "previewSurvey";
  }

  @GetMapping("/showResultsSurvey/{id}")
  public String showResults(@PathVariable(value = "id") long id, Model model) {
    surveyService.calculateResults(id, model);
    return "results";
  }

  @GetMapping("/deleteSurvey/{id}")
  public String deleteThroughId(@PathVariable(value = "id") long id) {
    surveyService.deleteSurvey(id);
    return "redirect:/";

  }
}
