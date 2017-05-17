package org.freyliis.anki.writer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.model.Deck;

import java.io.File;
import java.io.IOException;

public class JsonWriter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String output;

    public JsonWriter(String output) {
        this.output = output;
    }

    public void writeDeck(Deck deck) {
        if (output == null || output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be null.");
        }
        try {
            objectMapper.writeValue(new File(output), deck);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
