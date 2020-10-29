package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TopologicalSorting {
  private static class CrudeGraph {
    private int vertices;
    private Map<Integer, Set<Integer>> graph;

    public CrudeGraph() {
      graph = new HashMap<>();
    }

    public void addEdge(final int v, final int w) {
      if (!graph.containsKey(v)) {
        graph.put(v, new HashSet<>());
        vertices++;
      }
      if (!graph.containsKey(w)) {
        graph.put(w, new HashSet<>());
        vertices++;
      }
      graph.get(v).add(w);
    }

    public List<Integer> topologicalSort() {
      final Set<Integer> visited = new HashSet<>();
      final Stack<Integer> stack = new Stack<>();
      graph.keySet()
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(n -> dfsTopological(n, stack, visited));
      final List<Integer> result = new ArrayList<>();
      while (!stack.empty()) {
        result.add(stack.peek());
        System.out.print(stack.pop() + " ");
      }
      System.out.println();
      return result;
    }

    private void dfsTopological(
      final Integer node,
      final Stack<Integer> stack,
      final Set<Integer> visited
    ) {
      visited.add(node);
      graph.get(node)
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(n -> dfsTopological(n, stack, visited));
      stack.push(node);
    }

    public List<Integer> topologicalDepartureTime() {
      final Set<Integer> visited = new HashSet<>();
      final TreeMap<Integer, Integer> departure = new TreeMap<>();
      final AtomicInteger time = new AtomicInteger(-1);
      graph.keySet()
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(n -> dfsDeparture(n, time, visited, departure));
      final List<Integer> result = new ArrayList<>(departure.descendingKeySet());
      System.out.println(result);
      return result;
    }

    private void dfsDeparture(
      final Integer node,
      final AtomicInteger time,
      final Set<Integer> visited,
      final TreeMap<Integer, Integer> departure
    ) {
      visited.add(node);
      graph.get(node)
        .stream()
        .filter(n -> !visited.contains(n))
        .forEach(n -> dfsDeparture(n, time, visited, departure));
      time.incrementAndGet();
      departure.put(time.get(), node);
    }

    public List<Integer> topologicalSortKahn() {
      final Map<Integer, Integer> indegrees = new HashMap<>();
      final Queue<Integer> queue = new LinkedList<>();
      graph.keySet().forEach(k -> {
        indegrees.computeIfAbsent(k, c -> 0);
        graph.get(k).forEach(adj -> {
          indegrees.computeIfAbsent(adj, c -> 0);
          indegrees.compute(adj, (key, val) -> val + 1);
        });
      });
      indegrees.entrySet()
        .stream()
        .filter(entry -> entry.getValue().equals(0))
        .forEach(entry -> queue.add(entry.getKey()));

      final List<Integer> result = new ArrayList<>();
      int count = 0;
      while (!queue.isEmpty()) {
        int k = queue.poll();
        result.add(k);
        graph.get(k).forEach(adj -> {
          if (indegrees.get(adj) - 1 == 0) {
            queue.add(adj);
          }
          indegrees.compute(adj, (key, val) -> val - 1);
        });
        count++;
      }

      if (count != vertices) {
        System.out.println("Cycle!");
        return new ArrayList<>();
      }
      System.out.println(result);
      return result;
    }

    public void maxEdgesThatCanBeAddedToDag() {
      int count = 0;
      final List<Integer> topo = topologicalSortKahn();
      final Set<Integer> visited = new HashSet<>();
      for (int i = 0, n = topo.size(); i < n; i++) {
        visited.addAll(graph.get(topo.get(i)));
        for (int j = i + 1; j < n; j++) {
          if (!visited.contains(topo.get(j))) {
            count++;
            System.out.println(topo.get(i) + " - " + topo.get(j));
          }
          visited.remove(topo.get(j));
        }
      }
      System.out.println("Maximum more edges that can be added - " + count);
    }
  }

  // Driver method
  public static void main(String args[]) {
    // Create a graph given in the above diagram
    CrudeGraph g = new CrudeGraph();
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);

    System.out.println(
      "Following is a Topological " +
        "sort of the given graph");
    g.topologicalSort();
    g.topologicalDepartureTime();
    g.topologicalSortKahn();
    g.maxEdgesThatCanBeAddedToDag();
  }
}
