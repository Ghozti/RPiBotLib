package pibotlib.lib.addons.hats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.diozero.api.I2CConstants;
import com.diozero.util.SleepUtil;
import org.tinylog.Logger;
import com.diozero.animation.Animation;
import com.diozero.animation.AnimationInstance;
import com.diozero.animation.easing.Sine;
import com.diozero.api.ServoDevice;
import com.diozero.api.ServoTrim;
import com.diozero.devices.PCA9685;

public class ServoDriver {

    private static final long LARGE_DELAY = 500;
    private static final long SHORT_DELAY = 10;

    PCA9685 pca9685;
    private ArrayList<ServoDevice> servos = new ArrayList<>();

    public ServoDriver(int constant, int address, int pwmFrequency){
        try {
            pca9685 = new PCA9685(constant,address,pwmFrequency);
        }catch (Exception e){
            System.out.println("Servo device instantiation failed");
            e.printStackTrace();
        }
    }

    public ServoDriver(){
        try {
            pca9685 = new PCA9685(I2CConstants.CONTROLLER_1,0x40,50);
        }catch (Exception e){
            System.out.println("Servo device instantiation failed");
            e.printStackTrace();
        }
    }

    public void addServo(int gpio, ServoTrim trim){
        if (servos.size() >= 12){
            System.out.println("servo limit is 12");
            return;
        }else {
            try {
                servos.add(ServoDevice.newBuilder(gpio).setDeviceFactory(pca9685).setTrim(trim).build());
            }catch (Exception e){
                System.out.println("Servo init failed");
                e.printStackTrace();
            }
        }
    }

    public void addAllServos(ServoTrim trim){
        if (servos.size() >= 12){
            System.out.println("servo limit is 12");
            return;
        }else {
            try {
                for (int i = 0; i < 16; i++){
                    servos.add(ServoDevice.newBuilder(i).setDeviceFactory(pca9685).setTrim(trim).build());
                }
            }catch (Exception e){
                System.out.println("Servo init failed");
                e.printStackTrace();
            }
        }
    }

    public ServoDevice getServo(int ID){
        return servos.get(ID);
    }

    public void setServoMin(int ID){
        servos.get(ID).min();
    }

    public void setServoMid(int ID){
        servos.get(ID).mid();
    }

    public void setServoMax(int ID){
        servos.get(ID).max();
    }

    public void setServoAngle(int ID, float angle){
        servos.get(ID).setAngle(angle);
    }
}
