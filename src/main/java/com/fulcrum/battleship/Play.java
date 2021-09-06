package com.fulcrum.battleship;

import java.util.Scanner;

class Play {
    private final Board board;
    private final Scanner scanner;

    public Play() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    public void showBoard() {
        System.out.println(board);
    }

    public void showBoardWithHiddenShips() {
        System.out.print(board.toStringWithHiddenShips());
    }

    public void drawLine() {
        System.out.println("--".repeat(this.board.getDimension() + 1));
    }

    public void setShips() {
        this.showBoard();
        for (ShipBrand shipBrand : ShipBrand.values()) {
            this.setShip(shipBrand);
        }
    }

    private void setShip(ShipBrand shipBrand) {
        boolean isSet = false;

        System.out.println("Enter the coordinates of the " + shipBrand.getDescription() + " (" + shipBrand.getLength() + " cells):");
        do {
            String crd1 = scanner.next();
            String crd2 = scanner.next();

            try {
                Board.Coordinates coordinates1 = new Board.Coordinates();
                Board.Coordinates coordinates2 = new Board.Coordinates();

                coordinates1.setCoords(crd1.toUpperCase().charAt(0), Integer.parseInt(crd1.substring(1)));
                coordinates2.setCoords(crd2.toUpperCase().charAt(0), Integer.parseInt(crd2.substring(1)));

                int distance = coordinates1.getDistance(coordinates2);

                if (distance != shipBrand.getLength()) {
                    throw new Exception("Wrong length of the " + shipBrand.getDescription() + "!");
                }

                this.board.setShip(coordinates1, coordinates2);
                isSet = true;
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + " Try again:");
            }
        } while (!isSet);
        this.showBoard();
    }

    public ShotResult takeShot() {
        boolean isSet = false;
        Board.Coordinates coordinates = new Board.Coordinates();

        do {
            String crd = scanner.next();
            try {
                coordinates.setCoords(crd.toUpperCase().charAt(0), Integer.parseInt(crd.substring(1)));
                isSet = true;
            } catch (Exception e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        } while (!isSet);

        return this.board.setShot(coordinates);
    }
}