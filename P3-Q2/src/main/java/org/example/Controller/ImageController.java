package org.example.Controller;

import org.example.Model.ImageModel;
import org.example.View.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageController {
    private final ImageModel model;
    private final ImageView view;

    public ImageController(ImageModel model, ImageView view) {
        this.model = model;
        this.view = view;
        view.setVisible(true);
    }
    public void init(){
        view.addOpenFileButtonListener(e -> handleFileOpen());
        view.addExitButtonListener(e -> System.exit(0));
    }
    private void handleFileOpen() {
        File selectedFile = view.showOpenFileDialog();
        if (selectedFile != null) {
            try {
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                model.readBmpFile(selectedFile);
                BufferedImage image = model.getImage();
                double ratio = model.getCompressionRatio();
                System.out.println("compression ratio: " + ratio);
                view.updateImage(image, ratio);
            } catch (IOException e) {
                view.showError("Error loading BMP file: " + e.getMessage());
            }
        }
    }
}
