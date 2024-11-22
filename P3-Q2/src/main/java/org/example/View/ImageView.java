package org.example.View;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageView extends JFrame {
    private final JLabel imageLabel;
    private JMenuItem openFileItem;
    private JMenuItem exitItem;

    public ImageView() {
        setTitle("BMP Viewer");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(imageLabel, BorderLayout.CENTER);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        openFileItem = new JMenuItem("Open File");
        styleButtonItem(openFileItem, new Color(165, 182, 141));

        exitItem = new JMenuItem("Exit");
        styleButtonItem(exitItem, new Color(160, 35, 52));

        menuBar.add(openFileItem);
        menuBar.add(exitItem);
        setJMenuBar(menuBar);
    }

    private void styleButtonItem(JMenuItem menuItem, Color color) {
        menuItem.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY, 1),
                            new EmptyBorder(5, 20, 5, 10)));
        menuItem.setBackground(color);
        menuItem.setOpaque(true);
        menuItem.setPreferredSize(new Dimension(50, 50));

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(color);
            }
        });
    }

    public void updateImage(BufferedImage image) {
        if (image != null) {
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
            imageLabel.repaint();
        }
    }

    public void addOpenFileButtonListener(ActionListener listener) {
        openFileItem.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitItem.addActionListener(listener);
    }

    public File showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("BMP Images", "bmp"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
