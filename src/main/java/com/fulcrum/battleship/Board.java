package com.fulcrum.battleship;

import java.util.Arrays;

public class Board {

    static class Coordinates {
        private int y = 0;
        private int x = 0;

        public void setCoords(char y, int x) throws Exception {
            if (this.isYValid(y) && this.isXValid(x)) {
                this.y = y - 'A';
                this.x = x - 1;
            } else {
                throw new Exception("Wrong ship location!");
            }
        }

        public boolean isYValid(char y) {
            return 'A' <= y && y < 'A' + DIMENSION;
        }

        public boolean isXValid(int x) {
            return 1 <= x && x < 1 + DIMENSION;
        }

        public int getXIndex() {
            return x;
        }

        public int getYIndex() {
            return y;
        }

        public int getDistance(Coordinates coordinates) throws Exception {
            if (this.getXIndex() == coordinates.getXIndex()) {
                return Math.abs(this.getYIndex() - coordinates.getYIndex()) + 1;
            } else if (this.getYIndex() == coordinates.getYIndex()) {
                return Math.abs(this.getXIndex() - coordinates.getXIndex()) + 1;
            } else {
                throw new Exception("Wrong ship location!");
            }
        }
    }

    private static final int DIMENSION = 10;
    private static final char FOG = '~';
    private static final char SHIP = 'o';
    private static final char HIT = 'x';
    private static final char MISS = 'm';

    private final char[][] table;

    public Board() {
        table = new char[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            Arrays.fill(table[i], FOG);
        }
    }

    public void setShip(Coordinates a, Coordinates b) throws Exception {
        a.getDistance(b);
        int minX = Math.min(a.getXIndex(), b.getXIndex());
        int maxX = Math.max(a.getXIndex(), b.getXIndex());
        int minY = Math.min(a.getYIndex(), b.getYIndex());
        int maxY = Math.max(a.getYIndex(), b.getYIndex());


        for (int y = minY; y <= maxY; y++) {
           for (int x = minX; x <= maxX; x++) {
                if (isShipClose(y, x)) {
                    throw new Exception("You placed it too close to another one.");
                }
            }
        }

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                table[y][x] = SHIP;
            }
        }
    }

    public boolean isShipClose(int y, int x) {
        boolean isClose = table[y][x] == SHIP;
        isClose = isClose || x - 1 >= 0 && table[y][x - 1] == SHIP;
        isClose = isClose || x - 1 >= 0 && y + 1 < DIMENSION && table[y + 1][x - 1] == SHIP;
        isClose = isClose || y + 1 < DIMENSION && table[y + 1][x] == SHIP;
        isClose = isClose || x + 1 < DIMENSION && y + 1 < DIMENSION && table[y + 1][x + 1] == SHIP;
        isClose = isClose || x + 1 < DIMENSION && table[y][x + 1] == SHIP;
        isClose = isClose || x + 1 < DIMENSION && y - 1 >= 0 && table[y - 1][x + 1] == SHIP;
        isClose = isClose || y - 1 >= 0 && table[y - 1][x] == SHIP;
        isClose = isClose || x - 1 >= 0 && y - 1 >= 0 && table[y - 1][x - 1] == SHIP;

        return isClose;
    }

    public boolean isShipSank(int y, int x) {
        int count = 0;

        int cY = y;
        int cX = x;
        while (cX < DIMENSION && (table[cY][cX] == SHIP || table[cY][cX] == HIT)) {
            if (table[cY][cX] == SHIP) {
                count++;
            }
            cX++;
        }

        cX = x;
        while (0 <= cX && (table[cY][cX] == SHIP || table[cY][cX] == HIT)) {
            if (table[cY][cX] == SHIP) {
                count++;
            }
            cX--;
        }

        cX = x;
        while (cY < DIMENSION && (table[cY][cX] == SHIP || table[cY][cX] == HIT)) {
            if (table[cY][cX] == SHIP) {
                count++;
            }
            cY++;
        }

        cY = y;
        while (0 <= cY && (table[cY][cX] == SHIP || table[cY][cX] == HIT)) {
            if (table[cY][cX] == SHIP) {
                count++;
            }
            cY--;
        }

        return count == 0;
    }

    public boolean isAnyShipOnBoard() {
        for (char[] row : table) {
            for (char c : row) {
                if (c == SHIP) {
                    return true;
                }
            }
        }
        return false;
    }

    public ShotResult setShot(Coordinates a) {
        char value = table[a.getYIndex()][a.getXIndex()];
        char result = (value == SHIP || value == HIT ? HIT : MISS);
        table[a.getYIndex()][a.getXIndex()] = result;

        if (result == HIT) {
            if (isShipSank(a.getYIndex(), a.getXIndex())) {
                if (!isAnyShipOnBoard()) {
                    return ShotResult.SANK_LAST_SHIP;
                }
                return ShotResult.SANK_SHIP;
            }
            return ShotResult.HIT_SHIP;
        } else {
            return ShotResult.MISSED;
        }
    }

    public String toString() {
        StringBuilder strBuild = new StringBuilder("  ");

        for (int i = 0; i < DIMENSION; i++) {
            strBuild.append(1 + i);
            if (i < DIMENSION - 1) {
                strBuild.append(" ");
            } else {
                strBuild.append("\n");
            }
        }
        for (int y = 0; y < DIMENSION; y++) {
            strBuild.append((char)('A' + y)).append(" ");
            for (int x = 0; x < DIMENSION; x++) {
                strBuild.append(table[y][x]);
                if (x < DIMENSION - 1) {
                    strBuild.append(" ");
                } else {
                    strBuild.append("\n");
                }
            }
        }

        return strBuild.toString();
    }

    public String toStringWithHiddenShips() {
        return this.toString().replace(SHIP, FOG);
    }

    public int getDimension() {
        return DIMENSION;
    }
}

