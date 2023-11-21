package com.uax.spalacios.xsurveys.repositories;

import com.uax.spalacios.xsurveys.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

  Survey findByPermalink(String permalink);

}
