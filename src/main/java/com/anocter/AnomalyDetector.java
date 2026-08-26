package com.anocter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AnomalyDetector {
    public void detect(int source, AnomalyWriter writer, DetectionType detectionType) {
        VideoCapture camera = new VideoCapture(source);
        List<Anomaly> anomalies = new ArrayList<>();

        if (!camera.isOpened()) {
            System.out.println("Failed to open camera");
            return;
        }

        Mat currentFrame = new Mat();
        Mat previousFrame = new Mat();
        Mat difference = new Mat();

        camera.read(previousFrame);

        while (true) {
            if (!camera.read(currentFrame)) {
                System.out.println("Failed to read frame");
                break;
            }

            Core.absdiff(previousFrame, currentFrame, difference);

            Scalar sum = Core.sumElems(difference);

            double diff = sum.val[0] + sum.val[1] + sum.val[2];
            double normalizedDiff = diff / 10000;

            if (normalizedDiff > 400) {
                if (detectionType == DetectionType.BEEP) new Thread(BeepType.WARNING.getBeep()).start();

                Anomaly anomaly = new Anomaly(currentFrame.clone(), Instant.now(), normalizedDiff);

                for (List<Anomaly> anomalies_ : AnomalySeparator.separate(anomalies)) {
                    writer.writeVideo(anomalies_);
                }

                anomalies.add(anomaly);
                writer.writeAnomaly(anomaly);
            }

            currentFrame.copyTo(previousFrame);
        }

        camera.release();
    }

    public void detect(AnomalyWriter writer, DetectionType  detectionType) {
        detect(0, writer, detectionType);
    }
}
