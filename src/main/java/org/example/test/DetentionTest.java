package org.example.test;

import org.example.interfaces.IDetention;
import org.example.util.DetentionUtil;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static org.example.util.DetentionConstant.PATH_RESOURCES;

public class DetentionTest implements IDetention {


    @Override
    public void excute() {
        Mat frame =  Imgcodecs.imread(PATH_RESOURCES.concat("image1.jpg")); // Load an image from file

        if (frame.empty()) {
            System.out.println("Error: Could not load image.");
            return;
        }

        System.out.println("Frame value: " + frame);
        // Convert the Mat to BufferedImage for display
        // Can pass as parameter for getBufferedImageFromMat the methods created further down
        BufferedImage bufferedImage = DetentionUtil.getBufferedImageFromMat(changeContrastAndBrightness(frame));
        HighGui.imshow("Girl", frame);

        // Display the image in a JFrame window
        JFrame frameWindow = new JFrame("Image Display");
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.setSize(1000, 800);
        frameWindow.setLocation(200, 300);
        frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
       // frameWindow.pack();
        frameWindow.setVisible(true);
    }

    // Modify the image by cropping a region of interest (ROI)
    private Mat modifyImage(Mat frame) {
        // Example modification: Crop a region of interest (ROI)
        return new Mat(frame, new Rect(10, 20, 100, 100));
    }

    // Create a Mat object with specific dimensions and color
    private Mat createSpecificMat(Scalar color) {
        /*
           2981 = frame rows numbers
           4500 = frame columns numbers
           CvType = CV_[The number of bits per item][Signed or Unsigned][Type Prefix]C[The channel number]
           new Scalar(0, 0, 255) creates a red image
         */
        if (color == null) {
            color = new Scalar(0, 0, 255); // Default to red color (BGR format)
        }
        Mat frame = new Mat(2981, 4500, CvType.CV_8UC3, color);
        System.out.println("Specific Mat created: " + frame);
        return frame;
    }

    private Mat modifyImageChannel(Mat frame) {
        for (int i = 0; i < frame.rows(); i++) {
            for (int j = 0; j < frame.cols(); j++) {
                double[] data = frame.get(i, j);
                // Modify the pixel value as needed
                data[0] += 100; // Set blue channel to maximum
                data[1] -= 50;   // Set green channel to zero
                data[2] = 0;   // Set red channel to zero
                frame.put(i, j, data);
            }
        }

        return frame;
    }


    /*
        * Change the contrast and brightness of the captured frame.
     */
    private Mat changeContrastAndBrightness(Mat frame) {
        double alpha = 2;
        double beta = 1 - alpha; //Adjust beta to maintain brightness
        Mat newMat = new Mat();

        Core.addWeighted(frame, alpha,createSpecificMat(new Scalar(255, 0, 0)), beta, 0, newMat);
        return newMat;
    }

}
