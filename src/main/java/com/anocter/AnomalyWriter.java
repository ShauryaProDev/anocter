package com.anocter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.opencv.videoio.VideoWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AnomalyWriter {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Anomaly.class, new AnomalySerializer())
            .registerTypeAdapter(Anomaly.class, new AnomalyDeserializer())
            .setPrettyPrinting()
            .create();

    private final Path srcFile;
    private final Path srcDir;

    public AnomalyWriter(String srcDir) throws IOException {
        this.srcDir = Path.of(srcDir);
        this.srcFile = this.srcDir.resolve("anocter_anom.json");

        if (!this.srcDir.toFile().exists()) {
            Files.createDirectory(this.srcDir);

        } else if (!this.srcFile.toFile().exists()) {
            Files.createFile(this.srcFile);
        }
    }

    public void writeAnomaly(Anomaly anomaly) {
        List<Anomaly> anomalies = readAnomalies();
        anomalies.add(anomaly);

        try (BufferedWriter writer = Files.newBufferedWriter(this.srcFile, StandardCharsets.UTF_8)) {
                writer.write(gson.toJson(anomalies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeVideo(List<Anomaly> anomalies) {
        if (anomalies == null || anomalies.isEmpty()) {
            return;
        }

        Anomaly first = anomalies.getFirst();

        String fileName = "output-" +
                first.capturedTime().toEpochMilli() +
                ".mp4";

        Path output = this.srcDir.resolve(fileName);

        VideoWriter writer = new VideoWriter(
                output.toString(),
                VideoWriter.fourcc('m', 'p', '4', 'v'),
                30,
                first.frame().size()
        );

        if (!writer.isOpened()) {
            throw new IllegalStateException(
                    "Could not open video writer: " + output
            );
        }

        try {
            for (Anomaly anomaly : anomalies) {
                writer.write(anomaly.frame());
            }
        } finally {
            writer.release();
        }
    }

    private List<Anomaly> readAnomalies() {
        try (BufferedReader reader = Files.newBufferedReader(this.srcFile, StandardCharsets.UTF_8)) {
            List<Anomaly> anomalies = gson.fromJson(reader, new TypeToken<List<Anomaly>>() {}.getType());

            return anomalies != null && !anomalies.isEmpty() ? anomalies : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
