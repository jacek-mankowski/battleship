package com.fulcrum.battleship;

public class Main {

    public static void main(String[] args) {
        Battleship battleship = new Battleship();

        if (args.length > 1) {
            battleship.run(args[0], args[1]);
        } else {
            battleship.run();
        }
    }
}
