package com.anocter;

import nu.pattern.OpenCV;
import java.io.IOException;

public class App {
    static void main() throws IOException {
        OpenCV.loadLocally();

        AnomalyDetector detector = new AnomalyDetector();
        AnomalyWriter writer = new AnomalyWriter("Your Location To Save Footages");

        detector.detect(writer, DetectionType.BEEP);
    }
}
