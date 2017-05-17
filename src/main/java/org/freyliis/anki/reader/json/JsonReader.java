package org.freyliis.anki.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.model.Deck;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JsonReader {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String pathToFile;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public JsonReader(String pathToFile) {
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        this.pathToFile = pathToFile;
    }

    public Optional<Deck> readDeck() {
        try {
            return Optional.of(objectMapper.readValue(new File(pathToFile), Deck.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
