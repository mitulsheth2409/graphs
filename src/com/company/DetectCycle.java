package com.company;

import java.util.HashSet;
import java.util.Set;

public class DetectCycle {
  public static void main(String[] args) {
    final Graph<Integer> directedGraph = new Graph<>(true);
    directedGraph.addEdge(0, 3);
    directedGraph.addEdge(0, 2);
    directedGraph.addEdge(1, 0);
    directedGraph.addEdge(2, 1);
    final Graph<Integer> undirectedGraph = directedGraph.getUndirected();
    System.out.println(isCyclicDirected(directedGraph));
    System.out.println(isCyclicUndirected(undirectedGraph));
  }

  private static boolean isCyclicDirected(
    final Graph<Integer> graph
  ) {
    final Set<Integer> visited = new HashSet<>();
    final Set<Integer> recursiveStack = new HashSet<>();
    for (final Integer node : graph.vertices()) {
      if (isCyclicUtilDirected(graph, visited, recursiveStack, node)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclicUtilDirected(
    final Graph<Integer> graph,
    final Set<Integer> visited,
    final Set<Integer> recStack,
    final Integer node
  ) {
    if (recStack.contains(node)) {
      return true;
    }

    if (visited.contains(node)) {
      return false;
    }
    visited.add(node);
    recStack.add(node);

    for (final Integer adj : graph.getOutgoing(node)) {
      if (isCyclicUtilDirected(graph, visited, recStack, adj)) {
        return true;
      }
    }
    recStack.remove(node);
    return false;
  }

  private static boolean isCyclicUndirected(final Graph<Integer> graph) {
    final Set<Integer> visited = new HashSet<>();
    for (final Integer node : graph.vertices()) {
      if (!visited.contains(node)) {
        if (isCyclicUtilUnDirected(graph, visited, null, node)) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isCyclicUtilUnDirected(
    final Graph<Integer> graph,
    final Set<Integer> visited,
    final Integer parent,
    final Integer node
  ) {
    visited.add(node);
    for (final Integer adj : graph.getOutgoing(node)) {
      if (visited.contains(adj)) {
        if (adj.equals(node) || !adj.equals(parent)) {
          return true;
        }
      } else if (isCyclicUtilUnDirected(graph, visited, node, adj)) {
        return true;
      }
    }
    return false;
  }
}
