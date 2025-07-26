package org.example.videocapture;

import org.example.interfaces.IDetention;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.Map;

public class FirstVideo implements IDetention {

    private static final String PATH_RESOURCES = "res/images/";

    @Override
    public void excute() {
        VideoCapture capture = new VideoCapture(0); // Open the default camera (0)
        if (!capture.isOpened()) {
            System.out.println("Error opening camera.");
        }

        Mat frame = new Mat(); // Create a Mat object to hold the captured frame
        int valueActual = 0;
        while (true) {
            if (capture.read(frame)) {
                if (getFiltersoMap().containsKey(valueActual)) { // Check if the key is in the filter map and not for saving
                    Imgproc.cvtColor(frame, frame, getFilter(valueActual)); // Apply the selected filter
                }

                HighGui.imshow("Video", frame); // Display the captured frame in a window
                int key = HighGui.waitKey(50); // Wait for 30 milliseconds for a key press
                if (key != -1 && key != 27 && key != 53) { // If a key is pressed
                    System.out.println("Key pressed: " + key);
                    valueActual = key;
                }

                //Take a photo and save it
                if (key == 53) { // Example condition to save the frame
                    String filename = "captured_frame.png"; // Define the filename
                    Imgcodecs.imwrite(PATH_RESOURCES.concat(filename), frame); // Save the captured frame as an image file
                    System.out.println("Frame saved as: " + filename);
                }

                if (key == 27) { // Wait for 'Esc' key to exit
                    System.out.println("Frame captured: " + frame);
                    break;
                }

            }
        }

        capture.release(); // Release the camera
        HighGui.destroyAllWindows(); // Close all OpenCV windows
    }

    private int getFilter(int key) {
        return getFiltersoMap().get(key);
    }

    private Map<Integer, Integer> getFiltersoMap() {
        return  Map.of(
                48, Imgproc.COLOR_BGRA2BGR, // Convert BGRA to BGR 0
                49, Imgproc.COLOR_BGR2GRAY, // Grayscale 1
                50, Imgproc.CV_CHAIN_CODE, // Example filter (not a real OpenCV filter) 2
                51, Imgproc.COLOR_BGR2HSV, // Convert BGR to HSV 3
                52, Imgproc.CV_COMP_CHISQR_ALT // Example filter (not a real OpenCV filter) 4
        );
    }
}
