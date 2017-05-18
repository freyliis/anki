package org.freyliis.anki.session.stream;

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
    public void shouldPrintQuestion() throws IOException {
        objectUnderTest.printQuestion("question");
        verify(outputStream, times(1)).write(any(byte[].class), anyInt(), anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnExceptionDuringPrintQuestion() throws IOException {
        doThrow(IOException.class).when(outputStream).write(any(byte[].class), anyInt(), anyInt());
        objectUnderTest.printQuestion("question");
    }

    @Test
    public void shouldReadAnswer() throws IOException {
        String answer = "answer";
        objectUnderTest = new StreamSession(new ByteArrayInputStream(answer.getBytes()), outputStream);
        String result = objectUnderTest.readAnswer();
        assertThat(result, is(answer));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnExceptionDuringReadAnswer() throws IOException {
        doThrow(IOException.class).when(inputStream).read(any(byte[].class), anyInt(), anyInt());
        objectUnderTest.readAnswer();
    }

    @Test
    public void shouldCloseStreams() throws IOException {
        objectUnderTest.endSession();
        verify(outputStream, times(1)).close();
        verify(inputStream, times(1)).close();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnExceptionDuringClosingStream() throws IOException {
        doThrow(IOException.class).when(inputStream).close();
        objectUnderTest.endSession();
    }
}