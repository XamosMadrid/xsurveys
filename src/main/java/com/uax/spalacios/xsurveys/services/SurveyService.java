package com.uax.spalacios.xsurveys.services;

import com.uax.spalacios.xsurveys.model.Answer;
import com.uax.spalacios.xsurveys.model.Evaluation;
import com.uax.spalacios.xsurveys.model.EvaluationStatus;
import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.QuestionOption;
import com.uax.spalacios.xsurveys.model.Survey;
import com.uax.spalacios.xsurveys.model.SurveyStatus;
import com.uax.spalacios.xsurveys.repositories.EvaluationRepository;
import com.uax.spalacios.xsurveys.repositories.SurveyRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
@RequiredArgsConstructor
public class SurveyService {
  private static final DecimalFormat df = new DecimalFormat("0.00");

  private final SurveyRepository repository;
  private final QuestionService questionService;
  private final EvaluationRepository evaluationRepository;

  public List<Survey> list() {
    return repository.findAll();
  }

  public Survey createSurvey(Survey survey) {
    survey.setStatus(SurveyStatus.CREANDO);
    survey.setPermalink(RandomStringUtils.random(50, true, true));
    return updateSurvey(survey);
  }

  public Survey updateSurvey(Survey survey) {
    return repository.save(survey);
  }

  public Survey getSurveyById(Long id) {
    return repository.findById(id).orElseThrow();
  }

  public void calculateResults(Survey survey, List<Question> questions) {
    List<Evaluation> evaluations = evaluationRepository.findByIdSurvey(survey.getId());
    int totalEvaluations = 0;
    Map<Long, Integer> totals = new HashMap<>();
    for (Evaluation evaluation : evaluations) {
      if (evaluation.getStatus() == EvaluationStatus.CERRADO) {
        totalEvaluations++;
        for (Answer answer : evaluation.getAnswers()) {
          Integer total = totals.get(answer.getIdQuestionOption());
          if (total == null) {
            total = 0;
          }
          total += 1;
          totals.put(answer.getIdQuestionOption(), total);
        }
      }
    }
    survey.setTotalEvaluations(totalEvaluations);

    for (Question question: questions) {
      for (QuestionOption option: question.getOptions()) {
        Integer total = totals.get(option.getId());
        if (total == null) {
          total = 0;
        }
        if (totalEvaluations == 0) {
          option.setTotalAnsweredPercent("0 %");
          option.setTotalAnswered(0);
        } else {
          option.setTotalAnsweredPercent(df.format((total * 100) / totalEvaluations) + " %");
          option.setTotalAnswered(total);
        }
      }
    }
  }

  public Survey calculateResults(Long id, Model model) {
    Survey survey = repository.findById(id).orElseThrow();
    List<Question> questions = questionService.listQuestionsBySurvey(id);

    calculateResults(survey, questions);

    model.addAttribute("survey", survey);
    model.addAttribute("questions", questions);
    return survey;
  }

  public void deleteSurvey(Long id) {
    List<Evaluation> evaluations = evaluationRepository.findByIdSurvey(id);
    for (Evaluation evaluation : evaluations) {
      evaluationRepository.delete(evaluation);
    }
    List<Question> questions = questionService.listQuestionsBySurvey(id);
    for (Question question : questions) {
      questionService.deleteQuestion(question.getId());
    }
    repository.deleteById(id);
  }

  public void fillModelWithCompleteSurvey(Long id, Model model) {
    Survey survey = getSurveyById(id);
    List<Question> questions = questionService.listQuestionsBySurvey(survey.getId());
    model.addAttribute("survey", survey);
    model.addAttribute("questions", questions);
    model.addAttribute("evaluationsLink", getURLForStartingEvaluation(survey.getPermalink()));
  }

  public Survey findByPermalink(String permalink) {
    return repository.findByPermalink(permalink);
  }

  public static String getURLForStartingEvaluation(String permalink) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    String base = "";
    if (requestAttributes instanceof ServletRequestAttributes) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      request.getContextPath();
      base = request.getScheme() + "://" + request.getServerName();
      if (request.getServerPort() != 80) {
        base += ":" + request.getServerPort();
      }
    }

    return base + "/evaluations/start/" + permalink;
  }
}
