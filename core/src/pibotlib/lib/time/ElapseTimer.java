package pibotlib.lib.time;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *an elapse timer that can count in milliseconds or seconds*/
public class ElapseTimer implements Runnable {

    public volatile double elapsedSeconds;
    public volatile long elapsedMilliseconds;
    volatile boolean stopTimer;

    public ElapseTimer(){
        elapsedSeconds = 0;
        elapsedMilliseconds = 0;
    }

    /**
     *starts the timer count*/
    public void startTimer(){
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     *stops the timer*/
    public synchronized void stopTimer(){
        stopTimer = true;
    }

    /**
     *resets current counted time to 0*/
    public synchronized void reset(){
        elapsedMilliseconds = 0;
        elapsedSeconds = 0;
    }

    /**
     *returns the elapsed milliseconds*/
    public synchronized long getElapsedMilliseconds(){
        return elapsedMilliseconds;
    }

    /**
     *returns the elapsed seconds*/
    public synchronized   double getElapsedSeconds(){
        return elapsedSeconds;
    }

    @Override
    public void run() {//TODO check this works
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable helloRunnable = () -> {
            elapsedMilliseconds +=  1;
            elapsedSeconds = elapsedMilliseconds/1000d;
            if (stopTimer){
                executor.shutdown();
            }
        };
        executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.MILLISECONDS);
    }
}
