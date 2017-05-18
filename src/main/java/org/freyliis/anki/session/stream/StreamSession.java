package org.freyliis.anki.session.stream;

import org.freyliis.anki.game.GameException;
import org.freyliis.anki.session.GameSession;

import java.io.*;

public class StreamSession implements GameSession {

    static final String CONGRATULATIONS = "Congratulations!!";
    static final String GOODBYE = "Goodbye!!";
    static final String PROPER_ANSWER_TEXT = "Proper answer is: ";
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public StreamSession(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void printQuestion(String question) throws GameException {
        print(question);
    }

    public void printAnswer(String answer) throws GameException {
        print(PROPER_ANSWER_TEXT + answer);
    }

    private void print(String text) throws GameException {
        try {
            bufferedWriter.write(text);
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new GameException(e);
        }
    }

    public String readAnswer() throws GameException {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new GameException(e);
        }
    }

    public void endSession() throws GameException {
        try {
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new GameException(e);
        }
    }

    public void printCongrats() throws GameException {
        print(CONGRATULATIONS);
    }

    public void printGoodbye() throws GameException {
        print(GOODBYE);
    }
}
