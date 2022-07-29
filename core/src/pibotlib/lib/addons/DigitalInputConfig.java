package pibotlib.lib.addons;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;

public class DigitalInputConfig {
    public static DigitalInputConfigBuilder buildDigitalOutputConfig(Context context, int adress, String id, String name, PullResistance pullResistance){//dio config
        return  DigitalInput.newConfigBuilder(context)
                .id(id)
                .name(name)
                .address(adress)
                .pull(pullResistance)
                .provider("pigpio-digital-output");
    }
}
