package Maze;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        if (x < 0 || y < 0 || x >= maze.getNCols() || y >= maze.getNRows()){
            return false;}
        else if (!maze.getColor(x, y).equals(NON_BACKGROUND)){
            return false;}
        else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            maze.recolor(x, y, PATH);
            return true;
        } else {
            maze.recolor(x, y, PATH);
            if (findMazePath(x - 1, y) || findMazePath(x + 1, y) || findMazePath(x, y - 1) || findMazePath(x, y + 1)) {
                return true;
            } else {
                maze.recolor(x, y, TEMPORARY);
                return false;
            }
        }
    }

    public ArrayList < ArrayList < PairInt >> findAllMazePaths ( int x , int y) {
        ArrayList <ArrayList< PairInt >> result = new ArrayList < >();
        Stack < PairInt > trace = new Stack< >();
        findMazePathStackBased (0 ,0 , result , trace );
        return result ;
    }


    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
        if (x < 0 || y < 0 || x > maze.getNCols() - 1 || y > maze.getNRows() - 1) {
            return;
        } else if (!maze.getColor(x, y).equals(NON_BACKGROUND)) {
            return;
        } else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            trace.push(new PairInt(x, y));
            ArrayList<PairInt> z = new ArrayList<PairInt>();
            z.addAll(trace);
            result.add(z);
            trace.clear();
        } else {
            trace.push(new PairInt(x, y));
            maze.recolor(x, y, PATH);
            findMazePathStackBased(x + 1, y, result, (Stack<PairInt>)trace.clone());
            findMazePathStackBased(x - 1, y, result, (Stack<PairInt>)trace.clone());
            findMazePathStackBased(x, y + 1, result, (Stack<PairInt>)trace.clone());
            findMazePathStackBased(x, y - 1, result, (Stack<PairInt>)trace.clone());
            maze.recolor(x, y, NON_BACKGROUND);
        }

    }




        public ArrayList<PairInt> findMazePathMin(int x, int y) {
        maze.recolor(PATH, NON_BACKGROUND);
        ArrayList<ArrayList<PairInt>> answer = findAllMazePaths(x, y);
        if (answer.size() != 0) {
            ArrayList<PairInt> minIndex = answer.get(0);
            int minLength = minIndex.size();
            for (int i = 1; i < answer.size(); i++) {
                if (minLength >= answer.get(i).size()) {
                    minIndex = answer.get(i);
                    minLength = minIndex.size();
                }
            }
            return minIndex;
        } else {
            return new ArrayList<PairInt>();
        }
    }
    

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
