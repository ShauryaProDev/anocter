package com.anocter;

public class Beep implements Runnable {
    double frequency;
    long duration;

    Beep(double frequency, long duration) {
        System.loadLibrary("BeepNative");

        this.frequency = frequency;
        this.duration = duration;
    }

    private native void beep(double frequency, long duration);

    @Override
    public void run() {
        this.beep(this.frequency, this.duration);
    }
}
