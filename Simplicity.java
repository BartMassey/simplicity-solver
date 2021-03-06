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

public class Simplicity {
    private static final boolean debug = false;

    static void printSoln(State s) {
        if (s == null)
            return;
        printSoln(s.parent);
        System.out.println();
        s.print();
    }

    static HashSet<State> children(State s, HashMap<State, State> stop) {
        HashSet<State> cs = new HashSet<State>();
        for (int b = 0; b < 4; b++) {
            if (debug)
                System.out.println("Processing block " + b);
            HashSet<State> csb = new HashSet<State>();
            csb.add(s);
            while (true) {
                HashSet<State> ncsb = new HashSet<State>();
                for (State sp: csb) {
                    Coord[] offsets = {
                        new Coord(-1, 0),
                        new Coord(0, -1),
                        new Coord(1, 0),
                        new Coord(0, 1) };
                    for (Coord c: offsets) {
                        State sc = sp.nextState(s, b, c);
                        if (csb.contains(sc) || ncsb.contains(sc))
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
                        if (debug)
                            System.out.println("Adding child " + b +
                                               " " + sc.blocks[b].pos);
                        ncsb.add(sc);
                    }
                }
                if (ncsb.isEmpty())
                    break;
                csb.addAll(ncsb);
                for (State sc: ncsb) {
                    if (debug)
                        System.out.println("Processing child " + b +
                                           " " + sc.blocks[b].pos);
                    State ss = stop.get(sc);
                    if (ss != null) {
                        if (ss.fmoves() <= sc.fmoves())
                            continue;
                        stop.remove(ss);
                    }
                    cs.add(sc);
                }
            }
        }
        return cs;
    }

    public static void main(String[] args) {
        HashMap<State, State> stop = new HashMap<State, State>();
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
                System.out.println("Solution found after examining " +
                                   examined + " states.");
                printSoln(s);
                return;
            }
            stop.put(s, s);
            if (debug) {
                System.out.println("Examining state:");
                s.print();
                System.out.println();
            } else if (examined % 1000 == 0) {
                System.out.print('.');
            }
            HashSet<State> cs = children(s, stop);
            for (State c: cs)
                q.add(c);
        }
        System.out.println("Puzzle has no solution.");
    }
}
