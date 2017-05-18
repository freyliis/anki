package org.freyliis.anki.session.stream;

import org.freyliis.anki.session.GameSession;

import java.io.*;

public class StreamSession implements GameSession {

    static final String CONGRATULATIONS = "Congratulations!!";
    static final String GOODBYE = "Goodbye!!";
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public StreamSession(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }


    public void printQuestion(String question) {
        print(question);
    }

    private void print(String question) {
        try {
            bufferedWriter.write(question);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
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
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void printCongrats() {
        print(CONGRATULATIONS);
    }

    public void printGoodbye() {
        print(GOODBYE);
    }
}
