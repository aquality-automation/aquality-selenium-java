package utils;
import static utils.TimeUtil.*;

public class Timer{

    private double startTime;

    public void start(){
        if(startTime == 0){
            startTime = getCurrentTimeInSeconds();
        }
    }

    public double stop(){
        return getCurrentTimeInSeconds() - startTime;
    }
}