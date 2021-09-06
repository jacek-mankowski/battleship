package com.fulcrum.battleship;

public enum ShipBrand {
    CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String description;
    private final int length;

    ShipBrand(String description, int length) {
        this.description = description;
        this.length =length;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLength() {
        return this.length;
    }
}
