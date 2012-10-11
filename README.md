# "Simplicity" Solver
Copyright © 2010 Bart Massey

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
