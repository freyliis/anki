package org.freyliis.anki.game;

import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.json.JsonWriter;

public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        System.out.println(args[1]);
        if (args.length == 2) {

            JsonReader jsonReader = new JsonReader(args[0]);
            JsonWriter jsonWriter = new JsonWriter(args[1]);
            StreamSession streamSession = new StreamSession(System.in, System.out);
            AnkiGame ankiGame = new AnkiGame(streamSession, jsonReader, jsonWriter);
            ankiGame.runGame();
        }
    }
}
