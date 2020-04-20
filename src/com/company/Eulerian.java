package com.company;

import java.util.Optional;
import java.util.Set;

public class Eulerian<V extends Comparable<V>> {
  public static void main(String[] args) {
    final Graph<Integer> graph = new Graph<>(false);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(0, 3);
    graph.addEdge(3, 4);
    System.out.println(new Eulerian<>(graph).isEulerian());

    graph.addEdge(0 , 4);
    System.out.println(new Eulerian<>(graph).isEulerian());

    graph.removeEdge(0, 4);
    graph.addEdge(1, 3);
    System.out.println(new Eulerian<>(graph).isEulerian());
  }

  private static final String NOT_EULERIAN = "NOT_EULERIAN";
  private static final String EULERIAN_PATH = "EULERIAN_PATH";
  private static final String EULERIAN_CYCLE = "EULERIAN_CYCLE";
  private final Graph<V> graph;
  public Eulerian(final Graph<V> graph) {
    this.graph = graph;
  }

  public String isEulerian() {
    if (!isConnected()) {
      return NOT_EULERIAN;
    }

    final long oddDegreeCount = graph.getAllNonZeroDegreeVertex().stream()
      .filter(vertex -> (graph.degree(vertex)) % 2 != 0)
      .count();

    // odd count can never be 1 in undirected graph
    return oddDegreeCount > 2L ? NOT_EULERIAN : (oddDegreeCount == 2L ? EULERIAN_PATH : EULERIAN_CYCLE);
  }

  private boolean isConnected() {
    final Optional<V> anyVertexMaybe = graph.getAny();
    if (!anyVertexMaybe.isPresent()) {
      return true;
    }

    final Set<V> nonZeroDegreeVertices = graph.getAllNonZeroDegreeVertex();
    if (nonZeroDegreeVertices.size() == 0) {
      return true;
    }

    final DFS<V> dfs = new DFS<>(graph);
    return dfs.perform(nonZeroDegreeVertices.iterator().next()).size() == graph.size();
  }
}
