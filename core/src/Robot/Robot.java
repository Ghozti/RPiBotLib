package Robot;

import com.diozero.devices.HCSR04;
import com.diozero.devices.PCA9685;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import pibotlib.lib.addons.TimedRobotBase;
import pibotlib.lib.addons.hats.ServoDriver;
import pibotlib.lib.gamecontrollers.LocalXboxController;

public class Robot extends TimedRobotBase {

    public Robot(){
        ServoDriver.main();
    }

    //called once per program init
    @Override
    public void robotInit() {

    }

    //called periodically at all times
    @Override
    public void robotPeriodic() {

    }

    //called periodically during autonomous
    @Override
    public void autonomousPeriodic() {

    }

    //called periodically during tele-op
    @Override
    public void teleopPeriodic() {

    }

    //called once when set to kill
    @Override
    public void robotShutDown() {

    }
}
