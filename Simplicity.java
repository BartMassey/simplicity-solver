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
}

class Block {
    Coord pos;  // coordinates of upper-left corner
    char color;
    Coord[] squares;  // array of relative positions
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

