/*
 * "Simplicity" Solver
 * Copyright © 2012 Bart Massey
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

