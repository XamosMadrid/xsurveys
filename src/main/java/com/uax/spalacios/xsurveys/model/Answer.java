package com.uax.spalacios.xsurveys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "answers")
@Data
public class Answer {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(name = "id_evaluation")
  private Long idEvaluation;

  @Column(name = "id_question")
  private Long idQuestion;

  @Column(name = "id_question_option")
  private Long idQuestionOption;
}
