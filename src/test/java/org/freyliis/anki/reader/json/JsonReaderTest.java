package org.freyliis.anki.reader.json;

import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonReaderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldThrowAnExceptionIfInputIsNull() throws GameException {
        expectedException.expect(GameException.class);
        JsonReader objectUnderTest = new JsonReader(null);
    }

    @Test
    public void shouldReadJsonObject() throws IOException, GameException {
        File fileToSave = temporaryFolder.newFile();
        JsonReader objectUnderTest = new JsonReader(fileToSave.getAbsolutePath());
        LocalDate date = LocalDate.now();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("question1", "answer1"));
        objectUnderTest.getObjectMapper().writeValue(fileToSave, new Deck(date, questions));

        Optional<Deck> result = objectUnderTest.readDeck();

        assertThat(result.get().getDate(), is(date));
    }
}
