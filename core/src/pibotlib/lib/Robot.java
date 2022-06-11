package pibotlib.lib;

import com.badlogic.gdx.controllers.Controller;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.utils.drives.DifferentialDrive;
import pibotlib.utils.gamecontrollers.LocalXboxController;
import pibotlib.utils.motorcontrollers.DualHBridgeController;
import pibotlib.utils.motorcontrollers.MotorController;

public class Robot implements Runnable{

    Context context;
    Pwm pwm;
    DigitalOutputConfigBuilder pinConfig;
    DigitalOutput pin,pin2;
    LocalXboxController controller;
    DualHBridgeController leftController, rightController;
    DifferentialDrive differentialDrive;

    public Robot(){
        controller = new LocalXboxController();
    }

    public Robot(LocalXboxController controller){
        this.controller = controller;
    }

    private static PwmConfig buildPwmConfig(Context pi4j, int address, PwmType type) {
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

    private static DigitalOutputConfigBuilder outputConfigBuilder(Context context, int adress, String id, String name){
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
        context = Pi4J.newAutoContext();

        pwm = context.create(buildPwmConfig(context,18,PwmType.HARDWARE));
        pin = context.create(outputConfigBuilder(context,14,"pin14","left motor"));
        pin2 = context.create(outputConfigBuilder(context,15,"pin15","right motor"));

        leftController = new DualHBridgeController(context, 14,15,23,24);
        rightController = new DualHBridgeController(context, 9,25,11,8);

        leftController.configMotor1PWM(buildPwmConfig(leftController.getContext(), 18,PwmType.HARDWARE));
        leftController.configMotor2PWM(buildPwmConfig(leftController.getContext(),12,PwmType.HARDWARE));
        rightController.configMotor1PWM(buildPwmConfig(rightController.getContext(), 13,PwmType.HARDWARE));
        rightController.configMotor2PWM(buildPwmConfig(rightController.getContext(),19,PwmType.HARDWARE));

        leftController.setMotor1DigitalForward(outputConfigBuilder(leftController.getContext(), 23,"pin23","left motorL"));
        leftController.setMotor1DigitalBackward(outputConfigBuilder(leftController.getContext(), 24,"pin24","left motorL"));
        leftController.setMotor2DigitalForward(outputConfigBuilder(leftController.getContext(), 25,"pin25","left motorR"));
        leftController.setMotor2DigitalBackward(outputConfigBuilder(leftController.getContext(), 8,"pin8","left motorR"));

        rightController.setMotor1DigitalForward(outputConfigBuilder(rightController.getContext(),17,"pin17","left motorL"));
        rightController.setMotor1DigitalBackward(outputConfigBuilder(rightController.getContext(),27,"pin27","left motorL"));
        rightController.setMotor2DigitalForward(outputConfigBuilder(rightController.getContext(),22,"pin22","left motorR"));
        rightController.setMotor2DigitalBackward(outputConfigBuilder(rightController.getContext(),10,"pin10","left motorR"));

        differentialDrive = new DifferentialDrive(leftController,rightController);

        while (true) {
            //if (DriverStationState.getState().equals("Enabled")) {
            //    pwm.on(-controller.getLeftYAxis()*100,1000);
            //    pin.high();
            //    pin2.low();
            //}
            //if (DriverStationState.getState().equals("Disabled")) {
            //    pwm.on(0,1000);
            //    pin.low();
            //    pin2.low();
            //}
////
            //if (DriverStationState.getState().equals("Kill")){
            //    pin.shutdown(context);
            //    pin2.shutdown(context);
            //    pwm.shutdown(context);
            //    context.shutdown();
            //    break;
            //}
           differentialDrive.arcadeDrive(controller.getLeftYAxis(),controller.getRightXAxis());
        }
    }
}
