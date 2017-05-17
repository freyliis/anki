package org.freyliis.anki.writer.json;

import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonWriterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldThrowAnExceptionIfInputIsNull() {
        JsonWriter objectUnderTest = new JsonWriter(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Output cannot be null.");
        objectUnderTest.writeDeck(null);
    }

    @Test
    public void shouldThrowAnExceptionIfInputIsEmpty() {
        JsonWriter objectUnderTest = new JsonWriter("");
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Output cannot be null.");
        objectUnderTest.writeDeck(null);
    }

    @Test
    public void shouldWriteDeck() throws IOException {
        File fileToSave = temporaryFolder.newFile();
        LocalDate date = LocalDate.now();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("question1", "answer1"));
        Deck deck = new Deck(date, questions);
        JsonWriter objectUnderTest = new JsonWriter(fileToSave.getAbsolutePath());

        objectUnderTest.writeDeck(deck);

        List<String> elements = Files.readAllLines(fileToSave.toPath());
        Deck result = objectUnderTest.getObjectMapper().readValue(String.join(" ", elements), Deck.class);
        assertThat(result.getDate(), is(deck.getDate()));
    }

}