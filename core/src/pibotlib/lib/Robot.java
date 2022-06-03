package pibotlib.lib;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.utils.gamecontrollers.LocalXboxController;
import pibotlib.utils.motorcontrollers.DualHBridgeController;
import pibotlib.utils.motorcontrollers.MotorController;

public class Robot implements Runnable{

    Context context;
    Pwm pwm;
    DigitalOutputConfigBuilder pinConfig;
    DigitalOutput pin,pin2;
    LocalXboxController controller;

    public Robot(){
        controller = new LocalXboxController();
    }

    private static PwmConfig buildPwmConfig(Context pi4j, int address) {
        return Pwm.newConfigBuilder(pi4j)
                .id("BCM" + address)
                .name("Buzzer")
                .address(address)
                .pwmType(PwmType.HARDWARE)
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
        pwm = context.create(buildPwmConfig(context,18));
        pin = context.create(outputConfigBuilder(context,14,"pin14","left motor"));
        pin2 = context.create(outputConfigBuilder(context,15,"pin15","right motor"));

        while (true) {
            if (DriverStationState.getState().equals("Enabled")) {
                pwm.on(controller.getLeftYAxis()*100,1000);
                pin.high();
                pin2.low();
            }
            if (DriverStationState.getState().equals("Disabled")) {
                pwm.on(0,1000);
                pin.high();
                pin2.low();
            }

            if (DriverStationState.getState().equals("Kill")){
                pin.shutdown(context);
                pin2.shutdown(context);
                pwm.shutdown(context);
                context.shutdown();
                break;
            }
        }
    }
}
