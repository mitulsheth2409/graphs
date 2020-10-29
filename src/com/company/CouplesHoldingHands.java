package com.company;

import java.util.HashMap;
import java.util.Map;

public class CouplesHoldingHands {
  public static void main(String[] args) {
    final int[] arr = {0, 4, 1, 2, 3, 5};
    System.out.println(getMinSwaps(arr));
    System.out.println(getMinSwapsMapMethod(arr));
  }

  private static int getMinSwapsMapMethod(final int[] rows) {
    final Map<Integer, Integer> hmap = new HashMap<>();
    int index = 0;
    while (index < rows.length) {
      hmap.put(rows[index], rows[index + 1]);
      hmap.put(rows[index + 1], rows[index]);
      index += 2;
    }
    System.out.println(hmap);
    index = 0;
    int swaps = 0;
    while (index < rows.length) {
      if (hmap.get(index) != index + 1) {
        int currentValue = hmap.get(index);
        int nextValue = hmap.get(index + 1);
        hmap.put(index, index + 1);
        hmap.put(currentValue, nextValue);
        hmap.put(nextValue, currentValue);
        System.out.println(hmap);
        swaps++;
      }
      index += 2;
    }
    return swaps;
  }

  private static int getMinSwaps(final int[] rows) {
    final int n = rows.length / 2;
    int swaps = 0;
    final int[] groups = new int[rows.length];
    for (int i = 0; i < n; i++) {
      groups[2 * i] = i;
      groups[2 * i + 1] = i;
    }

    for (int i = 0; i < n; i++) {
      final int person1 = rows[2 * i];
      final int person2 = rows[2 * i + 1];
      final int couple1 = person1 / 2;
      final int couple2 = person2 / 2;
      if (couple1 != couple2) {
        if (groups[person1] != groups[person2]) {
          swaps++;
          union(groups, person1, person2);
        }
      }
    }
    return swaps;
  }

  private static void union(final int[] groups, final int person1, final int person2) {
    final int group = groups[person1];
    for (int i = 0; i < groups.length; i++) {
      if (group == groups[i]) {
        groups[i] = groups[person2];
      }
    }
  }
}
