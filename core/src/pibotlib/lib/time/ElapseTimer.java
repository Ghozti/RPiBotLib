package pibotlib.lib.time;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ElapseTimer implements Runnable {

    public volatile double elapsedSeconds;
    public volatile long elapsedMilliseconds;
    boolean stopTimer;

    public ElapseTimer(){
        elapsedSeconds = 0;
        elapsedMilliseconds = 0;
    }

    public void startTimer(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stopTimer(){
        stopTimer = true;
    }

    public void reset(){
        elapsedMilliseconds = 0;
        elapsedSeconds = 0;
    }

    public long getElapsedMilliseconds(){
        return elapsedMilliseconds;
    }

    public double getElapsedSeconds(){
        return elapsedSeconds;
    }

    @Override
    public void run() {
        Runnable helloRunnable = () -> {
            elapsedMilliseconds +=  1;
            elapsedSeconds = elapsedMilliseconds/1000d;
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        if (stopTimer){
            executor.shutdown();
        }else {
            executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.MILLISECONDS);
        }
    }
}
