package org.freyliis.anki.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuestionTest {


    Question questionRed = new Question("question1", "answer1 answer2");
    Question questionOrange = new Question("question2", "answer1 answer2");
    Question questionGreen = new Question("question3", "answer3");

    @Before
    public void setUp() {
        questionOrange.setBox(Box.ORANGE);
        questionGreen.setBox(Box.GREEN);
        questionRed.setBox(Box.RED);
    }

    @Test
    public void shouldReturnTrueForRedAndFalseForOrangeAndGreenQuestionsToBeAnswered() {
        assertFalse(questionOrange.shouldBeAnswered());
        assertTrue(questionRed.shouldBeAnswered());
        assertFalse(questionGreen.shouldBeAnswered());
    }

    @Test
    public void shouldChangeBoxOfQuestionToOrangeDueToBePartiallyAnswered() {
        questionRed.answerQuestion("answer1");
        assertThat(questionRed.getBox(), is(Box.ORANGE));
    }

    @Test
    public void shouldChangeBoxOfQuestionToRedDueToNoAnswer() {
        questionOrange.answerQuestion("no answer");
        assertThat(questionOrange.getBox(), is(Box.RED));
    }

    @Test
    public void shouldChangeBoxOfQuestionTGreenDueToProperAnswer() {
        questionRed.answerQuestion(questionRed.getAnswer());
        assertThat(questionRed.getBox(), is(Box.GREEN));
    }
}