package org.freyliis.anki.game;

import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.session.GameSession;

import java.time.LocalDate;

public class AnkiGame {

    private GameSession gameSession;

    public AnkiGame(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public Deck runGame(LocalDate dayOfGame, Deck deck) throws GameException {
        validateDeck(deck);
        playGame(deck);
        sumUpGame(deck);
        endGame(deck);
        return new Deck(dayOfGame, deck.getQuestions());
    }

    private void endGame(Deck deck) {
        deck.getQuestions().stream().forEach(question -> question.moveToProperBox());
    }

    private void playGame(Deck deck) throws GameException {
        while (!deck.getQuestionsToAnswer().isEmpty()) {
            for (Question question : deck.getQuestionsToAnswer()) {
                gameSession.printQuestion(question.getQuestion());
                if (!question.answerQuestion(gameSession.readAnswer())) {
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

    private void validateDeck(Deck deck) throws GameException {
        if (deck.getDate().isEqual(LocalDate.now())) {
            throw new GameException("You already played game today.");
        }
        if (deck.getQuestions().isEmpty()) {
            throw new GameException("There are no questions to answer in the deck.");
        }
    }
}
