package Robot;

import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.constants.Constants;
import pibotlib.lib.gamecontrollers.LocalXboxController;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimedRobotBase implements Runnable {

    String robotSate;

    public abstract void robotInit();

    public abstract void robotPeriodic();

    public abstract void autonomousPeriodic();

    public abstract void teleopPeriodic();

    public abstract void robotShutDown();

    public void runRobot(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotPeriodic();
            }
        }, 0, 1);
    }

    @Override
    public void run() {
        runRobot();
    }
}
