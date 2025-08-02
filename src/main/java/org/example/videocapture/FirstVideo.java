package org.example.videocapture;

import org.example.interfaces.IDetention;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.Map;

import static org.example.util.DetentionConstant.PATH_RESOURCES;

public class FirstVideo implements IDetention {



    @Override
    public void excute() {
        VideoCapture capture = new VideoCapture(0); // Open the default camera (0)
        if (!capture.isOpened()) {
            System.out.println("Error opening camera.");
        }

        Mat frame = new Mat(); // Create a Mat object to hold the captured frame
        int valueActual = 0;
        int pressedKey = 0;
        double alpha = 1.3;
        while (true) {
            if (capture.read(frame)) {
                if (getFiltersoMap().containsKey(valueActual)) { // Check if the key is in the filter map and not for saving
                    Imgproc.cvtColor(frame, frame, getFilter(valueActual)); // Apply the selected filter
                }

                // Process key presses for contrast and brightness
                //frame = pressKeyProcess(pressedKey, alpha, valueActual, frame);

                if (HighGui.pressedKey == 93 || HighGui.pressedKey == 91) {
                    pressedKey = HighGui.pressedKey;
                    if (HighGui.pressedKey == 93) {
                        alpha += 0.05D; // Increase contrast and brightness
                    }
                    if (HighGui.pressedKey == 91){
                        alpha -= 0.05D; // Decrease contrast and brightness
                    }

                } else if (valueActual == 48){ // Reset alpha to 0 when '0' key is pressed
                    System.out.println("alpha cambiado");
                    alpha = 1.3;
                    pressedKey = 0; // Reset pressedKey to avoid changing contrast and brightness
                }


                if (pressedKey == 93 || pressedKey == 91) {
                    frame = changeContrastAndBrightness(frame, alpha); // Change contrast and brightness
                    System.out.println("Contrast and brightness changed.");
                }

                HighGui.imshow("Video", frame); // Display the captured frame in a window
                int key = HighGui.waitKey(50); // Wait for 30 milliseconds for a key press
                if (key != -1 && key != 27 && key != 53) { // If a key is pressed
                    System.out.println("Key pressed: " + key);
                    valueActual = key;
                }

                //Take a photo and save it if pressed the key '5' (ASCII 53)
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

    /**
     * Change the contrast and brightness of the captured frame.
     * @param frame The captured frame to modify.
     * @return The modified frame with adjusted contrast and brightness.
     */
    private Mat changeContrastAndBrightness(Mat frame, double alpha) {
        System.out.println("Alpha: "+ alpha);
        double beta = 1 - alpha; //Adjust beta to maintain brightness

        // Create a new Mat with red color
        Mat frameDefault = new Mat(frame.rows(), frame.cols(), CvType.CV_8UC3, new Scalar(255, 0, 0));
        Mat newMat = new Mat();

        Core.addWeighted(frame, alpha,frameDefault, beta, 0, newMat);
        return newMat;
    }
}
