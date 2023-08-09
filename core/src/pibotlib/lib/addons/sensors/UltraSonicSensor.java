package pibotlib.lib.addons.sensors;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.PullResistance;
import pibotlib.lib.addons.DigitalInputConfig;
import pibotlib.lib.addons.DigitalOutputConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 *Used for the HC-SR04 sensor or any other ultra-sonic sensor that works like it*/
public class UltraSonicSensor implements Runnable, DisplayAble {

    DigitalOutput sensorTriggerPin;
    DigitalInput sensorEchoPin;
    int sensorTrigger, sensorEcho;
    long startTime, endTime;
    volatile long distance;
    String name;
    Context context;
    Thread thread;

    public UltraSonicSensor(Context context, int sensorTrigger, int sensorEcho, String name){
        thread = new Thread(this,"sensorThread");
        this.context = context;
        sensorTriggerPin = context.create(DigitalOutputConfig.buildDigitalOutputConfig(context,sensorTrigger,"5","trigger"));
        sensorEchoPin = context.create(DigitalInputConfig.buildDigitalOutputConfig(context,sensorEcho,"6","echo", PullResistance.PULL_DOWN));
        this.name  = name;
    }

    /**
     *starts the sensor*/
    public void runSensor(){
        thread.start();
    }

    /**
     *returns the distance the sensor detects in cm*/
    public synchronized long getDistance(){
        return distance;
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("sensorThread")) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        sensorTriggerPin.low();
                        Thread.sleep(2000);
                        sensorTriggerPin.high();
                        Thread.sleep((long)0.01);
                        sensorTriggerPin.low();

                        while (sensorEchoPin.isLow()) {
                            startTime = System.nanoTime();
                        }
                        while (sensorEchoPin.isHigh()) {
                            endTime = System.nanoTime();
                        }
                        distance = (long) ((((endTime-startTime)/1e3)/2)/29.1);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Sonic sensor failure");
                        e.printStackTrace();
                    }
                }
            }, 0, 35);
        }
    }

    /**
     *returns the distance of the sensor in a string*/
    @Override
    public String getValueToString() {
        return getDistance() + " cm";
    }

    /**
     *returns the name of the sensor*/
    @Override
    public String getName() {
        return name;
    }
}
