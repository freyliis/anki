package org.freyliis.anki.reader;

import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;

import java.util.Optional;

public interface InputReader {

    public Optional<Deck> readDeck() throws GameException;
}
