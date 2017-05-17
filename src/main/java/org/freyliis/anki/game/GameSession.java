package org.freyliis.anki.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameSession {

    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public void printQuestion(String question) {
        System.out.println(question);
    }

    public String readAnswer() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void endSession() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void printCongrats() {
        System.out.println("Congratulations!!");
    }

    public void printGoodbye() {
        System.out.println("Goodbye!!");
    }
}
