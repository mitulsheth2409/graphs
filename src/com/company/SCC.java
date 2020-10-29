package com.company;

/**
 * Strongly Connected Components
 */
public class SCC {
  public static void main(String[] args) {
    final Graph<Integer> graph = new Graph<>(true);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(0, 3);
    graph.addEdge(3, 4);
    Graph<Integer> transposeGraph = graph.getTranspose();
  }

}
