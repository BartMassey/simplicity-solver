/*
 * Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

/*
 * "Simplicity" Solver
 */

import java.lang.*;
import java.util.*;

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

    public boolean equals(Object o) {
        Coord q = (Coord)o;
        return q.r == r && q.c == c;
    }

    boolean equals(int r, int c) {
        return this.r == r && this.c == c;
    }

    boolean clipped() {
        return r < 0 || r >= 4 || c < 0 || c >= 4;
    }

    public int hashCode() {
        return (r << 2) | c;
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

    public boolean equals(Object o) {
        Block b = (Block)o;
        return b.color == color && b.pos.equals(pos);
    }

    public int hashCode() {
        return (pos.hashCode() << 8) | color;
    }
}

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

public class Simplicity {
    private static final boolean debug = false;

    static void printSoln(State s) {
        if (s == null)
            return;
        printSoln(s.parent);
        System.out.println();
        s.print();
    }

    public static void main(String[] args) {
        HashSet<State> stop = new HashSet<State>();
        PriorityQueue<State> q = new PriorityQueue<State>();

        State start = new State();
        start.start();
        start.print();
        System.out.println();
        System.out.println("Solving...");
        q.add(start);
        int examined = 0;
        while(true) {
            State s = q.poll();
            examined++;
            if (s == null)
                break;
            if (s.goal()) {
                System.out.println("Solution found");
                printSoln(s);
                return;
            }
            stop.add(s);
            if (debug) {
                System.out.println("Examining state:");
                s.print();
                System.out.println();
            } else if (examined % 1000 == 0) {
                System.out.print('.');
            }
            Coord[] offsets = {
                new Coord(-1, 0),
                new Coord(0, -1),
                new Coord(1, 0),
                new Coord(0, 1) };
            for (int b = 0; b < 4; b++) {
                for (Coord c : offsets) {
                    State sc = s.nextState();
                    sc.blocks[b].pos = sc.blocks[b].pos.offset(c);
                    if (stop.contains(sc))
                        continue;
                    if (sc.blocks[b].clipped())
                        continue;
                    int d = 0;
                    for (; d < 4; d++) {
                        if (d == b)
                            continue;
                        if (sc.blocks[b].intersects(sc.blocks[d]))
                            break;
                    }
                    if (d < 4)
                        continue;
                    q.add(sc);
                }
            }
        }
        System.out.println("Puzzle has no solution.");
    }
}
