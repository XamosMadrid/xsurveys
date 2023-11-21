package com.uax.spalacios.xsurveys.services;

import com.uax.spalacios.xsurveys.model.Answer;
import com.uax.spalacios.xsurveys.model.Evaluation;
import com.uax.spalacios.xsurveys.model.EvaluationStatus;
import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.QuestionOption;
import com.uax.spalacios.xsurveys.model.Survey;
import com.uax.spalacios.xsurveys.model.SurveyStatus;
import com.uax.spalacios.xsurveys.repositories.EvaluationRepository;
import com.uax.spalacios.xsurveys.repositories.EvaluationRepository;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class EvaluationService {
  private final EvaluationRepository repository;
  private final SurveyService surveyService;
  private final QuestionService questionService;

  public Evaluation startEvaluation(String surveyPermalink) {
    Survey survey = surveyService.findByPermalink(surveyPermalink);
    if (survey.getStatus() != SurveyStatus.ABIERTO) {
      return null;
    }

    List<Question> questions = questionService.listQuestionsBySurvey(survey.getId());
    Evaluation evaluation = new Evaluation();
    evaluation.setIdSurvey(survey.getId());
    evaluation.setNameFiller("");
    List<Answer> answers = new LinkedList<>();
    for (Question question: questions) {
        Answer answer = new Answer();
        answer.setIdQuestion(question.getId());
        answers.add(answer);
    }
    evaluation.setAnswers(answers);

    Evaluation evaluationSaved = createEvaluation(evaluation);
    return evaluationSaved;
  }

  public List<Evaluation> list() {
    List<Evaluation> evaluations = repository.findAll();

    return evaluations;
  }

  public Evaluation createEvaluation(Evaluation evaluation) {
    evaluation.setStatus(EvaluationStatus.ABIERTO);
    evaluation.setPermalink(RandomStringUtils.random(50, true, true));
    return repository.save(evaluation);
  }

  public void fillModelWithEvaluation(String permalink, Model model) {
    Evaluation evaluation = repository.findByPermalink(permalink);
    surveyService.fillModelWithCompleteSurvey(evaluation.getIdSurvey(), model);
    model.addAttribute("evaluation", evaluation);
  }

  public Evaluation complete(Evaluation evaluation) {
    evaluation.setStatus(EvaluationStatus.CERRADO);
    return repository.save(evaluation);
  }
}
