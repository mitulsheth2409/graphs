package com.company;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.IntStream;

public class PathRectangleWithCircles {
  public static void main(String[] args) {
    final PathRectangleWithCircles prc = new PathRectangleWithCircles();
    int m1 = 5, n1 = 5, k1 = 2, r1 = 1;
    int[] x = {1, 3};
    int[] y = {3, 3};
    System.out.println(prc.ifPathPossible(m1, n1, k1, r1, x, y));

    int m2 = 5, n2 = 5, k2 = 2, r2 = 1;
    x = new int[]{1, 1};
    y = new int[]{2, 3};
    System.out.println(prc.ifPathPossible(m2, n2, k2, r2, x, y));
  }


  private boolean ifPathPossible(final int m, final int n, final int k, final int r,
                                 final int[] x, final int[] y ) {
    final int[][] rect = new int[m][n];
    IntStream.range(0, m).forEach(i ->
      IntStream.range(0, n).forEach(j ->
        IntStream.range(0, k).forEach(t -> {
          final double p = Math.sqrt(Math.pow(x[t] - 1 - i, 2) + Math.pow(y[t] - 1 - j, 2));
          if (p <= r) {
            rect[i][j] = -1;
          }
        })
      )
    );

    if (rect[0][0] == -1) {
      return false;
    }
    final int[] x_helper = {0, 0, 1, 1, 1, -1, -1 , -1};
    final int[] y_helper = {1, -1, 1, -1, 0, 0, 1, -1};
    final Queue<Point> queue = new LinkedList<>();
    rect[0][0] = 1;
    queue.add(new Point(0, 0));
    while (!queue.isEmpty()) {
      final Point point = queue.poll();
      IntStream.range(0, 8).forEach(t -> {
        final int new_x = point.x + x_helper[t];
        final int new_y = point.y + y_helper[t];
        if (isValid(new_x, new_y, rect, m, n)) {
          rect[new_x][new_y] = 1;
          queue.add(new Point(new_x, new_y));
        }
      });
    }
    return rect[m - 1][n - 1] == 1;
  }

  private boolean isValid(final int i, final int j, final int[][] rect, final int m, final int n) {
    return i >= 0 && j >= 0 && i < m  && j < n && rect[i][j] == 0;
  }

  private static class Point {
    final int x;
    final int y;

    public Point(final int x, final int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Point)) return false;
      Point point = (Point) o;
      return x == point.x &&
        y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
