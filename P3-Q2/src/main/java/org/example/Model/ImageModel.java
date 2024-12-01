package org.example.Model;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageModel {
    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;
    private int[] redChannel;
    private int[] greenChannel;
    private int[] blueChannel;
    public BufferedImage getImage(){
        return image;
    }
    public void readBmpFile(File bmpFile) throws IOException {
        try {
            image = Imaging.getBufferedImage(bmpFile);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
        } catch (ImageReadException e) {
            throw new RuntimeException(e);
        }
        if (image == null) {
            throw new IOException("Invalid BMP file.");
        }
    }

    private void extractRGBChannels() {
        redChannel = new int[imageWidth * imageHeight];
        greenChannel = new int[imageWidth * imageHeight];
        blueChannel = new int[imageWidth * imageHeight];

        int index = 0;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int rgb = image.getRGB(x, y);
                redChannel[index] = (rgb >> 16) & 0xFF;
                greenChannel[index] = (rgb >> 8) & 0xFF;
                blueChannel[index] = rgb & 0xFF;
                index++;
            }
        }
//        return new int[][]{redChannel, greenChannel, blueChannel};
    }
    private String compressChannel(int[] channel) {
        extractRGBChannels();
        // Build Huffman tree and generate codes
        HuffmanCompression huffmanCompression = new HuffmanCompression();
        huffmanCompression.compress(channel);

        // Encode the channel
        StringBuilder encodedData = new StringBuilder();
        for (int pixel : channel) {
            encodedData.append(huffmanCompression.getHuffmanCodes().get(pixel));
        }
        return encodedData.toString();
    }
    public String compressWholeImg() {
        //extract pixel data
        int[] data = new int[imageWidth * imageHeight];
        int index = 0;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                 data[index] = image.getRGB(x,y);
                 index++;
            }
        }

        // Step 2: Build Huffman Tree
        HuffmanCompression huffmanCompression = new HuffmanCompression();
        huffmanCompression.compress(data);

        // Step 3: Encode the pixel data
        StringBuilder encodedData = new StringBuilder();
        for (int pixel : data) {
            encodedData.append(huffmanCompression.getHuffmanCodes().get(pixel));
        }
        return encodedData.toString();
    }
    public double getCompressionRatio(){
        int originalBits = image.getWidth() * image.getHeight() * 24;
//        String redCompressed = compressChannel(redChannel);
//        String greenCompressed = compressChannel(greenChannel);
//        String blueCompressed = compressChannel(blueChannel);
//        int compressedSize = redCompressed.length()
//        + greenCompressed.length() + blueCompressed.length();
        int compressedSize = compressWholeImg().length();
        System.out.println("original size: " + originalBits);
        System.out.println("compressed size: " + compressedSize);
        return (double)originalBits / compressedSize;
    }
}
