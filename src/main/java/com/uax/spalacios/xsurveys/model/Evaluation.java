package com.uax.spalacios.xsurveys.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "evaluations")
@Data
public class Evaluation {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "id_survey")
  private Long idSurvey;

  @Column(name = "permalink")
  private String permalink;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private EvaluationStatus status;

  @Column(name = "name_filler")
  private String nameFiller;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="id_evaluation")
  private List<Answer> answers;
}
