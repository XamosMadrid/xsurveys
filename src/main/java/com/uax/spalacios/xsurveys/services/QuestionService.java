package com.uax.spalacios.xsurveys.services;

import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.QuestionOption;
import com.uax.spalacios.xsurveys.model.Survey;
import com.uax.spalacios.xsurveys.repositories.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository repository;

  public List<Question> list() {
    return repository.findAll();
  }

  public List<Question> listQuestionsBySurvey(Long idSurvey) {
    return repository.findByIdSurveyOrderByOrder(idSurvey);
  }

  public Question updateQuestion(Question question) {
    return repository.save(question);
  }

  public Question createQuestion(Question question) {
    return repository.save(question);
  }

  public Question getQuestionById(Long id) {
    return repository.findById(id).orElseThrow();
  }

  public void deleteQuestion(Long id) {
    repository.deleteById(id);
  }
}
