package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<V extends Comparable<V>> {
    private static class Node<V extends Comparable<V>> {
        private final Set<V> incomingNeighbors;
        private final Set<V> outgoingNeighbors;
        private final V data;

        public Node(final V data) {
            incomingNeighbors = new HashSet<>();
            outgoingNeighbors = new HashSet<>();
            this.data = data;
        }

        public Set<V> getIncomingNeighbors() {
            return incomingNeighbors;
        }

        public Set<V> getOutgoingNeighbors() {
            return outgoingNeighbors;
        }

        public V getData() {
            return data;
        }

        public void addIncomingNeighbor(V newNode) {
            incomingNeighbors.add(newNode);
        }

        public void addOutgoingNeighbor(V newNode) {
            outgoingNeighbors.add(newNode);
        }

        public int getIndegree() {
            return incomingNeighbors.size();
        }

        public int getOutDegree() {
            return outgoingNeighbors.size();
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<V> other = (Node<V>) o;
            return data.equals(other.data);
        }

        @Override
        public String toString() {
            return new StringBuilder()
              .append("Incoming: ")
              .append(incomingNeighbors.toString())
              .append("; ")
              .append("Outgoing: ")
              .append(outgoingNeighbors.toString())
              .toString();
        }
    }

    private final boolean isDirected;
    private final Map<V, Node<V>> graph = new HashMap<>();
    private int size;
    private int numEdges;

    public Graph(final boolean isDirected) {
        this.isDirected = isDirected;
    }

    public void addVertex(V data) {
        addNode(data);
    }

    public void removeVertex(V data) {
        if (graph.containsKey(data)) {
            graph.remove(data);
            vertices().stream().forEach(vertex -> {
                final Node<V> node = graph.get(vertex);
                node.getOutgoingNeighbors().remove(data);
                node.getIncomingNeighbors().remove(data);
            });
        }
    }

    public void addEdge(V source, V target) {
        final Node<V> sourceNode = addNode(source);
        final Node<V> targetNode = addNode(target);
        sourceNode.addOutgoingNeighbor(target);
        targetNode.addIncomingNeighbor(source);
        numEdges++;
        if (!isDirected) {
            sourceNode.addIncomingNeighbor(target);
            targetNode.addOutgoingNeighbor(source);
            numEdges++;
        }
    }

    public void removeEdge(V source, V target) {
        existenceCheck(source);
        existenceCheck(target);
        final Node<V> sourceNode = graph.get(source);
        final Node<V> targetNode = graph.get(target);
        sourceNode.getOutgoingNeighbors().remove(target);
        targetNode.getIncomingNeighbors().remove(source);
        if (!isDirected) {
            sourceNode.getIncomingNeighbors().remove(target);
            targetNode.getOutgoingNeighbors().remove(source);
        }
    }

    public Set<V> vertices() {
        return graph.keySet();
    }

    public Set<V> getIncoming(V data) {
        existenceCheck(data);
        return graph.get(data).getIncomingNeighbors();
    }

    public Set<V> getOutgoing(V data) {
        existenceCheck(data);
        return graph.get(data).getOutgoingNeighbors();
    }

    public int inDegree(V data) {
        existenceCheck(data);
        return graph.get(data).getIndegree();
    }

    public int outDegree(V data) {
        existenceCheck(data);
        return graph.get(data).getOutDegree();
    }

    public int degree(V data) {
        final int degree = inDegree(data) + outDegree(data);
        return isDirected ? degree : degree / 2;
    }

    public int numOfEdges() {
        return numEdges;
    }

    public int size() {
        return size;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public Set<V> getAllZeroDegreeVertices() {
        return graph.keySet().stream()
          .filter(vertex -> degree(vertex) == 0)
          .collect(Collectors.toSet());
    }

    public Set<V> getAllNonZeroDegreeVertex() {
        return graph.keySet().stream()
          .filter(vertex -> !getAllZeroDegreeVertices().contains(vertex))
          .collect(Collectors.toSet());
    }

    public Optional<V> getAny() {
        return size == 0 ? Optional.empty() : Optional.of(graph.keySet().iterator().next());
    }

    private Node<V> addNode(V data) {
        if (graph.containsKey(data)) {
            return graph.get(data);
        }
        final Node<V> newNode = new Node<>(data);
        graph.put(data, newNode);
        size++;
        return newNode;
    }

    private void existenceCheck(final V data) {
        if (!graph.containsKey(data)) {
            throw new IllegalArgumentException(String.format("Vertex %s does not exist", data));
        }
    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
