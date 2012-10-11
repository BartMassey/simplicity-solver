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

The goal is to get the R block to the upper-left corner.

    R???
    RR??
    ????
    ????

A solution will be a sequence of puzzle states in the format
shown above leading from the starting state to a goal state.

The solver uses A* search, with an admissible heuristic of
taxicab distance of the R piece from its goal position. A
back-of-the-envelope calculation gives a strict upper bound
of 64K for the number of possible states of this problem,\
so almost anything works quickly.
