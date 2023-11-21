package com.uax.spalacios.xsurveys.controllers;

import com.uax.spalacios.xsurveys.model.Evaluation;
import com.uax.spalacios.xsurveys.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EvaluationController {

  private final EvaluationService evaluationService;

  @GetMapping("/evaluations/start/{surveyPermalink}")
  public String startEvaluation(@PathVariable(value = "surveyPermalink") String surveyPermalink) {
    Evaluation newEvaluation = evaluationService.startEvaluation(surveyPermalink);
    if (newEvaluation == null) {
      return "evaluationNotOpen";
    }

    return "redirect:/evaluation/" + newEvaluation.getPermalink();
  }

  @GetMapping("/evaluation/{permalink}")
  public String showEvaluation(@PathVariable(value = "permalink") String permalink, Model model) {
    evaluationService.fillModelWithEvaluation(permalink, model);
    return "evaluation";
  }

  @PostMapping("/completeEvaluation")
  public String completeEvaluation(@ModelAttribute("evaluation") Evaluation evaluation) {
    evaluationService.complete(evaluation);
    return "redirect:/evaluation/" + evaluation.getPermalink();
  }
}
