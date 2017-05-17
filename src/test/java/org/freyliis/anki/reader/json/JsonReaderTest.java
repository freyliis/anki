package org.freyliis.anki.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonReaderTest {

    JsonReader objectUnderTest = new JsonReader();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldThrowAnExceptionIfInputIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Input cannot be null.");
        objectUnderTest.readDeck(null);
    }

    @Test
    public void shouldThrowAnExceptionIfInputIsEmpty() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Input cannot be null.");
        objectUnderTest.readDeck("");
    }

    @Test
    public void shouldReadJsonObject() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDate date = LocalDate.now();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("question1", "answer1"));
        String input = objectMapper.writeValueAsString(new Deck(date, questions));
        Optional<Deck> result = objectUnderTest.readDeck(input);
        assertThat(result.get().getDate(), is(date));
    }
}
