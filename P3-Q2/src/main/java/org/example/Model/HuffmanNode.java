package org.example.Model;

import java.util.PriorityQueue;

class HuffmanNode implements Comparable<HuffmanNode> {
    int value;  // Pixel value
    int frequency;  // Frequency of the pixel
    HuffmanNode left, right;

    public HuffmanNode(int value, int frequency) {
        this.value = value;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return Integer.compare(this.frequency, o.frequency);
    }
}

