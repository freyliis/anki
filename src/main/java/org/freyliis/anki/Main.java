package org.freyliis.anki;

import org.freyliis.anki.game.AnkiGame;
import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.session.GameSession;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.json.JsonWriter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static String defaultDeckPath = "defaultDeck.json";

    public static void main(String[] args) {
        if (args.length == 2) {
            setUpGame(args[0], args[1]);
        } else {
            setUpGame(defaultDeckPath, defaultDeckPath);
        }

    }

    private static void setUpGame(String inputPath, String outputPath) {
        GameSession gameSession = null;
        try {
            JsonReader jsonReader = new JsonReader(inputPath);
            JsonWriter jsonWriter = new JsonWriter(outputPath);
            gameSession = new StreamSession(System.in, System.out);
            AnkiGame ankiGame = new AnkiGame(gameSession);
            Optional<Deck> deckOptional = jsonReader.readDeck();
            if (deckOptional.isPresent()) {
                Deck afterGameDeck = ankiGame.runGame(LocalDate.now(), deckOptional.get());
                jsonWriter.saveDeck(afterGameDeck);
            }
        } catch (GameException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getLocalizedMessage());
        } finally {
            if (gameSession != null) {
                try {
                    gameSession.endSession();
                } catch (GameException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getLocalizedMessage());
                }
            }
        }
    }
}
