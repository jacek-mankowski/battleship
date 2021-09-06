package com.fulcrum.battleship;

import java.io.IOException;
import java.util.Objects;

public class Battleship {

    private static final int NUMBER_OF_PLAYERS = 2;
    private final Player[] players = new Player[NUMBER_OF_PLAYERS];
    private Player winner;

    public void run() {
        this.run("Player1", "Player2");
    }

    public void run(String playerName1, String playerName2) {
        if (!checkPlayersNames(playerName1, playerName2)) {
            System.out.println("Players names should not be empty or the same.");
        } else {
            players[0] = new Player(playerName1);
            players[1] = new Player(playerName2);

            this.startGame();
            this.setPlayersShips();
            this.doShooting();
            this.congratulateWinner();
        }
    }

    private boolean checkPlayersNames(String playerName1, String playerName2) {
        String[] playersNames = {playerName1, playerName2};

        for (int i = 0; i < playersNames.length; i++) {
            if (Objects.isNull(playersNames[i]) || "".equals(playersNames[i])) {
                return false;
            }
            for (int j = i + 1; j < playersNames.length; j++) {
                if (Objects.equals(playersNames[i], playersNames[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startGame() {
        clearScreen();
        System.out.println("The game starts!\n");
    }

    private void passTheMove() {
        System.out.println("Press Enter and pass the move to another player.");
        pressEnter();
        clearScreen();
    }

    private static void pressEnter() {
        char key;
        try {
            do {
                key = (char) System.in.read();
            } while (key != '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearScreen() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(20));
        }
    }

    private void setPlayersShips() {
        for (Player player : players) {
            System.out.println(player.getName() + ", place your ships on the game field.\n");
            player.getPlay().setShips();
            this.passTheMove();
        }
    }

    private void doShooting() {
        ShotResult result;
        int current = NUMBER_OF_PLAYERS - 1;
        int opponent;

        do {
            current = (current + 1) % NUMBER_OF_PLAYERS;
            opponent = (current + 1) % NUMBER_OF_PLAYERS;

            players[opponent].getPlay().showBoardWithHiddenShips();
            players[opponent].getPlay().drawLine();
            players[current].getPlay().showBoard();
            System.out.println(players[current].getName() + ", it's your turn:");
            result = players[opponent].getPlay().takeShot();
            if (result != ShotResult.SANK_LAST_SHIP) {
                System.out.println(result.getDescription());
                this.passTheMove();
            }
        } while (result != ShotResult.SANK_LAST_SHIP);

        this.setWinner(players[current]);
    }

    private void setWinner(Player player) {
        this.winner = player;
    }

    private void congratulateWinner() {
        System.out.println(ShotResult.SANK_LAST_SHIP.getDescription());
        System.out.println(this.winner.getName() + ", you won. Congratulations!");
    }
}
