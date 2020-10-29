package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CloneGraph {
  private static class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
      val = 0;
      neighbors = new ArrayList<>();
    }

    public Node(int _val) {
      val = _val;
      neighbors = new ArrayList<>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
      val = _val;
      neighbors = _neighbors;
    }
  }

  public Node cloneGraph(Node node) {
    if (node == null) {
      return node;
    }
    final Set<Integer> visited = new HashSet<>();
    final Map<Integer, Node> result = new HashMap<>();
    final Node newRoot = new Node(node.val, new ArrayList<>());
    result.put(node.val, newRoot);
    final Queue<Node> queue = new LinkedList<>();
    queue.add(node);
    visited.add(node.val);
    while (!queue.isEmpty()) {
      final Node current = queue.poll();
      final List<Node> lst = result.get(current.val).neighbors;
      current.neighbors.stream().forEach(vertex -> {
        if (visited.contains(vertex.val)) {
          lst.add(result.get(vertex.val));
        } else {
          final Node newNode = new Node(vertex.val, new ArrayList<>());
          lst.add(newNode);
          visited.add(vertex.val);
          queue.add(vertex);
          result.put(vertex.val, newNode);
        }
      });
    }
    return result.get(node.val);
  }

  public static void main(String[] args) {
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    List<Node> v = new ArrayList<>();
    v.add(node2);
    v.add(node4);
    node1.neighbors = v;
    v = new ArrayList<>();
    v.add(node1);
    v.add(node3);
    node2.neighbors = v;
    v = new ArrayList<>();
    v.add(node2);
    v.add(node4);
    node3.neighbors = v;
    v = new ArrayList<>();
    v.add(node3);
    v.add(node1);
    node4.neighbors = v;
    new CloneGraph().cloneGraph(node1).neighbors.forEach(n -> System.out.println(n.val));
  }
}
