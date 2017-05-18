package org.freyliis.anki.game;

import org.freyliis.anki.model.Deck;
import org.freyliis.anki.model.Question;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class IntegrationTest {


    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {
                        new Deck(LocalDate.now().minusDays(1), new ArrayList<Question>() {{
                            add(new Question("What enzyme breaks down sugars mouth and digestive tract?", "Amylase"));
                        }}),
                        "Amylase",
                        new Deck(LocalDate.now().minusDays(1), new ArrayList<Question>() {{
                            add(new Question("What enzyme breaks down sugars mouth and digestive tract?", "Amylase"));
                        }})
                }
        });
    }

    private Deck inpuDeck;
    private String answer;
    private Deck outputDeck;


//    @Test
//    public void test() {
//        AnkiGame ankiGame = new AnkiGame(null, new DummyJsonReader(), new DummyJsonWriter());
//    }
//
//
//    class DummyJsonReader implements InputReader {
//        @Override
//        public Optional<Deck> readDeck() {
//            return Optional.of(inpuDeck);
//        }
//    }
//
//    class DummyJsonWriter implements

}
