package com.jitterted.ebp.blackjack;

public class Player {
    private int playerBalance = 0;
    private int playerBet = 0;

    public Player(int playerBalance){
        this.playerBalance = Math.max(0,playerBalance);
    }

    public Player(){}

    //Command
    boolean placesBet(int bet){
        if(playerBalance()>=bet) {
            playerBet = bet;
            playerBalance -= bet;
            return true;
        }
        return false;
    }

    int playerBalance() {
        return this.playerBalance;
    }

    public void playerWins() {
      this.playerBalance += getBet() * 2;
    }

    private int getBet() {
        return this.playerBet;
    }

    public void playerLoses() {
        this.playerBalance += getBet() * 0;
    }

    public void playerTies() {
        this.playerBalance += getBet() * 1;
    }

    public void deposit(int amount) {
        this.playerBalance += amount;
    }
}
