package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.IntStream;

import javafx.collections.transformation.SortedList;

public class KCores<V extends Comparable<V>> {
  public static void main(String[] args) {
    final Graph<Integer> graph = new Graph(false);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(1, 5);
    graph.addEdge(2, 3);
    graph.addEdge(2, 4);
    graph.addEdge(2, 5);
    graph.addEdge(2, 6);
    graph.addEdge(3, 4);
    graph.addEdge(3, 6);
    graph.addEdge(3, 7);
    graph.addEdge(4, 6);
    graph.addEdge(4, 7);
    graph.addEdge(5, 6);
    graph.addEdge(5, 8);
    graph.addEdge(6, 7);
    graph.addEdge(6, 8);
//    new KCores<>(graph, 3).getKCores();
    System.out.println();

    final Graph<Integer> g2 = new Graph<>(false);
    g2.addEdge(0, 4);
    g2.addEdge(1, 2);
    g2.addEdge(1, 3);
    g2.addEdge(1, 4);
    g2.addEdge(1, 5);
    g2.addEdge(2, 7);
    g2.addEdge(2, 8);
    g2.addEdge(3, 7);
    g2.addEdge(3, 8);
    g2.addEdge(7, 8);

    Graph<Integer> g3 = new Graph<>(false);
    g3.addEdge(0, 1);
    g3.addEdge(0, 2);
    g3.addEdge(0, 3);
    g3.addEdge(1, 4);
    g3.addEdge(1, 5);
    g3.addEdge(1, 6);
    g3.addEdge(2, 7);
    g3.addEdge(2, 8);
    g3.addEdge(2, 9);
    g3.addEdge(3, 10);
    g3.addEdge(3, 11);
    g3.addEdge(3, 12);
    new KCores<>(g3, 3).getKCores();
  }

  private final Graph<V> graph;
  private final int k_cores;

  public KCores(final Graph<V> graph, final int k_cores) {
    this.graph = graph;
    this.k_cores = k_cores;
  }

  private void getKCores() {
    final Set<V> result = new HashSet<>();
    final Map<V, Integer> degrees = new HashMap<>();
    int minDeg = Integer.MAX_VALUE;
    for (final V vertex : graph.vertices()) {
      final int degree = graph.degree(vertex);
      minDeg = Math.min(degree, minDeg);
      degrees.put(vertex, degree);
    }
    if (minDeg >= k_cores) {
      System.out.println("The graph is itself the answer");
    }
    final Set<V> processed = new HashSet<>();
    graph.vertices().forEach(vertex -> {
      if (!processed.contains(vertex) && degrees.get(vertex) < k_cores) {
        final Set<V> visited = new HashSet<>();
        dfs(vertex, visited, processed, degrees);
      }
    });

    degrees.forEach((key, value) -> {
      if (value >= k_cores) {
        System.out.println(key + " = " + value);
        graph.getOutgoing(key).stream()
          .filter(neighbor -> degrees.get(neighbor) >= 3)
          .forEach(neighbor -> System.out.print(neighbor + " "));
      }
    });
  }

  private void dfs(
    final V vertex,
    final Set<V> visited,
    final Set<V> processed,
    final Map<V, Integer> degrees
  ) {
    visited.add(vertex);
    processed.add(vertex);

    graph.getOutgoing(vertex).forEach(neighbor -> {
      if (!visited.contains(neighbor) && !processed.contains(neighbor)) {
        degrees.put(neighbor, degrees.get(neighbor) - 1);
        if (degrees.get(neighbor) < k_cores) {
          dfs(neighbor, visited, processed, degrees);
        }
      }
    });
  }
}
