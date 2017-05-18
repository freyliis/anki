package org.freyliis.anki;

import org.freyliis.anki.game.AnkiGame;
import org.freyliis.anki.game.GameException;
import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.json.JsonWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static String defaultDeckPath = "defaultDeck.json";

    public static void main(String[] args) {
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
            StreamSession streamSession = new StreamSession(System.in, System.out);
            AnkiGame ankiGame = new AnkiGame(streamSession, jsonReader, jsonWriter);
            ankiGame.runGame();
        } catch (GameException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
}
