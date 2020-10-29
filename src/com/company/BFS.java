package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BFS {
  private static Graph<Integer> graph;
  public static void main(String[] args) {
    graph = new Graph<>(true);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    System.out.println(graph);
    System.out.println(graph.size());
    System.out.println(performBFS(0));
  }

  private static List<Integer> performBFS(final Integer start) {
    final List<Integer> result = new ArrayList<>();
    final Queue<Integer> queue = new LinkedList<>();
    final Set<Integer> visited = new HashSet<>();
    queue.add(start);
    visited.add(start);
    while (!queue.isEmpty()) {
      final Integer node = queue.poll();
      result.add(node);
      graph.getOutgoing(node)
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(n -> {
          visited.add(n);
          queue.add(n);
        });
    }
    return result;
  }
}
