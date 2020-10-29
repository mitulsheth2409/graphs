package com.company;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MotherVertex<V extends Comparable<V>> {
  private final Graph<V> graph;
  public MotherVertex(final Graph<V> graph) {
    this.graph = graph;
  }
  public V getMotherVertex() throws Exception {
    if (!GraphUtils.isConnected(graph)) {
      throw new IllegalArgumentException("No mother vertex for a disconnected graph");
    }
    if (!graph.isDirected()) {
      throw new IllegalArgumentException("All the vertices of a connected undirected graph are " +
                                           "mother vertices.");
    }
    final Map<V, Boolean> visited =
      graph.vertices().stream().collect(Collectors.toMap(Function.identity(), v -> false));

    final AtomicReference<V> lastFinishedVertex = new AtomicReference<>();
    graph.vertices().forEach(vertex -> {
      if (!visited.get(vertex)) {
        GraphUtils.performDFS(graph, vertex, visited);
        lastFinishedVertex.set(vertex);
      }
    });

    final Map<V, Boolean> invertVisited =
      graph.vertices().stream().collect(Collectors.toMap(Function.identity(), v -> false));
    GraphUtils.performDFS(graph, lastFinishedVertex.get(), invertVisited);
    return invertVisited.containsValue(false) ? null : lastFinishedVertex.get();
  }

  public static void main(String[] args) throws Exception {
    final Graph<Integer> graph = new Graph<>(true);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    graph.addEdge(3, 4);
    graph.addEdge(5, 0);
    final MotherVertex<Integer> motherVertex = new MotherVertex<>(graph);
    System.out.println(motherVertex.getMotherVertex());
  }
}
