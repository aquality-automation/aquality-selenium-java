package utils;
import java.util.function.Supplier;

public class Timer{
    private double startTime;
    private final Supplier<Double> getCurrentTime;

    public Timer(){
        getCurrentTime = Timer::getCurrentTimeInSeconds;
    }

    public Timer(TimeUnit timeUnit){
        switch (timeUnit){
            case MILLISECONDS:
                getCurrentTime = Timer::getCurrentTimeInMilliseconds;
                break;
            case SECONDS:
                getCurrentTime = Timer::getCurrentTimeInSeconds;
                break;
            default:
                throw new IllegalArgumentException("Unknown TimeUnit " + timeUnit);
        }
    }

    /**
     * timer can be started only once
     */
    public void start(){
        if(startTime == 0){
            startTime = getCurrentTime.get();
        }
    }

    /**
     * @return duration from the start to current moment
     */
    public double duration(){
        return getCurrentTime.get() - startTime;
    }

    public enum TimeUnit{
        SECONDS, MILLISECONDS
    }

    private static double getCurrentTimeInSeconds(){
        return System.nanoTime()/Math.pow(10,9);
    }

    private static double getCurrentTimeInMilliseconds(){
        return System.nanoTime()/Math.pow(10,6);
    }
}