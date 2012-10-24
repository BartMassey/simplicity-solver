/*
 * Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

/*
 * "Simplicity" Solver
 */

class Coord {
    int r, c;   // row, column coordinates

    Coord(int r, int c) {
        this.r = r;
        this.c = c;
    }

    Coord(Coord p) {
        r = p.r;
        c = p.c;
    }

    Coord offset(Coord q) {
        return new Coord(q.r + r, q.c + c);
    }

    boolean equals(Coord q) {
        return q.r == r && q.c == c;
    }

    boolean equals(int r, int c) {
        return this.r == r && this.c == c;
    }

    boolean clipped() {
        return r < 0 || r >= 4 || c < 0 || c >= 4;
    }
}

class Block {
    Coord pos;  // coordinates of upper-left corner
    char color;
    Coord[] squares;  // array of relative positions

    Block(char color, Coord[] squares, int r, int c) {
        this.pos = new Coord(r, c);
        this.color = color;
        this.squares = squares;
    }

    Block(Block b) {
        pos = new Coord(b.pos);
        color = b.color;
        squares = b.squares;
    }

    boolean squareAt(Coord pos) {
        for (int i = 0; i < squares.length; i++)
            if (this.pos.offset(squares[i]).equals(pos))
                return true;
        return false;
    }

    boolean squareAt(int r, int c) {
        return squareAt(new Coord(r, c));
    }

    boolean intersects(Block b) {
        for (int i = 0; i < squares.length; i++)
            if (b.squareAt(pos.offset(squares[i])))
                return true;
        return false;
    }

    boolean clipped() {
        for (int i = 0; i < squares.length; i++) {
            Coord s = pos.offset(squares[i]);
            if (s.clipped())
                return true;
        }
        return false;
    }

    boolean isAt(int r, int c) {
        return pos.equals(r, c);
    }
}

/* Operation on State:
 *   + Display
 *   + Slide a block
 *   + Compute legal moves
 *   + Check for goal state
 *   + Check for block collisions
 */
class State {
    
    Block[] blocks = null;

    int nmoves = 0;

    State parent = null;

    void start() {
        Coord[] ell = {
            new Coord(0, 0),
            new Coord(1, 0),
            new Coord(1, 1) };
        Coord[] dash = {
            new Coord(0, 0),
            new Coord(0, 1) };
        Coord[] pipe = {
            new Coord(0, 0),
            new Coord(1, 0) };
        Block[] startBlocks = {
            new Block('R', ell, 2, 2),
            new Block('B', dash, 1, 0),
            new Block('G', pipe, 2, 1),
            new Block('Y', ell, 0, 2) };
        blocks = startBlocks;
    }

    State nextState() {
        State s = new State();
        s.blocks = new Block[4];
        for (int i = 0; i < 4; i++)
            s.blocks[i] = new Block(blocks[i]);
        s.nmoves = nmoves + 1;
        s.parent = this;
        return s;
    }

    void print() {
        System.out.println("moves: " + nmoves);
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                boolean hit1 = false;
                for (Block b: blocks) {
                    if (b.squareAt(r, c)) {
                        System.out.print(b.color);
                        hit1 = true;
                        break;
                    }
                }
                if (!hit1)
                    System.out.print('.');
            }
            System.out.println();
        }
    }

    boolean goal() {
        return blocks[0].isAt(0, 0);
    }
}

public class Simplicity {
    public static void main(String[] args) {
        State start = new State();
        start.start();
        start.print();
    }
}
