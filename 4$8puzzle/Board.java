/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-16
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int white;
    private int hamming = -1;
    private int manhattan = -1;
    private List<Board> neighbors;
    private static final int[][] table = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.tiles = tiles;
        this.white = getWhite();
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length);
        sb.append("\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                sb.append(" ")
                  .append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming >= 0) return hamming;
        int cnt = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == i * tiles[0].length + j + 1 || tiles[i][j] == 0) continue;
                cnt++;
            }
        }
        hamming = cnt;
        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan >= 0) return manhattan;
        int cnt = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == i * tiles[0].length + j + 1 || tiles[i][j] == 0) continue;
                int tmp = tiles[i][j] - 1;
                cnt += Math.abs(tmp / tiles[0].length - i);
                cnt += Math.abs(tmp % tiles[0].length - j);
            }
        }
        manhattan = cnt;
        return cnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors == null) getList();
        return neighbors;
    }



    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = deepCopy(tiles);
        if (copy[0][0] == 0) exch(copy, 0, 1, 1,1);
        else if (copy[0][1] == 0) exch(copy, 0, 0, 1,0);
        else exch(copy, 0, 0, 0, 1);
        return new Board(copy);
    }

    private static void exch(int[][] arr, int ix, int iy, int jx, int jy) {
        if (arr == null) throw new IllegalArgumentException();
        int tmp = arr[ix][iy];
        arr[ix][iy] = arr[jx][jy];
        arr[jx][jy] = tmp;
    }

    private static int[][] deepCopy(int[][] tiles) {
        int[][] ans = tiles.clone();
        for (int i = 0; i < tiles.length; i++) {
            ans[i] = tiles[i].clone();
        }
        return ans;
    }

    private void getList(){
        neighbors = new LinkedList<>();
        int wi = white / tiles[0].length;
        int wj = white % tiles[0].length;
        for (int i = 0; i < 4; i++) {
            int ix = wi + table[i][0];
            int iy = wj + table[i][1];
            if (!check(wi, wj, i)) continue;
            int[][] arr = deepCopy(tiles);
            exch(arr, wi, wj, ix, iy);
            neighbors.add(new Board(arr));
        }
    }

    private boolean check(int x, int y, int i) {
        if (x + table[i][0] < 0 || x + table[i][0] >= tiles.length) return false;
        if (y + table[i][1] < 0 || y + table[i][1] >= tiles[0].length) return false;
        return true;
    }

    private int getWhite() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) return i * tiles[0].length + j;
            }
        }
        return 0;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board twin1 = initial.twin();
        Board twin2 = initial.twin();
        assert twin1.equals(twin2);
        assert twin2.equals(twin1);
        StdOut.println(initial);
        StdOut.println(twin1);
    }
}
