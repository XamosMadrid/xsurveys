CREATE TABLE surveys
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        varchar(100) NOT NULL,
    description TEXT NULL,
    permalink   varchar(100) NOT NULL,
    status      varchar(100) NOT NULL,
    CONSTRAINT surveys_PK PRIMARY KEY (id)
);


CREATE TABLE questions
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    `statement` TEXT   NOT NULL,
    `question_order`       BIGINT,
    id_survey   BIGINT,
    CONSTRAINT questions_PK PRIMARY KEY (id),
    CONSTRAINT questions_surveys_FK FOREIGN KEY (id_survey) REFERENCES surveys (id)
);


CREATE TABLE question_options
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    id_question BIGINT,
    content     TEXT NULL,
    CONSTRAINT question_options_PK PRIMARY KEY (id),
    CONSTRAINT options_questions_FK FOREIGN KEY (id_question) REFERENCES questions (id)
);



CREATE TABLE evaluations
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    id_survey   BIGINT,
    status      varchar(100) NOT NULL,
    permalink   varchar(100) NOT NULL,
    name_filler TEXT   NOT NULL,
    CONSTRAINT evaluation_PK PRIMARY KEY (id),
    CONSTRAINT evaluations_surveys_FK FOREIGN KEY (id_survey) REFERENCES surveys (id)

);


CREATE TABLE answers
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    id_evaluation      BIGINT,
    id_question        BIGINT,
    id_question_option BIGINT,
    CONSTRAINT answers_PK PRIMARY KEY (id),
    CONSTRAINT answers_evaluations_FK FOREIGN KEY (id_evaluation) REFERENCES evaluations (id),
    CONSTRAINT answers_questions_FK FOREIGN KEY (id_question) REFERENCES questions (id),
    CONSTRAINT answers_questions_options_FK FOREIGN KEY (id_question_option) REFERENCES question_options (id)
);
