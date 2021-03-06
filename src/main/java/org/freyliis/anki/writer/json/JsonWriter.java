package org.freyliis.anki.writer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.writer.OutputWriter;

import java.io.File;
import java.io.IOException;

public class JsonWriter implements OutputWriter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String output;

    public JsonWriter(String output) {
        this.output = output;
    }

    public void saveDeck(Deck deck) throws GameException {
        if (output == null || output.isEmpty()) {
            throw new GameException("Output cannot be null.");
        }
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), deck);
        } catch (IOException e) {
            throw new GameException(e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
