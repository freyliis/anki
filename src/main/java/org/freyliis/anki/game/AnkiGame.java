package org.freyliis.anki.game;

import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;

import java.time.LocalDate;

public class AnkiGame {

   private GameSession gameSession;

    public AnkiGame(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void runGame(Deck deck) {
        validateGame(deck);
        for (Question question : deck.getQuestionsToAnswer()) {
            gameSession.printQuestion(question.getQuestion());
            question.answerQuestion(gameSession.readAnswer());
        }
        sumUpGame(deck);
    }

    private void sumUpGame(Deck deck) {
        if(deck.getQuestionsToAnswer().isEmpty()) {
            gameSession.printCongrats();
        }
        gameSession.printGoodbye();
    }

    private void validateGame(Deck deck) {
        if (deck.getDate().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("You already played game today.");
        }
        if (deck.getQuestionsToAnswer().isEmpty()) {
            throw new IllegalArgumentException("There are no questions to answer in the deck.");
        }
    }
}
