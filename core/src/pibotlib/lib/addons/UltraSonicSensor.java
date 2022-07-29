package pibotlib.lib.addons;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.PullResistance;
import java.util.Timer;
import java.util.TimerTask;

public class UltraSonicSensor implements Runnable{

    DigitalOutput sensorTriggerPin;
    DigitalInput sensorEchoPin;
    int sensorTrigger, sensorEcho;
    long startTime, endTime;
    volatile long distance;
    Context context;
    Thread thread;

    public UltraSonicSensor(Context context, int sensorTrigger, int sensorEcho){//5,6
        thread = new Thread(this,"sensorThread");
        this.context = context;
        sensorTriggerPin = context.create(DigitalOutputConfig.buildDigitalOutputConfig(context,sensorTrigger,"5","trigger"));
        sensorEchoPin = context.create(DigitalInputConfig.buildDigitalOutputConfig(context,sensorEcho,"6","echo", PullResistance.PULL_DOWN));
    }

    public void runSensor(){
        System.out.println("sensor run method");
        thread.start();
    }

    public synchronized long getDistance(){
        return distance;
    }

    @Override
    public void run() {
        System.out.println("sensor run");
        if (Thread.currentThread().getName().equals("sensorThread")) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        System.out.println("sensor init");
                        sensorTriggerPin.low();
                        Thread.sleep(2000);
                        sensorTriggerPin.high();
                        sensorTriggerPin.low();
                        System.out.println("sensor logic running");
                        while (sensorTriggerPin.isLow()){
                            startTime = System.nanoTime();
                        }
                        while (sensorTriggerPin.isHigh()){
                            endTime = System.nanoTime();
                        }
                        distance = (long) ((((endTime-startTime)/1e3)/2)/29.1);
                        System.out.println(distance);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Sonic sensor failure");
                        e.printStackTrace();
                    }
                }
            }, 0, 10);
        }
    }
}
