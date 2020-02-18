package utils;

import java.time.Duration;

public class DurationSample {

    private final double duration;
    private final double minLimit;
    private final double maxLimit;
    private final double deviation;

    public DurationSample(double duration, Duration minLimit, Duration maxLimit, double deviation) {
        this.duration = duration;
        this.minLimit = minLimit.getSeconds();
        this.maxLimit = maxLimit.getSeconds();
        this.deviation = deviation;
    }

    public DurationSample(double duration, Duration expectedDuration, double deviation) {
        this(duration, expectedDuration, expectedDuration, deviation);
    }

    public DurationSample(double duration, long expectedDuration, double deviation) {
        this(duration, Duration.ofSeconds(expectedDuration), deviation);
    }

    public DurationSample(double duration, long expectedDuration) {
        this(duration, Duration.ofSeconds(expectedDuration));
    }

    public DurationSample(double duration, Duration expectedDuration) {
        this(duration, expectedDuration, expectedDuration, 0);
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
