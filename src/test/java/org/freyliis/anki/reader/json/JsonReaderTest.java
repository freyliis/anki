package org.freyliis.anki.reader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.freyliis.anki.model.Deck;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.time.LocalDate;
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
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        LocalDate date = LocalDate.now();
        String input = objectMapper.writeValueAsString(new Deck(date));
        System.out.println(input);
        Optional<Deck> result = objectUnderTest.readDeck(input);
        assertThat(result.get().getDate(), is(date));
    }
}
