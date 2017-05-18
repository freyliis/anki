package org.freyliis.anki.writer;

import org.freyliis.anki.game.GameException;
import org.freyliis.anki.model.Deck;

public interface OutputWriter {

    void saveDeck(Deck deck) throws GameException;
}
