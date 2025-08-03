package org.example.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.management.AttributeNotFoundException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public final class DetentionUtil {

    public static BufferedImage getBufferedImageFromMat(Mat mat) {
        if (mat.empty()) {
            throw new RuntimeException("Error: Mat is empty.");
        }

        if (mat.cols() <= 0 || mat.rows() <= 0) {
            throw new RuntimeException("Error: Mat has no columns or rows.");
        }
        //BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        mat.get(0, 0, ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData());
        return bufferedImage;
    }

    public static Mat loadImage(String imagePath) {
        Mat frame =  Imgcodecs.imread(imagePath); // Load an image from file

        if (frame.empty()) {
            System.out.println("Error: Could not load image.");
            throw new RuntimeException("Error: Could not load image from path: " + imagePath);
        }
        return  frame;
    }
}
