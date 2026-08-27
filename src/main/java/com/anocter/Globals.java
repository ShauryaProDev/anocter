package com.anocter;

import io.github.cdimascio.dotenv.Dotenv;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Globals {
    public static final Dotenv dotEnv = Dotenv
            .configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    public static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd hh:mm:ss.SSS a")
            .withZone(ZoneId.systemDefault());

    public static final ExecutorService executor =  Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 2);
}
