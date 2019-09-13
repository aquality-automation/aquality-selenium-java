package utils;

public class DurationSample {

    private final double duration;
    private final double minLimit;
    private final double maxLimit;
    private final double deviation;

    public DurationSample(double duration, double minLimit, double maxLimit, double deviation) {
        this.duration = duration;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        this.deviation = deviation;
    }

    public DurationSample(double duration, double expectedDuration, double deviation) {
        this.duration = duration;
        this.minLimit = expectedDuration;
        this.maxLimit = expectedDuration;
        this.deviation = deviation;
    }

    public boolean isDurationBetweenLimits(){
        return duration >= (minLimit - deviation) && duration <= (maxLimit + deviation);
    }

    public double getDuration(){
        return duration;
    }

    @Override
    public String toString(){
        return String.format("Duration: %1$s\nMin Limit: %2$s\nMax Limit: %3$s\nDeviation: %4$s", duration, minLimit, maxLimit, deviation);
    }
}
