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
}
