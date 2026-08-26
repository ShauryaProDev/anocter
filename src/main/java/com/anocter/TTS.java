package com.anocter;

import javax.sound.sampled.*;
import java.io.*;

public class TTS {
    public static void speech(String text) throws Exception {
        Process process = new ProcessBuilder(
                "piper",
                "--model", Globals.dotEnv.get("SPEECH_MODEL_PATH"),
                "--output-raw"
        )
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();

        try (Writer writer = new OutputStreamWriter(
                process.getOutputStream()
        )) {
            writer.write(text);
            writer.write("\n");
        }

        AudioFormat format = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                26000,
                16,
                1,
                2,
                26000,
                false
        );

        DataLine.Info info =
                new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "Audio format not supported: " + format
            );
        }

        SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);

        speakers.open(format);
        speakers.start();

        byte[] buffer = new byte[8192];

        int bytesRead;

        try (InputStream audio = process.getInputStream()) {
            while ((bytesRead = audio.read(buffer)) != -1)
                if (bytesRead > 0)
                    speakers.write(buffer, 0, bytesRead);
        }

        speakers.drain();
        speakers.stop();
        speakers.close();
    }
}