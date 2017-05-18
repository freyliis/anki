package org.freyliis.anki.session;

public interface GameSession {

    void printQuestion(String question);

    String readAnswer();

    void endSession();

    void printCongrats();

    void printGoodbye();

}
