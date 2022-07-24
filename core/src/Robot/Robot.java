package Robot;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.PwmType;
import pibotlib.lib.addons.DigitalOutputConfig;
import pibotlib.lib.addons.PwmConfig;
import pibotlib.lib.addons.RobotStateLight;
import pibotlib.lib.autonomous.TimedAutoBase;
import pibotlib.lib.autonomous.TimedCommand;
import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.gamecontrollers.LocalXboxController;
import pibotlib.lib.motorcontrollers.DualHBridgeController;

public class Robot extends TimedRobotBase {

    Context context;
    LocalXboxController controller;
    DualHBridgeController leftController, rightController;
    DifferentialDrive differentialDrive;
    RobotStateLight stateLight;
    boolean controllerFound;

    TimedAutoBase autoBase;

    public Robot(){
        //constructor called once which can create its own controller
        try {
            controller = new LocalXboxController();
            controllerFound = true;
        }catch (Exception e){
            System.out.println("No controller detected");
            controllerFound = false;
        }
    }

    public Robot(LocalXboxController controller){
        //constructor which takes a controller as an argument
        try {
            controllerFound = true;
            this.controller = controller;
        }catch (Exception e){
            System.out.println("No controller detected");
            controllerFound = false;
        }
    }

    @Override
    public void robotInit() {
        //called once per program run
    }

    @Override
    public void robotPeriodic() {
        //called periodically when enabled or disabled. Also called during auto and teleop
    }

    @Override
    public void autonomousPeriodic() {
        //called periodically under auto mode
    }

    @Override
    public void teleopPeriodic() {
        //called periodically under teleopMode
    }

    @Override
    public void robotShutDown() {
        //called once at "kill"
    }
}
