package Robot;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.PwmType;
import pibotlib.graphics.DriverStation;
import pibotlib.lib.addons.DigitalOutputConfig;
import pibotlib.lib.addons.PwmConfig;
import pibotlib.lib.addons.RobotStateLight;
import pibotlib.lib.addons.TimedRobotBase;
import pibotlib.lib.addons.hats.ServoDriver;
import pibotlib.lib.addons.sensors.UltraSonicSensor;
import pibotlib.lib.autonomous.TimedAutoBase;
import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.gamecontrollers.LocalXboxController;
import pibotlib.lib.motorcontrollers.DualHBridgeController;

public class Robot extends TimedRobotBase {

    Context context;
    LocalXboxController controller;
    DualHBridgeController leftController, rightController;
    DifferentialDrive differentialDrive;
    RobotStateLight stateLight;
    UltraSonicSensor sonicSensor;
    boolean controllerFound;

    TimedAutoBase autoBase;

    public Robot(){

    }

    //called once per program init
    @Override
    public void robotInit() {
        try {
            context = Pi4J.newAutoContext();//always call first

            leftController = new DualHBridgeController(context, 14, 15, 23, 24);
            rightController = new DualHBridgeController(context, 9, 25, 11, 8);

            leftController.configMotor1PWM(PwmConfig.buildPwmConfig(leftController.getContext(), 18, PwmType.HARDWARE));
            leftController.configMotor2PWM(PwmConfig.buildPwmConfig(leftController.getContext(), 12, PwmType.HARDWARE));
            rightController.configMotor1PWM(PwmConfig.buildPwmConfig(rightController.getContext(), 13, PwmType.HARDWARE));
            rightController.configMotor2PWM(PwmConfig.buildPwmConfig(rightController.getContext(), 19, PwmType.HARDWARE));

            leftController.setMotor1DigitalForward(DigitalOutputConfig.buildDigitalOutputConfig(leftController.getContext(), 23, "pin23", "left motorL"));
            leftController.setMotor1DigitalBackward(DigitalOutputConfig.buildDigitalOutputConfig(leftController.getContext(), 24, "pin24", "left motorL"));
            leftController.setMotor2DigitalForward(DigitalOutputConfig.buildDigitalOutputConfig(leftController.getContext(), 25, "pin25", "left motorR"));
            leftController.setMotor2DigitalBackward(DigitalOutputConfig.buildDigitalOutputConfig(leftController.getContext(), 8, "pin8", "left motorR"));

            rightController.setMotor1DigitalForward(DigitalOutputConfig.buildDigitalOutputConfig(rightController.getContext(), 17, "pin17", "left motorL"));
            rightController.setMotor1DigitalBackward(DigitalOutputConfig.buildDigitalOutputConfig(rightController.getContext(), 27, "pin27", "left motorL"));
            rightController.setMotor2DigitalForward(DigitalOutputConfig.buildDigitalOutputConfig(rightController.getContext(), 22, "pin22", "left motorR"));
            rightController.setMotor2DigitalBackward(DigitalOutputConfig.buildDigitalOutputConfig(rightController.getContext(), 10, "pin10", "left motorR"));

            differentialDrive = new DifferentialDrive(leftController, rightController);
            stateLight = new RobotStateLight(context, 16);

            ServoDriver.main();
        }catch (Exception e){
            System.out.println("Robot init fail, reboot raspberry pi and try again");
            e.printStackTrace();
        }
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
