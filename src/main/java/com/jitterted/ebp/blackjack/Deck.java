package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
  private final List<Card> cards = new ArrayList<>();
  private final List<String> cardValues = new ArrayList<String>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));

  //Long method code smell, even though the card values were needed to be enumerated by this Black Jack Deck, we need for it to be
  public Deck() {
    for (Suit suit : Suit.values()) {
      for (String cardValue : cardValues) {
        cards.add(new Card(suit, cardValue));
      }
    }
    Collections.shuffle(cards);
  }

  public int size() {
    return cards.size();
  }

  public Card draw() {
    return cards.remove(0);
  }
}
