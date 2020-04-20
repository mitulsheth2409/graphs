package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFS<V extends Comparable<V>> {
  private final Graph<V> graph;
  private boolean isConnected;
  public DFS(final Graph<V> graph) {
    this.graph = graph;
  }

  public static void main(String[] args) {
    final Graph<Integer> graph = new Graph<>(true);
    graph.addEdge(0, 3);
    graph.addEdge(0, 2);
    graph.addEdge(1, 0);
    graph.addEdge(2, 1);
    graph.addEdge(4, 0);
    graph.addEdge(3, 4);
    graph.addEdge(4, 5);
    graph.addEdge(5, 1);
    final DFS<Integer> dfs = new DFS<>(graph);
    System.out.println(graph);
    System.out.println(graph.size());
    System.out.println(dfs.perform(0));
    System.out.println(dfs.performRecursively(0));
  }

  public List<V> perform(final V start) {
    final List<V> result = new ArrayList<>();
    final Stack<V> stack = new Stack<>();
    final Set<V> visited = new HashSet<>();
    stack.add(start);
    while (!stack.empty()) {
      final V node = stack.pop();
      if (!visited.contains(node)) {
        result.add(node);
        visited.add(node);
      }
      graph.getOutgoing(node)
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(stack::push);
    }
    return result;
  }

  public List<V> performRecursively(final V start) {
    final List<V> result = new ArrayList<>();
    final Set<V> visited = new HashSet<>();
    performDFS(start, visited, result);
    return result;
  }

  private void performDFS(
    final V node,
    final Set<V> visited,
    final List<V> result
  ) {
    visited.add(node);
    result.add(node);
    graph.getOutgoing(node)
      .stream()
      .filter(n -> !visited.contains(n))
      .forEach(n -> performDFS(n, visited, result));
  }
}
