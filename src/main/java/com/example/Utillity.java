package com.example;

public class Utillity {
    public static double getRunTimeInSeconds(double startTime) {
        long endTime = System.nanoTime();
        double runTime = endTime - startTime;
        double runTimeInSeconds = runTime / 1_000_000_000.0;
        return runTimeInSeconds;
    }
}
