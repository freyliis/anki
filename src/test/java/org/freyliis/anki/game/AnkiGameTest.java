package org.freyliis.anki.game;

import org.freyliis.anki.model.Box;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.json.JsonWriter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AnkiGameTest {

    private AnkiGame objectUnderTest;
    private List<Question> questions;
    private StreamSession streamSession = mock(StreamSession.class);
    JsonReader jsonReader = mock(JsonReader.class);
    JsonWriter jsonWriter = mock(JsonWriter.class);
    private Question question = new Question("question", "answer");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        objectUnderTest = new AnkiGame(streamSession, jsonReader, jsonWriter);
        questions = new ArrayList<>();
        questions.add(question);
    }

    @Test
    public void shouldThrowAnExceptionDueToWrongDate() throws GameException {
        expectedException.expect(GameException.class);
        expectedException.expectMessage("You already played game today.");
        Deck deck = new Deck(LocalDate.now(), null);
        when(jsonReader.readDeck()).thenReturn(Optional.of(deck));
        objectUnderTest.runGame();
    }

    @Test
    public void shouldThrowAnExceptionDueToEmptyDeck() throws GameException {
        expectedException.expect(GameException.class);
        expectedException.expectMessage("There are no questions to answer in the deck.");
        Deck deck = new Deck(LocalDate.now().minusDays(1), new ArrayList<>());
        when(jsonReader.readDeck()).thenReturn(Optional.of(deck));
        objectUnderTest.runGame();
    }

    @Test
    public void shouldThrowAnExceptionDueToNoProperDeckInFile() throws GameException {
        expectedException.expect(GameException.class);
        expectedException.expectMessage("No deck was read from input.");
        when(jsonReader.readDeck()).thenReturn(Optional.empty());
        objectUnderTest.runGame();
    }

    @Test
    public void shouldNotAnswerTheQuestionAndThenAnswerProperly() throws GameException {
        when(streamSession.readAnswer()).thenReturn("dunno").thenReturn("answer");
        Deck deck = new Deck(LocalDate.now().minusDays(1), questions);
        when(jsonReader.readDeck()).thenReturn(Optional.of(deck));

        objectUnderTest.runGame();

        assertFalse(deck.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.ORANGE));
        verify(streamSession, times(1)).printCongrats();
        verify(streamSession,times(1)).printGoodbye();
        verify(streamSession,times(1)).endSession();
    }

    @Test
    public void shouldNotAnswerTheQuestionAndThenAnswerPartially() throws GameException {
        when(streamSession.readAnswer()).thenReturn("dunno").thenReturn("ans");
        Deck deck = new Deck(LocalDate.now().minusDays(1), questions);
        when(jsonReader.readDeck()).thenReturn(Optional.of(deck));

        objectUnderTest.runGame();

        assertTrue(deck.getQuestionsToAnswer().contains(question));
        assertThat(question.getBox(), is(Box.RED));
        verify(streamSession, times(0)).printCongrats();
        verify(streamSession,times(1)).printGoodbye();
        verify(streamSession,times(1)).endSession();
    }
}

