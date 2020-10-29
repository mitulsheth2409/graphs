package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TransitiveClosure {
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
    graph.addEdge(12, 13);
    System.out.println(getTranstitiveClosure(graph));
  }

  private static Map<TransitiveClosurePoints, Boolean> getTranstitiveClosure(final Graph<Integer> graph) {
    final Map<TransitiveClosurePoints, Boolean> transitiveClosure = new HashMap<>();
    graph.vertices().forEach(vertex -> getTransitiveClosureUtil(graph, vertex, vertex, transitiveClosure));
    return transitiveClosure;
  }

  private static void getTransitiveClosureUtil(
    final Graph<Integer> graph,
    final Integer source,
    final Integer dest,
    final Map<TransitiveClosurePoints, Boolean> transitiveClosure
  ) {
    transitiveClosure.put(new TransitiveClosurePoints(source, dest), true);
    graph.getOutgoing(source)
      .stream()
      .map(adj -> new TransitiveClosurePoints(source, adj))
      .filter(tc -> !transitiveClosure.containsKey(tc))
      .forEach(tc -> getTransitiveClosureUtil(graph, tc.src, tc.dest, transitiveClosure));
  }

  private static class TransitiveClosurePoints {
    final Integer src;
    final Integer dest;

    public TransitiveClosurePoints(Integer src, Integer dest) {
      this.src = src;
      this.dest = dest;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof TransitiveClosurePoints)) return false;
      TransitiveClosurePoints that = (TransitiveClosurePoints) o;
      return Objects.equals(src, that.src) &&
        Objects.equals(dest, that.dest);
    }

    @Override
    public int hashCode() {
      return Objects.hash(src, dest);
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("TransitiveClosurePoints{");
      sb.append("src=").append(src);
      sb.append(", dest=").append(dest);
      sb.append('}');
      return sb.toString();
    }
  }
}
