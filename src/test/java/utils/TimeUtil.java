package utils;

public class TimeUtil {

    public static long getCurrentTime(){
        return System.nanoTime();
    }

    public static double getCurrentTimeInSeconds(){
        return System.nanoTime()/Math.pow(10,9);
    }

    public static double calculateDuration(long startTimeNanoSec){
        return (getCurrentTime() - startTimeNanoSec)/Math.pow(10,9);
    }
}
