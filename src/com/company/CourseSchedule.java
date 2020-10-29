//package com.company;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Stack;
//
//public class CourseSchedule {
//  public static void main(String[] args) {
//    Arrays.stream(findOrder(4, new int[][] {
//      {1, 0}, {2, 0}, {3, 1}, {3, 2} // {course, prerequisite}
//    })).forEach(num -> System.out.print(num + " "));
//  }
//
//  public static int[] findOrder(final int numCourses, final int[][] prerequisites) {
//    final Map<Integer, List<Integer>> hmap = new HashMap<>();
//    for (int i = 0; i < numCourses; i++) {
//      hmap.put(i, new ArrayList<>());
//    }
//    for (final int[] prerequisite : prerequisites) {
//      hmap.get(prerequisite[1]).add(prerequisite[0]);
//    }
//    final Stack<Integer> order = new Stack<>();
//    final Set<Integer> visited = new HashSet<>();
//    for (final Map.Entry<Integer, List<Integer>> entry : hmap.entrySet()) {
//      if (!topologicalOrdering(entry.getKey(), order, visited, new HashSet<>(), hmap)) {
//        return new int[0];
//      }
//    }
//    final int[] result = new int[numCourses];
//    int i = 0;
//    while (!order.empty()) {
//      result[i] = order.pop();
//      i++;
//    }
//    return result;
//  }
//
//  public static boolean topologicalOrdering(
//    final Integer key,
//    final Stack<Integer> order,
//    final Set<Integer> visited,
//    final Set<Integer> loopy,
//    final Map<Integer, List<Integer>> hmap
//  ) {
//    if (visited.contains(key)) {
//      return true;
//    }
//    if (loopy.contains(key)) {
//      return false;
//    }
//    loopy.add(key);
//    for (final Integer adj : hmap.get(key)) {
//      if (!topologicalOrdering(adj, order, visited, loopy, hmap)) {
//        return false;
//      }
//    }
//    visited.add(key);
//    order.push(key);
//    return true;
//  }
//
//  public static int[] findOrderBFS(final int numCourses, final int[][] prerequisites) {
//    if (numCourses == 0) return null;
//
//    //
//    final int[] indegree = new int[numCourses];
//    final int[] order = new int[numCourses];
//
//    for (int i = 0; i < prerequisites.length; i++) {
//      indegree[prerequisites[i][0]]++;
//    }
//  }
//
//}
