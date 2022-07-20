package pibotlib.lib.addons;

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;

public class RobotStateLight {

    Context context;
    PwmConfig config;
    Pwm pwm;

    public RobotStateLight(Context context, int pwmChannel){
        this.context = context;
        config = Pwm.newConfigBuilder(context)
                .id("BCM " + pwmChannel)
                .name("RSL")
                .address(pwmChannel)
                .pwmType(PwmType.SOFTWARE)
                .provider("pigpio-pwm")
                .initial(0)
                .shutdown(0)
                .build();
        pwm = context.create(config);
    }

    public void blinkRSL(){
        pwm.on(5,1000);
    }

    public void shutDown(){
        pwm.off();
    }


}
