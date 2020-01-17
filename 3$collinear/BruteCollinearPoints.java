/* *****************************************************************************
 *  Name: wfb
 *  Date: 2020-01-11
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> list = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
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
        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length-2; j++) {
                double m = points[i].slopeTo(points[j]);
                Point min = points[i], max = points[i];
                if (points[j].compareTo(min) < 0) min = points[j];
                if (points[j].compareTo(max) > 0) max = points[j];
                for (int k = j+1; k < points.length-1; k++) {
                    double n = points[j].slopeTo(points[k]);
                    if (Double.compare(m, n) != 0) continue;
                    if (points[k].compareTo(min) < 0) min = points[k];
                    if (points[k].compareTo(max) > 0) max = points[k];
                    for (int l = k+1; l < points.length; l++) {
                        double o = points[k].slopeTo(points[l]);
                        if (Double.compare(n, o) != 0) continue;
                        if (points[l].compareTo(min) < 0) min = points[l];
                        if (points[l].compareTo(max) > 0) max = points[l];
                        list.add(new LineSegment(min, max));
                    }
                }
            }
        }
    }
    public int numberOfSegments() {
        // the number of line segments
        return this.list.size();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
