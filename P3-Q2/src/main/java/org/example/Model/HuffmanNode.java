package org.example.Model;

class HuffmanNode implements Comparable<HuffmanNode> {
    int pixelValue;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(int value, int frequency) {
        this.pixelValue = value;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return Integer.compare(this.frequency, o.frequency);
    }
}

