package pibotlib.lib.time;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *an elapse timer that can count in milliseconds or seconds*/
public class ElapseTimer implements Runnable {

    public volatile double elapsedSeconds;
    public volatile long elapsedMilliseconds;

    volatile double savedElapsedSeconds;
    volatile long savedElapsedMilSeconds;
    boolean pause;

    volatile long elapsedMillisecondsSaved;
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

    public synchronized void pauseTimer(){
        pause = true;
    }

    public synchronized void resumeTimer(){
        pause = false;
    }

    /**
     *resets current counted time to 0*/
    public synchronized void reset(){
        pause = true;
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
    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable helloRunnable = () -> {

            if (!pause) {
                elapsedMilliseconds += 100;
                elapsedSeconds = elapsedMilliseconds / 1000d;
            }
            if (stopTimer){
                executor.shutdown();
            }
        };
        executor.scheduleAtFixedRate(helloRunnable, 0, 100, TimeUnit.MILLISECONDS);
    }
}
