package pibotlib.lib.addons;

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;

public class PwmConfig {

    public static com.pi4j.io.pwm.PwmConfig buildPwmConfig(Context pi4j, int address, PwmType type) {//pwm config builder
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
}
