/*
 * Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
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

    public String toString() {
        return "(" + r + ", " + c + ")";
    }
}
