/*
 * Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

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
