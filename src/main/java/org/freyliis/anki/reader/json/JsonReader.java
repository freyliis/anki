package org.freyliis.anki.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.model.Deck;

import java.io.IOException;
import java.util.Optional;

public class JsonReader {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Deck> readDeck(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        try {
            return Optional.of(objectMapper.readValue(input, Deck.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
