package pibotlib.lib.autonomous;

import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.time.ElapseTimer;

public class TimedCommand {

    public ElapseTimer timer;
    boolean isDone;
    long durationMilliseconds;
    DifferentialDrive drive;
    double x,y;

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
        do {
            if (timer.getElapsedMilliseconds() <= durationMilliseconds){
                drive.arcadeDrive(x,y);
            }else {
                isDone = true;
                timer.stopTimer();
            }
        }while (!isDone);

    }

    public boolean isDone(){
        return isDone;
    }
}
