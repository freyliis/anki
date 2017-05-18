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

    public void runGame() throws GameException {
        Optional<Deck> deckOptional = inputReader.readDeck();
        Deck deck = validateDeck(deckOptional);
        playGame(deck);
        sumUpGame(deck);
        endGame(deck);
        outputWriter.saveDeck(new Deck(LocalDate.now(), deck.getQuestions()));
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

    private void playGame(Deck deck) throws GameException {
        while (!deck.getQuestionsToAnswer().isEmpty()) {
            for (Question question : deck.getQuestionsToAnswer()) {
                streamSession.printQuestion(question.getQuestion());
                if(!question.answerQuestion(streamSession.readAnswer())) {
                    streamSession.printAnswer(question.getAnswer());
                }
            }
        }
    }

    private void sumUpGame(Deck deck) throws GameException {
        if (deck.areAllQuestionsProperlyAnswered()) {
            streamSession.printCongrats();
        }
        streamSession.printGoodbye();
        streamSession.endSession();
    }

    private Deck validateDeck(Optional<Deck> deckOptional) throws GameException {
        if (!deckOptional.isPresent()) {
            throw new GameException("No deck was read from input.");
        }
        Deck deck = deckOptional.get();
        if (deck.getDate().isEqual(LocalDate.now())) {
            throw new GameException("You already played game today.");
        }
        if (deck.getQuestions().isEmpty()) {
            throw new GameException("There are no questions to answer in the deck.");
        }
        return deck;
    }
}
