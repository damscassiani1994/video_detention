package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.example.videocapture.FirstVideo;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.dnn.Net;
import org.opencv.highgui.HighGui;
import org.opencv.osgi.OpenCVInterface;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Main
{

    private static final String YOLO_PATH = "/Users/damaso/Documents/Proyectos/opencv/yolo/yolov3";
    private static final String LIB_PATH = "/Users/damaso/Documents/Proyectos/java/IA/objects_detection/video_detection/libs";
    static {
        System.load(LIB_PATH.concat("/libopencv_java4110.dylib"));
    }
    private static List<String> getOutputNames(Net net) {
        List<String> names = new ArrayList<>();

        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();


        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));//unfold and create R-CNN layers from the loaded YOLO model//
        return names;
    }
    public static void main( String[] args )
    {

        System.out.println("OpenCV version: " + Core.VERSION);
        String modelWeights =  YOLO_PATH + "/yolov3.weights";//Download and load only wights for YOLO , this is obtained from official YOLO site//
        String modelConfiguration = YOLO_PATH + "/yolov3.cfg";//Download and load cfg file for YOLO , can be obtained from official site//

        FirstVideo firstVideo = new FirstVideo();
        firstVideo.Excute();
    }
}

