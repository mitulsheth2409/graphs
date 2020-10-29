package com.company;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GraphUtils {
  public static <V extends Comparable<V>> boolean isConnected(final Graph<V> graph) {
    // if there are no vertices in the graph, or just one vertex, then the graph is connected
    if (graph.size() < 2) {
      return true;
    }

    // if there are vertices that are not connected and the graph has more than one node
    if (graph.getAllZeroDegreeVertices().size() > 0) {
      return false;
    }

    // if graph is directed, convert to undirected and then do dfs
    final Graph<V> operatingGraph = graph.isDirected() ? graph.getUndirected() : graph;
    final V vertex = operatingGraph.vertices().iterator().next();
    final Map<V, Boolean> visited =
      operatingGraph.vertices().stream().collect(Collectors.toMap(Function.identity(), v -> false));
    performDFS(operatingGraph, vertex, visited);
    return !visited.containsValue(false);
  }

  public static <V extends Comparable<V>> void performDFS(
    final Graph<V> graph,
    final V node,
    final Map<V, Boolean> visited
  ) {
    visited.put(node, true);
    graph.getOutgoing(node)
      .stream()
      .filter(n -> !visited.get(n))
      .forEach(n -> performDFS(graph, n, visited));
  }
}
