package com.anocter;

import org.opencv.core.Mat;
import java.time.Instant;

public record Anomaly(Mat frame, Instant capturedTime, double movement) {
}
