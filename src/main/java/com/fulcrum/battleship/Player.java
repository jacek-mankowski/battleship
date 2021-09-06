package com.fulcrum.battleship;

public class Player {
    private final String name;
    private final Play play;

    public Player(String name) {
        this.name = name;
        this.play = new Play();
    }

    public String getName() {
        return this.name;
    }

    public Play getPlay() {
        return this.play;
    }
}
