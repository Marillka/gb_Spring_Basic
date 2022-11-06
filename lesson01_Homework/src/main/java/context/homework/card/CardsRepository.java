package context.homework.card;

import context.homework.Patient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CardsRepository {

    private List<Card> cards;

    @PostConstruct
    public void init() {
         cards = new ArrayList<>(Arrays.asList(
                 new Card(1L, "Alex"),
                 new Card(2L, "Bob"),
                 new Card(3L, "John")
         ));
    }

    public Card findCardByPatientName(Patient patient) {
        return cards.stream().filter(p -> p.getName().equals(patient.getName())).findFirst().orElseThrow(() -> new RuntimeException());
    }
}
