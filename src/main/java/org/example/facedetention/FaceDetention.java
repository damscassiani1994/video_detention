package org.example.facedetention;

import org.example.interfaces.IDetention;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceDetention implements IDetention {
    private static final String LBP_PATH = "res/lbpcascade/";

    @Override
    public void excute() {
        // Create face detector using LBP (Local Binary Patterns) cascade classifier
        CascadeClassifier faceCascade = loadCascadeClassifier("lbpcascade_frontalface.xml");

        if (faceCascade.empty()) {
            System.out.println("Error loading face cascade classifier.");
            return;
        }


        VideoCapture capture = new VideoCapture(0); // Open the default camera (0)
        if (!capture.isOpened()) {
            System.out.println("Error opening camera.");
        }

        Mat frame = new Mat(); // Create a Mat object to hold the captured frame
        while (true) {
            if (capture.read(frame)) {

                MatOfRect faces = new MatOfRect(); // Create a MatOfRect to hold detected faces
                faceCascade.detectMultiScale(frame, faces); // Detect faces in the captured frame

                // Draw rectangles around detected faces
                faces.toList().forEach(rect ->
                    Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255)));

                HighGui.imshow("Video", frame); // Display the captured frame in a window
                int key = HighGui.waitKey(50); // Wait for 30 milliseconds for a key press
                if (key != -1 && key != 27) { // If a key is pressed
                    System.out.println("Key pressed: " + key);
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

    private CascadeClassifier loadCascadeClassifier(String fileName) {
        CascadeClassifier classifier = new CascadeClassifier();
        String fullPath = LBP_PATH + fileName;
        if (!classifier.load(fullPath)) {
            System.out.println("Error loading cascade classifier from: " + fullPath);
        }
        return classifier;
    }
}
