package org.freyliis.anki.game;

import org.freyliis.anki.model.Box;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.reader.InputReader;
import org.freyliis.anki.session.stream.StreamSession;
import org.freyliis.anki.writer.OutputWriter;

import java.time.LocalDate;
import java.util.Optional;

public class AnkiGame {

    private StreamSession streamSession;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    public AnkiGame(StreamSession streamSession, InputReader inputReader, OutputWriter outputWriter) {
        this.streamSession = streamSession;
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void runGame() {
        Optional<Deck> deckOptional = inputReader.readDeck();
        Deck deck = validateDeck(deckOptional);
        playGame(deck);
        sumUpGame(deck);
        endGame(deck);
        outputWriter.saveDeck(deck);
    }

    private void endGame(Deck deck) {
        for (Question question : deck.getQuestions()) {
            moveToProperBox(question);
        }
    }

    private void moveToProperBox(Question question) {
        switch (question.getBox()) {
            case GREEN:
                question.setBox(Box.ORANGE);
                break;
            case ORANGE:
                question.setBox(Box.RED);
                break;
        }
    }

    private void playGame(Deck deck) {
        for (Question question : deck.getQuestionsToAnswerToday()) {
            streamSession.printQuestion(question.getQuestion());
            question.answerQuestion(streamSession.readAnswer());
        }
    }

    private void sumUpGame(Deck deck) {
        if (deck.areAllQuestionsProperlyAnswered()) {
            streamSession.printCongrats();
        }
        streamSession.printGoodbye();
        streamSession.endSession();
    }

    private Deck validateDeck(Optional<Deck> deckOptional) {
        if (!deckOptional.isPresent()) {
            throw new IllegalArgumentException("No deck was read from input.");
        }
        Deck deck = deckOptional.get();
        if (deck.getDate().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("You already played game today.");
        }
        if (deck.getQuestionsToAnswerToday().isEmpty()) {
            throw new IllegalArgumentException("There are no questions to answer in the deck.");
        }
        return deck;
    }
}
