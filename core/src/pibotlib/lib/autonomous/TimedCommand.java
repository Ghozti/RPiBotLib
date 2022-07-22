package pibotlib.lib.autonomous;

import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.time.ElapseTimer;

public class TimedCommand {

    public ElapseTimer timer;
    boolean isDone;
    double durationSeconds;
    long durationMilliseconds;
    String name;

    public TimedCommand(double durationSeconds){
        timer = new ElapseTimer();
        durationMilliseconds = (long) (durationSeconds * 1000);
    }

    public TimedCommand(long durationMilliseconds, String name){
        System.out.println("Command " + name + " init");
        timer = new ElapseTimer();
        this.durationMilliseconds = durationMilliseconds;
        this.name = name;
    }

    public void execute(DifferentialDrive drive, double x, double y){
        timer.startTimer();
        System.out.println("command " + name + " running");
        do {
            if (timer.getElapsedMilliseconds() <= durationMilliseconds){

            }else {
                System.out.println("command " + name + " is done");
                isDone = true;
                timer.stopTimer();
            }
        }while (!isDone);

    }

    public boolean isDone(){
        return isDone;
    }
}
