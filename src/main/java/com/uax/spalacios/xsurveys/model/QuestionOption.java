package com.uax.spalacios.xsurveys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "question_options")
@Data
public class QuestionOption {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "id_question")
  private Long idQuestion;

  @Column(name = "content")
  private String content;

  @Transient
  private Integer totalAnswered;

  @Transient
  private String totalAnsweredPercent;

}
