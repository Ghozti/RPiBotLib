package pibotlib.lib;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;
import pibotlib.utils.DriverStationState;

import java.util.Scanner;

public class Robot implements Runnable{

    Context context;
    Pwm pwm;
    DigitalOutputConfigBuilder pinConfig;
    DigitalOutput pin,pin2;

    public Robot(){
        //System.out.println("robot init");
        //context = Pi4J.newAutoContext();
        //pinConfig = DigitalOutput.newConfigBuilder(context)
        //        .id("led")
        //        .name("LED Flasher")
        //        .address(4)
        //        .shutdown(DigitalState.LOW)
        //        .initial(DigitalState.LOW)
        //        .provider("pigpio-digital-output");
        //pin = context.create(pinConfig);
        //pin.high();
        ////pwm = context.create(buildPwmConfig(context,12));
        ////pwm.on(50,1);
    }

    public void runRobot(){
        //if (DriverStationState.getState().equals("Enabled")){
        //    System.out.println("robot running");
        //    context = Pi4J.newAutoContext();
        //    pinConfig = DigitalOutput.newConfigBuilder(context)
        //            .id("led")
        //            .name("LED Flasher")
        //            .address(4)
        //            .shutdown(DigitalState.LOW)
        //            .initial(DigitalState.LOW)
        //            .provider("pigpio-digital-output");
        //    pin = context.create(pinConfig);
        //    pin.high();
        //}
        //if (DriverStationState.getState().equals("Disabled")){
        //    pin.low();
        //}else {
        //    context.shutdown();
        //}
        //pwm.on(50,1);
    }

    protected static PwmConfig buildPwmConfig(Context pi4j, int address) {
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

    public static DigitalOutputConfigBuilder outputConfigBuilder(Context context, int adress, String id, String name){
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
        pin = context.create(outputConfigBuilder(context,14,"pin14","bitch"));
        pin2 = context.create(outputConfigBuilder(context,15,"pin15","fuckthis pin"));

        while (true) {
            if (DriverStationState.getState().equals("Enabled")) {
                pwm.on(36,16000000);
                pin.high();
                pin2.low();
            }
            if (DriverStationState.getState().equals("Disabled")) {
                pin.low();
                pin2.low();
                pwm.on(0,16000000);
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
