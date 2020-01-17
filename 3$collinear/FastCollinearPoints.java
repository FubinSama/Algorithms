/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-11
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> list = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        for (Point p: points) if (p == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) { //is it repeat?
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        if (points.length < 4) return;
        Point[] points1 = Arrays.copyOf(points, points.length);
        for (Point base: points) {
            Arrays.sort(points1, base.slopeOrder());
            Point min = base, max = base;
            if (points1[1].compareTo(min) < 0) min = points1[1];
            if (points1[1].compareTo(max) > 0) max = points1[1];
            double k0 = base.slopeTo(points1[1]);
            int cnt = 2;
            for (int i = 2; i < points1.length; i++) {
                double k = base.slopeTo(points1[i]);
                if (Double.compare(k, k0) == 0) {
                    ++cnt;
                } else {
                    if (cnt >=4 && base.compareTo(min) == 0) {
                        list.add(new LineSegment(min, max));
                    }
                    min = base; max = base;
                    k0 = k;
                    cnt = 2;
                }
                if (points1[i].compareTo(min) < 0) min = points1[i];
                if (points1[i].compareTo(max) > 0) max = points1[i];
            }
            if (cnt >=4 && base.compareTo(min) == 0) list.add(new LineSegment(min, max));
        }
    }
    public int numberOfSegments() {
        // the number of line segments
        return list.size();
    }
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] ans = new LineSegment[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.002 * 5);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        StdDraw.setPenRadius(0.002 * 2.5);
        StdDraw.setPenColor(0, 0, 255);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(collinear.segments().length);
    }
}
