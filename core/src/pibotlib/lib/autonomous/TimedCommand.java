package pibotlib.lib.autonomous;

import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.constants.Constants;
import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.time.ElapseTimer;

import java.util.Timer;
import java.util.TimerTask;

public class TimedCommand {

    volatile public ElapseTimer timer;
    volatile boolean isDone;
    volatile long durationMilliseconds;
    volatile DifferentialDrive drive;
    volatile double x,y;

    public TimedCommand(double durationSeconds, DifferentialDrive drive, double x, double y){
        timer = new ElapseTimer();
        durationMilliseconds = (long) (durationSeconds * 1000);
        this.drive = drive;
        this.x = x;
        this.y = y;
    }

    public TimedCommand(long durationMilliseconds, DifferentialDrive drive, double x, double y){
        timer = new ElapseTimer();
        this.durationMilliseconds = durationMilliseconds;
        this.drive = drive;
        this.x = x;
        this.y = y;
    }

    public void execute(){
        timer.startTimer();

        Timer taskTimer = new Timer();
        taskTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timer.getElapsedMilliseconds() <= durationMilliseconds){
                    drive.arcadeDrive(x,y);
                }else {
                    isDone = true;
                    timer.stopTimer();
                }
            }
        }, 0, 10);

    }

    public boolean isDone(){
        return isDone;
    }
}
