package pibotlib.lib.addons;

import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.constants.Constants;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TimedRobotBase implements Runnable {

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
                if (!DriverStationState.getState().equals(Constants.DriverStationStates.KILL)){

                    robotPeriodic();

                    if (DriverStationState.getRobotMode().equals(Constants.RobotSates.AUTO)){
                        if (DriverStationState.getState().equals(Constants.DriverStationStates.ENABLED)){
                            autonomousPeriodic();
                        }
                    }

                    if (DriverStationState.getRobotMode().equals(Constants.RobotSates.TELEOP)){
                        if (DriverStationState.getState().equals(Constants.DriverStationStates.ENABLED)){
                            teleopPeriodic();
                        }
                    }

                }else {
                    robotShutDown();
                    timer.cancel();
                    System.exit(0);
                }
            }
        }, 0, 10);
    }

    @Override
    public void run() {
        robotInit();
        runRobot();
    }
}
