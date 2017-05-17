package org.freyliis.anki.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameSessionTest {

    GameSession objectUnderTest = new GameSession();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void shouldPrintQuestion() {
        String question = "question";
        objectUnderTest.printQuestion(question);
        String result = outContent.toString();
        assertThat(result, is(question + System.lineSeparator()));
    }
//TODO
//    @Test
//    public void shouldReadAnswer() {
//        String answer = "answer";
//        String result = objectUnderTest.readAnswer();
//        assertThat(result, is(answer));
//    }

}