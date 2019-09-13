package utils;
import java.util.function.Supplier;

public class Timer{
    private double startTime;
    private final Supplier<Double> getCurrentTime;

    public Timer(){
        getCurrentTime = TimeUtil::getCurrentTimeInSeconds;
    }

    public Timer(TimeUnit timeUnit){
        switch (timeUnit){
            case MILLISECONDS:
                getCurrentTime = TimeUtil::getCurrentTimeInMilliseconds;
                break;
            case SECONDS:
                getCurrentTime = TimeUtil::getCurrentTimeInSeconds;
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

    public double stop(){
        return getCurrentTime.get() - startTime;
    }

    public static boolean isDurationBetweenLimits(double duration, double minLimit, double maxLimit, double deviation) {
        return duration >= (minLimit - deviation) && duration <= (maxLimit + deviation);
    }

    public enum TimeUnit{
        SECONDS, MILLISECONDS
    }
}