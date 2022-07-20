package pibotlib.lib.autonomous;

import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.time.ElapseTimer;

public class TimedCommand {

    ElapseTimer timer;
    boolean isDone;
    double durationSeconds;
    long durationMilliseconds;

    public TimedCommand(double durationSeconds){
        timer = new ElapseTimer();
        this.durationSeconds = durationSeconds;
    }

    public TimedCommand(long durationMilliseconds){
        timer = new ElapseTimer();
        this.durationMilliseconds = durationMilliseconds;
    }

    public void execute(DifferentialDrive drive, double x, double y){
        timer.startTimer();
        while (!isDone) {
            if (durationSeconds >= timer.getElapsedSeconds() || durationMilliseconds >= timer.getElapsedMilliseconds()){
                isDone = true;
            }
            drive.arcadeDrive(x, y);
        }
    }

    public boolean isDone(){
        return isDone;
    }
}
