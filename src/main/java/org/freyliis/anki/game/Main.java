package org.freyliis.anki.game;

import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.writer.json.JsonWriter;

public class Main {

    public static void main(String[] args) {
        if (args.length == 2) {
            JsonReader jsonReader = new JsonReader(args[0]);
            JsonWriter jsonWriter = new JsonWriter(args[1]);
            ConsoleGameSession consoleGameSession = new ConsoleGameSession();
            AnkiGame ankiGame = new AnkiGame(consoleGameSession, jsonReader, jsonWriter);
            ankiGame.runGame();
        }
    }
}
