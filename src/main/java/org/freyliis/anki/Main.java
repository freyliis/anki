package org.freyliis.anki;

import org.freyliis.anki.game.AnkiGame;
import org.freyliis.anki.game.GameException;
import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.session.GameSession;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.json.JsonWriter;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static String defaultDeckPath = "defaultDeck.json";

    public static void main(String[] args) {
        GameSession gameSession = null;
        try {
            JsonReader jsonReader;
            JsonWriter jsonWriter;
            if (args.length == 2) {
                jsonReader = new JsonReader(args[0]);
                jsonWriter = new JsonWriter(args[1]);
            } else {
                jsonReader = new JsonReader(defaultDeckPath);
                jsonWriter = new JsonWriter(defaultDeckPath);
            }
            gameSession = new StreamSession(System.in, System.out);
            AnkiGame ankiGame = new AnkiGame(gameSession, jsonReader, jsonWriter);
            ankiGame.runGame(LocalDate.now());
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
