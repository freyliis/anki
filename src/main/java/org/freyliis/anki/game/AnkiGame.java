package org.freyliis.anki.game;

import org.freyliis.anki.model.Box;
import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.reader.InputReader;
import org.freyliis.anki.session.GameSession;
import org.freyliis.anki.writer.OutputWriter;

import java.time.LocalDate;
import java.util.Optional;

public class AnkiGame {

    private GameSession gameSession;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    public AnkiGame(GameSession gameSession, InputReader inputReader, OutputWriter outputWriter) {
        this.gameSession = gameSession;
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void runGame(LocalDate dayOfGame) throws GameException {
        Optional<Deck> deckOptional = inputReader.readDeck();
        Deck deck = validateDeck(deckOptional);
        playGame(deck);
        sumUpGame(deck);
        endGame(deck);
        outputWriter.saveDeck(new Deck(dayOfGame, deck.getQuestions()));
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
                gameSession.printQuestion(question.getQuestion());
                if(!question.answerQuestion(gameSession.readAnswer())) {
                    gameSession.printAnswer(question.getAnswer());
                }
            }
        }
    }

    private void sumUpGame(Deck deck) throws GameException {
        if (deck.areAllQuestionsProperlyAnswered()) {
            gameSession.printCongrats();
        }
        gameSession.printGoodbye();
        gameSession.endSession();
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
