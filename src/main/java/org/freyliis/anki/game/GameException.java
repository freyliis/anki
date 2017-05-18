package org.freyliis.anki.game;

public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }

    public GameException(Exception e) {
        super(e);
    }
}
