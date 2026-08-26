package com.anocter;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class AnomalySeparator {
    private static final long INTERVAL_MILLIS = 3000;
    private static final long TOLERANCE_MILLIS = 5;

    public static List<List<Anomaly>> separate(List<Anomaly> anomalies) {

        List<List<Anomaly>> result = new ArrayList<>();

        if (anomalies.isEmpty()) {
            return result;
        }

        List<Anomaly> group = new LinkedList<>();
        Instant groupStart = anomalies.getFirst().capturedTime();

        for (Anomaly anomaly : anomalies) {
            long elapsed = Duration.between(
                    groupStart,
                    anomaly.capturedTime()
            ).toMillis();

            if (elapsed > INTERVAL_MILLIS + TOLERANCE_MILLIS) {
                result.add(group);

                group = new LinkedList<>();
            }
            group.add(anomaly);
            groupStart = anomaly.capturedTime();
        }

        result.add(group);

        return result;
    }
}
