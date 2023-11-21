package com.uax.spalacios.xsurveys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.uax.spalacios.xsurveys.model.Answer;
import com.uax.spalacios.xsurveys.model.Evaluation;
import com.uax.spalacios.xsurveys.model.EvaluationStatus;
import com.uax.spalacios.xsurveys.model.Question;
import com.uax.spalacios.xsurveys.model.QuestionOption;
import com.uax.spalacios.xsurveys.model.Survey;
import com.uax.spalacios.xsurveys.model.SurveyStatus;
import com.uax.spalacios.xsurveys.services.EvaluationService;
import com.uax.spalacios.xsurveys.services.QuestionService;
import com.uax.spalacios.xsurveys.services.SurveyService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AppTests {
	@Autowired
	private SurveyService surveyService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private EvaluationService evaluationService;

	@Test
	public void whenCreatingASurvey_thenIsCreated() {
		createSurvey();

		List<Survey> surveys = surveyService.list();
		assertEquals(surveys.size(), 1);

		Survey surveySaved = surveys.get(0);
		assertEquals("Nueva encuesta", surveySaved.getName());
		assertEquals("Preferencias y gustos", surveySaved.getDescription());
		assertEquals(SurveyStatus.CREANDO, surveySaved.getStatus());
		assertNotNull(surveySaved.getPermalink());

		List<Question> questions = questionService.listQuestionsBySurvey(surveySaved.getId());
		assertEquals(2, questions.size());

		Question question1Saved = questions.get(0);
		assertEquals("¿Qué color te gusta más?", question1Saved.getStatement());
		assertEquals(1, question1Saved.getOrder());
		assertEquals(3, question1Saved.getOptions().size());
		assertEquals("Rojo", question1Saved.getOptions().get(0).getContent());
		assertEquals("Amarillo", question1Saved.getOptions().get(1).getContent());
		assertEquals("Verde", question1Saved.getOptions().get(2).getContent());

		Question question2Saved = questions.get(1);
		assertEquals("¿Qué música te gusta más?", question2Saved.getStatement());
		assertEquals(2, question2Saved.getOrder());
		assertEquals(3, question2Saved.getOptions().size());
		assertEquals("Rock", question2Saved.getOptions().get(0).getContent());
		assertEquals("Clásica", question2Saved.getOptions().get(1).getContent());
		assertEquals("Pop", question2Saved.getOptions().get(2).getContent());
	}

	private Survey createSurvey() {
		Survey survey = new Survey();
		survey.setName("Nueva encuesta");
		survey.setDescription("Preferencias y gustos");
		Survey surveySaved = surveyService.createSurvey(survey);


		Question question1 = new Question();
		question1.setStatement("¿Qué color te gusta más?");
		List<QuestionOption> options1 = new ArrayList<>();
		QuestionOption option1_1 = new QuestionOption();
		option1_1.setContent("Rojo");
		options1.add(option1_1);
		QuestionOption option1_2 = new QuestionOption();
		option1_2.setContent("Amarillo");
		options1.add(option1_2);
		QuestionOption option1_3 = new QuestionOption();
		option1_3.setContent("Verde");
		options1.add(option1_3);
		question1.setOptions(options1);
		question1.setOrder(1);
		question1.setIdSurvey(surveySaved.getId());
		questionService.createQuestion(question1);

		Question question2 = new Question();
		question2.setStatement("¿Qué música te gusta más?");
		List<QuestionOption> options2 = new ArrayList<>();
		QuestionOption option2_1 = new QuestionOption();
		option2_1.setContent("Rock");
		options2.add(option2_1);
		QuestionOption option2_2 = new QuestionOption();
		option2_2.setContent("Clásica");
		options2.add(option2_2);
		QuestionOption option2_3 = new QuestionOption();
		option2_3.setContent("Pop");
		options2.add(option2_3);
		question2.setOptions(options2);

		question2.setOrder(2);
		question2.setIdSurvey(surveySaved.getId());
		questionService.createQuestion(question2);

		return surveySaved;
	}

	@Test
	public void whenCreatingAnEvaluation_thenIsCreated() {
		Survey survey = createSurvey();

		Evaluation evaluation = new Evaluation();
		evaluation.setIdSurvey(survey.getId());
		evaluation.setNameFiller("Tester");

		List<Answer> answers = new ArrayList<>();

		List<Question> questions = questionService.listQuestionsBySurvey(survey.getId());
		Question question1 = questions.get(0);
		Answer answer1 = new Answer();
		answer1.setIdQuestion(question1.getId());
		answer1.setIdQuestionOption(question1.getOptions().get(1).getId()); // Amarillo
		answers.add(answer1);

		Question question2 = questions.get(1);
		Answer answer2 = new Answer();
		answer2.setIdQuestion(question2.getId());
		answer2.setIdQuestionOption(question2.getOptions().get(2).getId()); // Pop
		answers.add(answer2);

		evaluation.setAnswers(answers);

		evaluationService.createEvaluation(evaluation);

		List<Evaluation> evaluations = evaluationService.list();
		assertEquals(evaluations.size(), 1);

		Evaluation evaluationSaved = evaluations.get(0);
		assertEquals("Tester", evaluationSaved.getNameFiller());
		assertEquals(survey.getId(), evaluationSaved.getIdSurvey());
		assertEquals(EvaluationStatus.ABIERTO, evaluationSaved.getStatus());
		assertNotNull(evaluationSaved.getPermalink());

		List<Answer> answersSaved = evaluationSaved.getAnswers();
		assertEquals(answersSaved.size(), 2);

		Answer answer1Saved = answersSaved.get(0);
		assertEquals(evaluationSaved.getId(), answer1Saved.getIdEvaluation());
		assertEquals(question1.getId(), answer1Saved.getIdQuestion());
		assertEquals(question1.getOptions().get(1).getId(), answer1Saved.getIdQuestionOption());

		Answer answer2Saved = answersSaved.get(1);
		assertEquals(evaluationSaved.getId(), answer2Saved.getIdEvaluation());
		assertEquals(question2.getId(), answer2Saved.getIdQuestion());
		assertEquals(question2.getOptions().get(2).getId(), answer2Saved.getIdQuestionOption());
	}


}
