package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.example.facedetention.FaceDetention;
import org.opencv.core.Core;

/**
 * Hello world!
 *
 */
public class Main
{

    private static final String YOLO_PATH = "/Users/damaso/Documents/Proyectos/opencv/yolo/yolov3";
    private static final String LIB_PATH = "/Users/damaso/Documents/Proyectos/java/IA/objects_detection/video_detention/libs";
    static {
        System.load(LIB_PATH.concat("/libopencv_java4110.dylib"));
    }

    public static void main( String[] args )
    {

        System.out.println("OpenCV version: " + Core.VERSION);
        String modelWeights =  YOLO_PATH + "/yolov3.weights";//Download and load only wights for YOLO , this is obtained from official YOLO site//
        String modelConfiguration = YOLO_PATH + "/yolov3.cfg";//Download and load cfg file for YOLO , can be obtained from official site//

        //FirstVideo firstVideo = new FirstVideo();
        //firstVideo.Excute();

        // Practice for apply face detection using LBP Cascade OpenCV
        FaceDetention faceDetention = new FaceDetention();
        faceDetention.Excute();
    }
}

