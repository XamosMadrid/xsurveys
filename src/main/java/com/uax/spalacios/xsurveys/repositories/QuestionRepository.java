package com.uax.spalacios.xsurveys.repositories;

import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  List<Question> findByIdSurveyOrderByOrder(Long idSurvey);
}
