package org.freyliis.anki.session.stream;

import org.freyliis.anki.game.GameException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class StreamSessionTest {

    private InputStream inputStream = mock(InputStream.class);
    private OutputStream outputStream = mock(OutputStream.class);
    private StreamSession objectUnderTest = new StreamSession(inputStream, outputStream);


    @Test
    public void shouldPrintQuestion() throws IOException, GameException {
        objectUnderTest.printQuestion("question");
        verify(outputStream, times(1)).write(any(byte[].class), anyInt(), anyInt());
    }

    @Test
    public void shouldPrintAnswer() throws IOException, GameException {
        objectUnderTest.printAnswer("answer");
        verify(outputStream, times(1)).write(any(byte[].class), anyInt(), anyInt());
    }

    @Test(expected = GameException.class)
    public void shouldThrowAnExceptionDuringPrintQuestion() throws IOException, GameException {
        doThrow(IOException.class).when(outputStream).write(any(byte[].class), anyInt(), anyInt());
        objectUnderTest.printQuestion("question");
    }

    @Test
    public void shouldReadAnswer() throws IOException, GameException {
        String answer = "answer";
        objectUnderTest = new StreamSession(new ByteArrayInputStream(answer.getBytes()), outputStream);
        String result = objectUnderTest.readAnswer();
        assertThat(result, is(answer));
    }

    @Test(expected = GameException.class)
    public void shouldThrowAnExceptionDuringReadAnswer() throws IOException, GameException {
        doThrow(IOException.class).when(inputStream).read(any(byte[].class), anyInt(), anyInt());
        objectUnderTest.readAnswer();
    }

    @Test
    public void shouldCloseStreams() throws IOException, GameException {
        objectUnderTest.endSession();
        verify(outputStream, times(1)).close();
        verify(inputStream, times(1)).close();
    }

    @Test(expected = GameException.class)
    public void shouldThrowAnExceptionDuringClosingStream() throws IOException, GameException {
        doThrow(IOException.class).when(inputStream).close();
        objectUnderTest.endSession();
    }
}