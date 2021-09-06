package com.fulcrum.battleship;

public enum ShotResult {
    MISSED("You missed!"),
    HIT_SHIP("You hit a ship!"),
    SANK_SHIP("You sank a ship!"),
    SANK_LAST_SHIP("You sank the last ship!");

    private final String description;

    ShotResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
