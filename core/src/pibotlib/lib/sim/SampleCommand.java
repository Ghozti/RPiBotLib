package pibotlib.lib.sim;

import pibotlib.lib.sim.characterization.SimRobotNew;
import pibotlib.lib.sim.utils.commands.SimCommand;

public class SampleCommand extends SimCommand {

    int count;
    SimRobotNew simRobot;

    public SampleCommand(SimRobotNew simRobot){
        this.simRobot = simRobot;
    }

    @Override
    public void init() {
        count = 0;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return (simRobot.getLeftEncoder() + simRobot.getRightEncoder()) / 2 == 500;
    }

    @Override
    public void end() {
        simRobot.drive(0,0);
    }
}
