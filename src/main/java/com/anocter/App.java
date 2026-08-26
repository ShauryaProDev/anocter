package com.anocter;

import nu.pattern.OpenCV;
import java.io.IOException;
import java.util.List;

public class App {
    static void main() throws IOException {
        OpenCV.loadLocally();

        AnomalyDetector detector = new AnomalyDetector();
        AnomalyWriter writer = new AnomalyWriter("C:\\Users\\Shaurya-PC\\Documents\\sample");

        detector.detect(writer, DetectionType.SILENT);
    }
}
