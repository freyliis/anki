package org.freyliis.anki.reader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public JsonReader(String pathToFile) {
        this.pathToFile = getPathIfFileIsCorrect(pathToFile);
    }

    private Path getPathIfFileIsCorrect(String filePath) {
        if (filePath == null || !Files.exists(Paths.get(filePath))) {
            throw new IllegalArgumentException("File path cannot be null/empty and file has to exist. Wrong path file: " + filePath);
        }
        return Paths.get(filePath);
    }

    public Optional<Deck> readDeck() {
        try {
            System.out.println(pathToFile.toAbsolutePath());
            return Optional.of(objectMapper.readValue(pathToFile.toFile(), Deck.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
