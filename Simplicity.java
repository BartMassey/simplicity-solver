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

    Coord offset(Coord q) {
        return new Coord(q.r + r, q.c + c);
    }

    boolean equals(Coord q) {
        return q.r == r && q.c == c;
    }
}

class Block {
    Coord pos;  // coordinates of upper-left corner
    char color;
    Coord[] squares;  // array of relative positions

    Block(Coord pos, char color, Coord[] squares) {
        this.pos = pos;
        this.color = color;
        this.squares = squares;
    }

    boolean squareAt(Coord pos) {
        for (int i = 0; i < squares.length; i++)
            if (this.pos.offset(squares[i]).equals(pos))
                return true;
        return false;
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
    Block[] blocks;
}

