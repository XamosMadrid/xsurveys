package com.uax.spalacios.xsurveys.controllers;

import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.QuestionOption;
import com.uax.spalacios.xsurveys.services.QuestionService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("{idSurvey}/addNewQuestion")
  public String addNewQuestion(@PathVariable(value = "idSurvey") Long idSurvey, Model model) {
    Question question = new Question();
    question.setIdSurvey(idSurvey);
    question.setOrder(questionService.listQuestionsBySurvey(idSurvey).size() + 1);
    List<QuestionOption> options = new ArrayList<>();
    QuestionOption option1 = new QuestionOption();
    option1.setContent("Opción 1");
    options.add(option1);
    QuestionOption option2 = new QuestionOption();
    option2.setContent("Opción 2");
    options.add(option2);
    QuestionOption option3 = new QuestionOption();
    option3.setContent("Opción 3");
    options.add(option3);
    question.setOptions(options);
    model.addAttribute("question", question);
    return "newQuestion";
  }

  @PostMapping("/saveQuestion")
  public String saveQuestion(@ModelAttribute("question") Question question) {
    Question questionSaved;
    if (question.getId() == null) {
      questionSaved = questionService.createQuestion(question);
    } else {
      questionSaved = questionService.updateQuestion(question);
    }
    return "redirect:/showFormUpdateQuestion/" + questionSaved.getId();
  }

  @GetMapping("/showFormUpdateQuestion/{id}")
  public String updateForm(@PathVariable(value = "id") long id, Model model) {
    Question question = questionService.getQuestionById(id);
    model.addAttribute("question", question);
    return "updateQuestion";
  }

  @GetMapping("{idSurvey}/deleteQuestion/{id}")
  public String deleteThroughId(@PathVariable(value = "idSurvey") long idSurvey, @PathVariable(value = "id") long id) {
    questionService.deleteQuestion(id);
    return "redirect:/showFormUpdateSurvey/" + idSurvey;
  }
}
