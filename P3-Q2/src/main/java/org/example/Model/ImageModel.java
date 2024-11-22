package org.example.Model;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageModel {
    private BufferedImage image;
    public void readBmpFile(File bmpFile) throws IOException {
        // Use Apache Commons Imaging to read the BMP file
        try {
            image = Imaging.getBufferedImage(bmpFile);
        } catch (ImageReadException e) {
            throw new RuntimeException(e);
        }
        if (image == null) {
            throw new IOException("Invalid BMP file.");
        }
    }
    public BufferedImage getImage(){
        return image;
    }

    public double huffmanCompression() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate frequency of each pixel value
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                frequencyMap.put(pixel, frequencyMap.getOrDefault(pixel, 0) + 1);
            }
        }

        // Step 2: Build Huffman Tree
        HuffmanCoding huffmanCoding = new HuffmanCoding();
        huffmanCoding.buildTree(frequencyMap);

        // Step 3: Encode the pixel data
        StringBuilder encodedData = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                encodedData.append(huffmanCoding.getHuffmanCodes().get(pixel));
            }
        }

        // Calculate compression ratio
        int originalBits = width * height * 24; // 24 bits per pixel for RGB
        return (double) originalBits / (double) encodedData.length();
    }

//    public static double predictorHuffmanCompression(BufferedImage image) {
//        // Apply a simple predictor (e.g., left neighbor predictor)
//        int width = image.getWidth();
//        int height = image.getHeight();
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int currentPixel = image.getRGB(x, y);
//                int leftPixel = (x > 0) ? image.getRGB(x - 1, y) : 0;
//                int predictionError = currentPixel - leftPixel;
//
//                frequencyMap.put(predictionError, frequencyMap.getOrDefault(predictionError, 0) + 1);
//            }
//        }
//
//        // Huffman compression on prediction errors
//        HuffmanCoding huffmanCoding = new HuffmanCoding();
//        int compressedBits = huffmanCoding.compress(frequencyMap);
//
//        // Calculate compression ratio
//        int originalBits = width * height * 24; // 24 bits per pixel for RGB
//        return (double) originalBits / compressedBits;
//    }
}
