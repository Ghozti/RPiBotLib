package pibotlib.lib.addons.hats;

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

    public static void main() {
        int pwm_freq = 50;
        int pin_number = 12;
        test(pwm_freq, pin_number);
    }

    public static void test(int pwmFrequency, int gpio) {
        ServoTrim trim = ServoTrim.MG996R;
        try (PCA9685 pca9685 = new PCA9685(I2CConstants.CONTROLLER_1,0x40,pwmFrequency);
             ServoDevice servo = ServoDevice.newBuilder(gpio).setDeviceFactory(pca9685).setTrim(trim).build()) {
            Logger.info("Mid");
            pca9685.setDutyUs(gpio, trim.getMidPulseWidthUs());
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Max");
            pca9685.setDutyUs(gpio, trim.getMaxPulseWidthUs());
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Mid");
            pca9685.setDutyUs(gpio, trim.getMidPulseWidthUs());
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Min");
            pca9685.setDutyUs(gpio, trim.getMinPulseWidthUs());
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Mid");
            pca9685.setDutyUs(gpio, trim.getMidPulseWidthUs());
            SleepUtil.sleepMillis(LARGE_DELAY);

            Logger.info("Max");
            servo.max();
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Mid");
            servo.mid();
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Min");
            servo.min();
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("Mid");
            servo.mid();
            SleepUtil.sleepMillis(LARGE_DELAY);

            Logger.info("0");
            servo.setAngle(0);
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("90 (Centre)");
            servo.setAngle(90);
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("180");
            servo.setAngle(180);
            SleepUtil.sleepMillis(LARGE_DELAY);
            Logger.info("90 (Centre)");
            servo.setAngle(90);
            SleepUtil.sleepMillis(LARGE_DELAY);

            for (int pulse_us = trim.getMidPulseWidthUs(); pulse_us < trim.getMaxPulseWidthUs(); pulse_us += 10) {
                servo.setPulseWidthUs(pulse_us);
                SleepUtil.sleepMillis(SHORT_DELAY);
            }
            for (int pulse_us = trim.getMaxPulseWidthUs(); pulse_us > trim.getMinPulseWidthUs(); pulse_us -= 10) {
                servo.setPulseWidthUs(pulse_us);
                SleepUtil.sleepMillis(SHORT_DELAY);
            }
            for (int pulse_us = trim.getMinPulseWidthUs(); pulse_us < trim.getMidPulseWidthUs(); pulse_us += 10) {
                servo.setPulseWidthUs(pulse_us);
                SleepUtil.sleepMillis(SHORT_DELAY);
            }
        }
    }

}
