package org.freyliis.anki.game;

import org.freyliis.anki.model.Box;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnkiGameTest {

    private AnkiGame objectUnderTest;
    private List<Question> questions;
    private GameSession gameSession = mock(GameSession.class);
    private Question question = new Question("question", "answer");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        objectUnderTest = new AnkiGame(gameSession);
        questions = new ArrayList<>();
        questions.add(question);
    }

    @Test
    public void shouldThrowAnExceptionDueToWrongDate() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You already played game today.");
        Deck deck = new Deck(LocalDate.now(), null);
        objectUnderTest.runGame(deck);
    }

    @Test
    public void shouldThrowAnExceptionDueToEmptyDeck() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("There are no questions to answer in the deck.");
        Deck deck = new Deck(LocalDate.now().minusDays(1), new ArrayList<>());
        objectUnderTest.runGame(deck);
    }

    @Test
    public void shouldAnswerTheQuestionPartially() {
        when(gameSession.readAnswer()).thenReturn("ans");
        Deck deck = new Deck(LocalDate.now().minusDays(1), questions);
        objectUnderTest.runGame(deck);
        assertTrue(deck.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.ORANGE));
    }

    @Test
    public void shouldAnswerTheQuestion() {
        when(gameSession.readAnswer()).thenReturn("answer");
        Deck deck = new Deck(LocalDate.now().minusDays(1), questions);
        objectUnderTest.runGame(deck);
        assertFalse(deck.getQuestionsToAnswer().contains(question));
    }

    @Test
    public void shouldNotAnswerTheQuestion() {
        when(gameSession.readAnswer()).thenReturn("dunno");
        Deck deck = new Deck(LocalDate.now().minusDays(1), questions);
        objectUnderTest.runGame(deck);
        assertTrue(deck.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.RED));
    }
}

