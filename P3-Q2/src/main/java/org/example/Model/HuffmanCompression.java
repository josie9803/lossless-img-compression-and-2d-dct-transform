package org.example.Model;

import java.util.*;

public class HuffmanCompression {
    private final Map<Integer, String> huffmanCodes = new HashMap<>();

    public void compress(int[] data){
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        buildTree(frequencyMap);
    }

    private void buildTree(Map<Integer, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

        // Create a node for each pixel value and add to the priority queue
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            // Merge nodes
            HuffmanNode parent = new HuffmanNode(-1, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            pq.add(parent);
        }

        // Root of the tree
        HuffmanNode root = pq.poll();
        generateCodes(root, "");
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        // If it's a leaf node, store the code
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.pixelValue, code);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public Map<Integer, String> getHuffmanCodes() {
        return huffmanCodes;
    }
}

