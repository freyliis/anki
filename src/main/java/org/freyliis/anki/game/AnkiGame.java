package org.freyliis.anki.game;

import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.freyliis.anki.reader.json.JsonReader;
import org.freyliis.anki.writer.json.JsonWriter;

import java.time.LocalDate;
import java.util.Optional;

public class AnkiGame {

    private ConsoleGameSession consoleGameSession;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public AnkiGame(ConsoleGameSession consoleGameSession, JsonReader jsonReader, JsonWriter jsonWriter) {
        this.consoleGameSession = consoleGameSession;
        this.jsonReader = jsonReader;
        this.jsonWriter = jsonWriter;
    }

    public void runGame() {
        Optional<Deck> deckOptional = jsonReader.readDeck();
        validateGame(deckOptional);
        Deck deck = deckOptional.get();
        playGame(deck);
        jsonWriter.writeDeck(deck);
    }

    private void playGame(Deck deck) {
        for (Question question : deck.getQuestionsToAnswer()) {
            consoleGameSession.printQuestion(question.getQuestion());
            question.answerQuestion(consoleGameSession.readAnswer());
        }
        sumUpGame(deck);
    }

    private void sumUpGame(Deck deck) {
        if (deck.getQuestionsToAnswer().isEmpty()) {
            consoleGameSession.printCongrats();
        }
        consoleGameSession.printGoodbye();
        consoleGameSession.endSession();
    }

    private void validateGame(Optional<Deck> deck) {
        if (!deck.isPresent()) {
            throw new IllegalArgumentException("No deck was read from input.");
        }
        if (deck.get().getDate().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("You already played game today.");
        }
        if (deck.get().getQuestionsToAnswer().isEmpty()) {
            throw new IllegalArgumentException("There are no questions to answer in the deck.");
        }
    }
}
