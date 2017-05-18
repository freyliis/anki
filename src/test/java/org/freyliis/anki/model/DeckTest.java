package org.freyliis.anki.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class DeckTest {

    Question questionRed = new Question("question1", "answer1");
    Question questionOrange = new Question("question2", "answer2");
    Question questionGreen = new Question("question3", "answer3");
    List<Question> questions = new ArrayList<>();

    @Before
    public void setUp() {
        questionOrange.setBox(Box.ORANGE);
        questionGreen.setBox(Box.GREEN);
        questions = new ArrayList<>();
    }

    @Test
    public void shouldReturnOnlyRedQuestionsInQuestionsToAnswer() {
        questions.add(questionRed);
        questions.add(questionOrange);
        questions.add(questionGreen);
        Deck objectUnderTest = new Deck(LocalDate.now(), questions);
        List<Question> result = objectUnderTest.getQuestionsToAnswerToday();
        assertThat(result.size(), is(1));
        assertFalse(result.contains(questionOrange));
        assertTrue(result.contains(questionRed));
        assertFalse(result.contains(questionGreen));
    }

    @Test
    public void shouldReturnTrueWhenAllQuestionsArePropelyAnswered() {
        questions.add(questionGreen);
        Deck objectUnderTest = new Deck(LocalDate.now(), questions);
        boolean result = objectUnderTest.areAllQuestionsProperlyAnswered();
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenAllQuestionsArePropelyAnswered() {
        questions.add(questionOrange);
        questions.add(questionGreen);
        Deck objectUnderTest = new Deck(LocalDate.now(), questions);
        boolean result = objectUnderTest.areAllQuestionsProperlyAnswered();
        assertFalse(result);
    }

    @Test
    public void shouldReturnEmptyList() {
        questions.add(questionGreen);
        Deck objectUnderTest = new Deck(LocalDate.now(), questions);
        List<Question> result = objectUnderTest.getQuestionsToAnswerToday();
        assertThat(result.size(), is(0));
    }

}