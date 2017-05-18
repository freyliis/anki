package org.freyliis.anki.session;

import org.freyliis.anki.game.GameException;

public interface GameSession {

    void printQuestion(String question) throws GameException;

    void printAnswer(String answer) throws GameException;

    String readAnswer() throws GameException;

    void endSession() throws GameException;

    void printCongrats() throws GameException;

    void printGoodbye() throws GameException;

}
