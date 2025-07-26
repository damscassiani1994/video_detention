package org.example.test;

import org.example.interfaces.IDetention;
import org.example.util.DetentionUtil;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
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
        BufferedImage bufferedImage = DetentionUtil.getBufferedImageFromMat(createSpecificMat());
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
    private Mat createSpecificMat() {
        /*
           2981 = frame rows numbers
           4500 = frame columns numbers
           CvType = CV_[The number of bits per item][Signed or Unsigned][Type Prefix]C[The channel number]
           new Scalar(0, 0, 255) creates a red image
         */
        Mat frame = new Mat(2981, 4500, CvType.CV_8UC3, new Scalar(0, 0, 255));
        System.out.println("Specific Mat created: " + frame);
        return frame;
    }

}
