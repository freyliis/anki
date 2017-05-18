package org.freyliis.anki.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.reader.InputReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class JsonReader implements InputReader {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Path pathToFile;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public JsonReader(String pathToFile) throws GameException {
        this.pathToFile = getPathIfFileIsCorrect(pathToFile);
    }

    private Path getPathIfFileIsCorrect(String filePath) throws GameException {
        if (filePath == null || !Files.exists(Paths.get(filePath))) {
            throw new GameException("File path cannot be null/empty and file has to exist. Wrong path file: " + filePath);
        }
        return Paths.get(filePath);
    }

    public Optional<Deck> readDeck() throws GameException {
        try {
            return Optional.of(objectMapper.readValue(pathToFile.toFile(), Deck.class));
        } catch (IOException e) {
            throw new GameException(e);
        }
    }
}
