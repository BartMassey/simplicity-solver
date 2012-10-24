# "Simplicity" Solver
Copyright © 2012 Bart Massey and the Fall 2012 Portland State University CS 441/541 class

This program is licensed under the "MIT License".
Please see the file COPYING in the source
distribution of this software for license terms.

["Simplicity"](http://www.puzzlebeast.com/slidingblock/index.html)
is a sliding-blocks puzzle by James W. Stephens.

This solver is written in Java using Pathfinding Search.

This is the starting state of Simplicity.

    ..Y.
    BBYY
    .GR.
    .GRR

The goal is to get the R block to the upper-left corner in
as few moves as possible. One move is a sequence of
horizontal and vertical steps by a single piece such that it
does not intersect any other pieces at any point.

    R???
    RR??
    ????
    ????

The solution is displayed as a sequence of puzzle states
leading from the starting state to a goal state.

The solver uses A* search, with an admissible heuristic of
the number of pieces that provably have to move from their
current position. A back-of-the-envelope calculation gives a
strict upper bound of 64K for the number of possible states
of this problem, so almost anything works quickly. The first
working version of this search examines 272 states before
finding a solution. By comparison, Dijkstra's Algorithm
(achieved here by using a constant heuristic value of 0)
examines 324 states.
