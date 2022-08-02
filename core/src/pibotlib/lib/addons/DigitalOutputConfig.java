package pibotlib.lib.addons;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

public class DigitalOutputConfig {

    /**
     *returns a new pi4j DigitalOutputConfigBuilder object*/
    public static DigitalOutputConfigBuilder buildDigitalOutputConfig(Context context, int adress, String id, String name){//dio config
        return  DigitalOutput.newConfigBuilder(context)
                .id(id)
                .name(name)
                .address(adress)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
    }
}
