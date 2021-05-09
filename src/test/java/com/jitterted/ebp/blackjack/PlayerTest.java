package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {
    @Test
    public void verifyPlayerBalanceAfterDeposit() throws Exception {
        Player player = new Player();
        player.deposit(100);

        assertThat(player.playerBalance())
                .isEqualTo(100);
    }

    @Test
    public void verifyPlayerBalanceAfterPlacingBet() throws Exception {
        Player player = new Player();
        player.deposit(100);
        player.placesBet(50);
        assertThat(player.playerBalance())
                .isEqualTo(50);
    }

    @Test
    public void verifyPlayerBalanceAfterLoses() throws Exception {
        Player player = new Player();
        player.deposit(100);
        player.placesBet(50);
        assertThat(player.playerBalance())
                .isEqualTo(50);
        player.playerLoses();
        assertThat(player.playerBalance()).isEqualTo(50);
    }

    @Test
    public void verifyPlayerBalanceAfterTie() throws Exception {
        Player player = new Player();
        player.deposit(100);
        player.placesBet(50);
        assertThat(player.playerBalance())
                .isEqualTo(50);
        player.playerTies();
        assertThat(player.playerBalance()).isEqualTo(100);
    }

    @Test
    public void verifyPlayerBalanceAfterWin() throws Exception {
        Player player = new Player();
        player.deposit(100);
        player.placesBet(50);
        assertThat(player.playerBalance())
                .isEqualTo(50);
        player.playerWins();
        assertThat(player.playerBalance()).isEqualTo(150);
    }

    @Test
    public void verifyPlayerCannotBetMoreThanBalance() throws Exception {
        Player player = new Player();
        player.deposit(100);
        assertThat(player.placesBet(101)).isEqualTo(false);
        assertThat(player.playerBalance())
                .isEqualTo(100);
        player.placesBet(100);
        assertThat(player.playerBalance()).isEqualTo(0);
    }

    @Test
    public void verifyPlayerTotalBetAmount() throws Exception {
        Player player = new Player();
        player.deposit(100);
        player.placesBet(40);
        assertThat(player.totalBetAmount())
                .isEqualTo(40);
        player.placesBet(50);
        assertThat(player.totalBetAmount())
                .isEqualTo(90);
    }

    @Test
    public void verifyPlayerBalanceWith100PlusBetWins() throws Exception {
        Player player = new Player();
        player.deposit(200);
        player.placesBet(40);
        player.playerWins();
        assertThat(player.totalBetAmount())
                .isEqualTo(40);
        assertThat(player.playerBalance())
                .isEqualTo(240);//No bonus
        //2nd round
        player.placesBet(100);
        player.playerWins();
        assertThat(player.totalBetAmount())
                .isEqualTo(140);
        assertThat(player.playerBalance())
                .isEqualTo(350);//bonus
    }

    @Test
    public void verifyPlayerBalanceWith100PlusBetLoses() throws Exception {
        Player player = new Player();
        player.deposit(200);
        player.placesBet(40);
        player.playerLoses();
        assertThat(player.totalBetAmount())
                .isEqualTo(40);
        assertThat(player.playerBalance())
                .isEqualTo(160);//No bonus
        //2nd round
        player.placesBet(100);
        player.playerLoses();
        assertThat(player.totalBetAmount())
                .isEqualTo(140);
        assertThat(player.playerBalance())
                .isEqualTo(70);//bonus
    }
    @Test
    public void verifyPlayerBalanceWith100PlusBetTies() throws Exception {
        Player player = new Player();
        player.deposit(200);
        player.placesBet(40);
        player.playerTies();
        assertThat(player.totalBetAmount())
                .isEqualTo(40);
        assertThat(player.playerBalance())
                .isEqualTo(200);//No bonus
        //2nd round
        player.placesBet(100);
        player.playerTies();
        assertThat(player.totalBetAmount())
                .isEqualTo(140);
        assertThat(player.playerBalance())
                .isEqualTo(210);//bonus
    }

}
