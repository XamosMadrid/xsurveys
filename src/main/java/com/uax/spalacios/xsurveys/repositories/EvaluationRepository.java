package com.uax.spalacios.xsurveys.repositories;

import com.uax.spalacios.xsurveys.model.Evaluation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

  Evaluation findByPermalink(String permalink);
  List<Evaluation> findByIdSurvey(Long idSurvey);

}
