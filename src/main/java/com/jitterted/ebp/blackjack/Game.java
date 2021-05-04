package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

  private Hand dealerHand = new Hand();
  private Hand playerHand = new Hand();
  Player player = new Player();

  public static void main(String[] args) {
    displayWelcomeScreen();
    playGame();
    resetScreen();
  }

  private static void displayWelcomeScreen() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  //Long method code smell
  //Extracted the portion where input is sought into its own method
  private static void playGame() {
    Game game = new Game();

    String input;
    do {
      game.initialDeal();
      game.play();
      input = askPlayerIfTheyWantToPlayAgain();
    } while (playerSaysYes(input));
  }

  private static boolean playerSaysYes(String input) {
    return input.equalsIgnoreCase("y");
  }

  private static String askPlayerIfTheyWantToPlayAgain() {
    String input;
    System.out.println("Play again? (y/n):");
    Scanner scanner = new Scanner(System.in);
    input = scanner.nextLine();
    return input;
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealerHand = new Hand();
    playerHand = new Hand();

    // deal first round of cards, players first
    dealHand();

    // deal next round of cards
    dealHand();
  }

  private void dealHand() {
    drawCardIntoPlayerHand();
    drawCardIntoDealerHand();
  }
  //Feature Envy: This can potentially go into either the Deck class taking in the hand to be dealt to, or in to a Player class with
  //the player's hand.
  private void drawCardIntoDealerHand() {
    dealerHand.add(deck.draw());
  }

  private void drawCardIntoPlayerHand() { playerHand.add(deck.draw()); }

  //Long method code smell. Extracted the player play portions into its own method.
  public void play() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    // Potential Feature Envy, when we move to having a Player Class
    boolean playerBusted = playerPlays();

    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      dealerPlays();
    }

    displayFinalGameState();

    handleGameOutcome();
  }

  private boolean playerPlays() {
    boolean playerBusted = false;
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayerInLowerCase();
      if (playerStands(playerChoice)) {
        break;
      }
      if (playerHits(playerChoice)) {
        drawCardIntoPlayerHand();
        playerBusted = playerHand.isBusted();
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private boolean playerHits(String playerChoice) {
    return playerChoice.startsWith("h");
  }

  private boolean playerStands(String playerChoice) {
    return playerChoice.startsWith("s");
  }

  private void dealerPlays() {
    while (dealerHand.value() <= 16) {
      drawCardIntoDealerHand();
    }
  }

  private void handleGameOutcome() {
    if (playerHand.isBusted()) {
      System.out.println("You Busted, so you lose.  💸");
    } else if (dealerHand.isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
    } else if (playerHand.beats(dealerHand)) {
      System.out.println("You beat the Dealer! 💵");
    } else if (playerHand.pushesWith(dealerHand)) {
      System.out.println("Push: The house wins, you Lose. 💸");
    } else {
      System.out.println("You lost to the Dealer. 💸");
    }
  }

  private String inputFromPlayerInLowerCase() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine().toLowerCase();
  }

  private void displayGameState() {
    clearScreen();
    displayDealerHand();
    displayPlayerHand();
  }

  private void displayFinalGameState() {
    clearScreen();
    displayFinalDealerHand();
    displayPlayerHand();
  }

  private void displayDealerHand() {
    displayDealerUpCard();
    displayDealerHoleCard();
  }

  private void displayDealerUpCard() {
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.displayFirstCard()); // first card is Face Up
  }

  private void clearScreen() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
  }
  // second card is the hole card, which is hidden

  private void displayDealerHoleCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayFinalDealerHand() {
    System.out.println("Dealer has: ");
    dealerHand.displayHand();
    System.out.println(" (" + dealerHand.value() + ")");
  }

  private void displayPlayerHand() {
    System.out.println();
    System.out.println("Player has: ");
    playerHand.displayHand();
    System.out.println(" (" + playerHand.value() + ")");
  }

  public void playerDeposits(int amount) {
    player.deposit(amount);
  }
}
