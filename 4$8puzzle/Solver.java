/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-17
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Stack<Board> solution;
    private boolean solvable;
    private int moves = -1;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> exchPQ = new MinPQ<>();
        minPQ.insert(new Node(initial, null));
        exchPQ.insert(new Node(initial.twin(), null));

        while (!minPQ.min().cur.isGoal() && !exchPQ.min().cur.isGoal()) {
            Node node = minPQ.delMin();
            for (Board board: node.cur.neighbors()) {
                if (node.parent == null || !board.equals(node.parent.cur)) {
                    minPQ.insert(new Node(board, node));
                }
            }

            Node node1 = exchPQ.delMin();
            for (Board board1: node1.cur.neighbors()) {
                if (node1.parent == null || !board1.equals(node1.parent.cur)) {
                    exchPQ.insert(new Node(board1, node1));
                }
            }
        }

        Node curN = minPQ.min();
        if (curN == null) return;
        if (curN.cur == null || !curN.cur.isGoal()) return;
        solvable = true;
        moves = curN.moves;
        solution = new Stack<>();
        while (curN != null) {
            solution.push(curN.cur);
            curN = curN.parent;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves(){
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        return solution;
    }

    private class Node implements Comparable<Node> {

        private Board cur;
        private Node parent;
        private int moves;
        private int priority;

        public Node(Board board, Node parent) {
            cur = board;
            this.parent = parent;
            this.moves = parent == null ? 0 : parent.moves + 1;
            this.priority = this.cur.manhattan() + this.moves;
        }

        public int compareTo(Node o) {
            return this.priority - o.priority;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
