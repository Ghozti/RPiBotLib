package pibotlib.utils.motorcontrollers;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;

public class DualHBridgeController implements MotorController{

    int motor1ChannelForward, motor1ChannelBackward, motor2ChannelForward, motor2ChannelBackward;
    int controllerID;
    Context pi4j;
    Pwm motor1PWMOutput, motor2PWMOutput;
    DigitalOutput motor1DigitalForward, motor1DigitalBackward, motor2DigitalForward, motor2DigitalBackward;
    boolean motor1Inverted, motor2Inverted;

    public DualHBridgeController(int motor1ChannelForward, int motor1ChannelBackward, int motor2ChannelForward, int motor2ChannelBackward){
        this.motor1ChannelForward = motor1ChannelForward;
        this.motor1ChannelBackward = motor1ChannelBackward;
        this.motor2ChannelForward = motor2ChannelForward;
        this.motor2ChannelBackward = motor2ChannelBackward;
    }

    public DualHBridgeController(int motor1ChannelForward, int motor1ChannelBackward){
        this.motor1ChannelForward = motor1ChannelForward;
        this.motor1ChannelBackward = motor1ChannelBackward;
    }

    public void configMotor1PWM(PwmConfig config){
        this.motor1PWMOutput = pi4j.create(config);
    }

    public void configMotor2PWM(PwmConfig config){
        this.motor2PWMOutput = pi4j.create(config);
    }

    public void setMotor1DigitalForward(DigitalOutputConfigBuilder config){
        motor1DigitalForward = pi4j.create(config);
    }

    public void setMotor1DigitalBackward(DigitalOutputConfigBuilder config){
        motor1DigitalBackward = pi4j.create(config);
    }

    public void setMotor2DigitalForward(DigitalOutputConfigBuilder config){
        motor2DigitalForward = pi4j.create(config);
    }

    public void setMotor2DigitalBackward(DigitalOutputConfigBuilder config){
        motor2DigitalBackward = pi4j.create(config);
    }

    @Override
    public void motor1Forward(int speedVal) {
        motor1PWMOutput.on(speedVal,1000);
        if (!motor1Inverted) {
            motor1DigitalForward.high();
            motor1DigitalBackward.low();
        }else {
            motor1DigitalBackward.high();
            motor1DigitalForward.low();
        }
    }

    @Override
    public void motor1Backward(int speedVal) {
        motor1PWMOutput.on(speedVal,1000);
        if (motor1Inverted) {
            motor1DigitalBackward.high();
            motor1DigitalForward.low();
        }else {
            motor1DigitalForward.high();
            motor1DigitalBackward.low();
        }
    }

    @Override
    public void motor2Forward(int speedVal) {
        motor2PWMOutput.on(speedVal,1000);
        if (!motor2Inverted) {
            motor2DigitalForward.high();
            motor2DigitalBackward.low();
        }else {
            motor2DigitalBackward.high();
            motor2DigitalForward.low();
        }
    }

    @Override
    public void motor2Backward(int speedVal) {
        motor2PWMOutput.on(speedVal,1000);
        if (motor2Inverted) {
            motor2DigitalBackward.high();
            motor2DigitalForward.low();
        }else {
            motor2DigitalForward.high();
            motor2DigitalBackward.low();
        }
    }

    @Override
    public void setID(int id) {
        this.controllerID = id;
    }

    @Override
    public void setContext(Context context) {
        this.pi4j = context;
    }

    @Override
    public void setmotor1Inverted(boolean inverted) {
        this.motor1Inverted = inverted;
    }

    @Override
    public void setmotor2Inverted(boolean inverted) {
        this.motor2Inverted = inverted;
    }

    @Override
    public int getId() {
        return controllerID;
    }

    @Override
    public boolean motor1Inverted() {
        return motor1Inverted;
    }

    @Override
    public boolean motor2Inverted() {
        return motor2Inverted;
    }

    @Override
    public int getMotor1ForwardChannel() {
        return motor1ChannelForward;
    }

    @Override
    public int getMotor1BackwardChannel() {
        return motor1ChannelBackward;
    }

    @Override
    public int getMotor2ForwardChannel() {
        return motor2ChannelForward;
    }

    @Override
    public int getMotor2BackwardChannel() {return motor2ChannelBackward;}

    @Override
    public int getMotor1Speed() {return 0;}

    @Override
    public int getMotor2Speed() {
        return 0;
    }
}
