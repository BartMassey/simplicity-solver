/*
 * Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

/* Operation on State:
 *   + Display
 *   + Slide a block
 *   + Compute legal moves
 *   + Check for goal state
 *   + Check for block collisions
 */
class State implements Comparable<State> {
    
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
        int f = nmoves + hmoves();
        System.out.println("moves: " + nmoves + "   f: " + f);
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

    public boolean equals(Object o) {
        State s = (State) o;
        for (int i = 0; i < 4; i++)
            if (!blocks[i].equals(s.blocks[i]))
                return false;
        return true;
    }

    public int hashCode() {
        return
            (blocks[0].hashCode() << 20) ^
            (blocks[1].hashCode() << 13) ^
            (blocks[2].hashCode() << 6) ^
            blocks[3].hashCode();
    }

    public int hmoves() {
        Coord p = blocks[0].pos;
        return p.r + p.c;
    }

    public int gmoves() {
        return nmoves;
    }

    public int fmoves() {
        return gmoves() + hmoves();
    }

    public int compareTo(State s) {
        int f = fmoves();
        int sf = s.fmoves();
        if (f == sf)
            return s.gmoves() - gmoves();
        return f - sf;
    }
}
