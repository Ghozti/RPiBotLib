package Robot;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.addons.RobotStateLight;
import pibotlib.lib.constants.Constants;
import pibotlib.lib.drives.DifferentialDrive;
import pibotlib.lib.gamecontrollers.LocalXboxController;
import pibotlib.lib.motorcontrollers.DualHBridgeController;

public class Robot implements Runnable{

    Context context;
    LocalXboxController controller;
    DualHBridgeController leftController, rightController;
    DifferentialDrive differentialDrive;
    RobotStateLight stateLight;
    boolean controllerFound;

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

    private static PwmConfig buildPwmConfig(Context pi4j, int address, PwmType type) {//pwm config builder
        return Pwm.newConfigBuilder(pi4j)
                .id("BCM" + address)
                .name("Buzzer")
                .address(address)
                .pwmType(type)
                .provider("pigpio-pwm")
                .initial(0)
                .shutdown(0)
                .build();
    }

    private static DigitalOutputConfigBuilder outputConfigBuilder(Context context, int adress, String id, String name){//dio config
        return  DigitalOutput.newConfigBuilder(context)
                .id(id)
                .name(name)
                .address(adress)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
    }

    @Override
    public void run() {
        // will run in its own thread to not interfere with libgdx's runtime

        try {
            context = Pi4J.newAutoContext();//always call first

            leftController = new DualHBridgeController(context, 14, 15, 23, 24);
            rightController = new DualHBridgeController(context, 9, 25, 11, 8);

            leftController.configMotor1PWM(buildPwmConfig(leftController.getContext(), 18, PwmType.HARDWARE));
            leftController.configMotor2PWM(buildPwmConfig(leftController.getContext(), 12, PwmType.HARDWARE));
            rightController.configMotor1PWM(buildPwmConfig(rightController.getContext(), 13, PwmType.HARDWARE));
            rightController.configMotor2PWM(buildPwmConfig(rightController.getContext(), 19, PwmType.HARDWARE));

            leftController.setMotor1DigitalForward(outputConfigBuilder(leftController.getContext(), 23, "pin23", "left motorL"));
            leftController.setMotor1DigitalBackward(outputConfigBuilder(leftController.getContext(), 24, "pin24", "left motorL"));
            leftController.setMotor2DigitalForward(outputConfigBuilder(leftController.getContext(), 25, "pin25", "left motorR"));
            leftController.setMotor2DigitalBackward(outputConfigBuilder(leftController.getContext(), 8, "pin8", "left motorR"));

            rightController.setMotor1DigitalForward(outputConfigBuilder(rightController.getContext(), 17, "pin17", "left motorL"));
            rightController.setMotor1DigitalBackward(outputConfigBuilder(rightController.getContext(), 27, "pin27", "left motorL"));
            rightController.setMotor2DigitalForward(outputConfigBuilder(rightController.getContext(), 22, "pin22", "left motorR"));
            rightController.setMotor2DigitalBackward(outputConfigBuilder(rightController.getContext(), 10, "pin10", "left motorR"));

            differentialDrive = new DifferentialDrive(leftController, rightController);
            stateLight = new RobotStateLight(context, 16);
        }catch (Exception e){
            System.out.println("Robot init fail, reboot raspberry pi and try again");
        }

        while(true) {

            try
            {
                Thread.sleep(50);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if (DriverStationState.getState().equals(Constants.DriverStationStates.KILL)){
                stateLight.shutDown();
                differentialDrive.arcadeDrive(0, 0);
                context.shutdown();
                return;
            }

            if (!controllerFound){
                try {
                    controller = new LocalXboxController();
                    controllerFound = true;
                }catch (Exception e){
                    System.out.println("No controller found");
                }
            }

            if (DriverStationState.getState().equals(Constants.DriverStationStates.ENABLED)) {
                differentialDrive.arcadeDrive(-controller.getLeftYAxis() * 100, controller.getRightYAxis() * 100);
                stateLight.blinkRSL();
            }else {
                stateLight.shutDown();
                differentialDrive.arcadeDrive(0, 0);
            }
        }
    }
}
