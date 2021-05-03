package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class Card {
  private final Suit suit;
  private final Rank rank;

  public Card(Suit suit, String rank) {
    this.suit = suit;
    this.rank = Rank.of(rank);
  }

  public int rankValue() {
    return rank.rankValue();
  }

  //Long method code smell #1: moved the card color logic and card lines pieces into separate methods
  public String display() {
    return ansi()
        .fg(getCardColor()).toString()
        + String.join(ansi().cursorDown(1)
                            .cursorLeft(11)
                            .toString(), getCardDetailsToDisplay());
  }

  private Ansi.Color getCardColor() {
    return suit.isRed() ? Ansi.Color.RED : Ansi.Color.BLACK;
  }

  private String[] getCardDetailsToDisplay() {
    String[] lines = new String[7];
    lines[0] = "┌─────────┐";
    lines[1] = String.format("│%s%s       │", rank.display(), rank.display().equals("10") ? "" : " ");
    lines[2] = "│         │";
    lines[3] = String.format("│    %s    │", suit.symbol());
    lines[4] = "│         │";
    lines[5] = String.format("│       %s%s│", rank.display().equals("10") ? "" : " ", rank.display());
    lines[6] = "└─────────┘";
    return lines;
  }

  @Override
  public String toString() {
    return "Card {" +
        "suit=" + suit +
        ", rank=" + rank +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Card card = (Card) o;

    if (!suit.equals(card.suit)) return false;
    return rank.equals(card.rank);
  }

  @Override
  public int hashCode() {
    int result = suit.hashCode();
    result = 31 * result + rank.hashCode();
    return result;
  }
}
