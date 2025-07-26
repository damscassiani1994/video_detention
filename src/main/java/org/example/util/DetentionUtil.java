package org.example.util;

import org.opencv.core.Mat;

import javax.management.AttributeNotFoundException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public final class DetentionUtil {

    public static BufferedImage getBufferedImageFromMat(Mat mat) {
        if (mat.empty()) {
            throw new RuntimeException("Error: Mat is empty.");
        }

        BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        mat.get(0, 0, ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData());
        return bufferedImage;
    }
}
