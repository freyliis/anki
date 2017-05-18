package org.freyliis.anki.game;

import org.freyliis.anki.model.Box;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.session.stream.StreamSession;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AnkiGameTest {

    private AnkiGame objectUnderTest;
    private List<Question> questions;
    private StreamSession streamSession = mock(StreamSession.class);
    private Question question = new Question("question", "answer");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        objectUnderTest = new AnkiGame(streamSession);
        questions = new ArrayList<>();
        questions.add(question);
    }

    @Test
    public void shouldThrowAnExceptionDueToWrongDate() throws GameException {
        expectedException.expect(GameException.class);
        expectedException.expectMessage("You already played game today.");
        Deck deck = new Deck(LocalDate.now(), null);
        objectUnderTest.runGame(LocalDate.now(), deck);
    }

    @Test
    public void shouldThrowAnExceptionDueToEmptyDeck() throws GameException {
        expectedException.expect(GameException.class);
        expectedException.expectMessage("There are no questions to answer in the deck.");
        Deck deck = new Deck(LocalDate.now().minusDays(1), new ArrayList<>());
        objectUnderTest.runGame(LocalDate.now(), deck);
    }

    @Test
    public void shouldNotAnswerTheQuestionAndThenAnswerProperly() throws GameException {
        when(streamSession.readAnswer()).thenReturn("dunno").thenReturn("answer");
        LocalDate now = LocalDate.now();
        Deck deck = new Deck(now.minusDays(1), questions);

        Deck result = objectUnderTest.runGame(now, deck);

        assertFalse(result.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.ORANGE));
        assertThat(result.getDate(), is(now));
        verify(streamSession, times(1)).printCongrats();
        verify(streamSession, times(1)).printGoodbye();
        verify(streamSession, times(1)).endSession();
    }

    @Test
    public void shouldNotAnswerTheQuestionAndThenAnswerPartially() throws GameException {
        when(streamSession.readAnswer()).thenReturn("dunno").thenReturn("ans");
        LocalDate now = LocalDate.now();
        Deck deck = new Deck(now.minusDays(1), questions);

        Deck result = objectUnderTest.runGame(now, deck);

        assertTrue(result.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.RED));
        assertThat(result.getDate(), is(now));
        verify(streamSession, times(0)).printCongrats();
        verify(streamSession, times(1)).printGoodbye();
        verify(streamSession, times(1)).endSession();
    }
}

