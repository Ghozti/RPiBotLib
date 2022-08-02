package pibotlib.lib.motorcontrollers;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;

/**
 *used to declare new dual H-bridge motor controllers*/
public class DualHBridgeController implements MotorController{

    int motor1ChannelForward, motor1ChannelBackward, motor2ChannelForward, motor2ChannelBackward;
    int controllerID;
    Context pi4j;
    Pwm motor1PWMOutput, motor2PWMOutput;
    DigitalOutput motor1DigitalForward, motor1DigitalBackward, motor2DigitalForward, motor2DigitalBackward;
    boolean motor1Inverted, motor2Inverted;

    public DualHBridgeController(Context context, int motor1ChannelForward, int motor1ChannelBackward, int motor2ChannelForward, int motor2ChannelBackward){
        this.pi4j = context;
        this.motor1ChannelForward = motor1ChannelForward;
        this.motor1ChannelBackward = motor1ChannelBackward;
        this.motor2ChannelForward = motor2ChannelForward;
        this.motor2ChannelBackward = motor2ChannelBackward;
    }

    public DualHBridgeController(Context context, int motor1ChannelForward, int motor1ChannelBackward){
        this.pi4j = context;
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

    /**
     *will drive motor 1 forward (speedVal should be from 0-100)*/
    @Override
    public void motor1Forward(double speedVal) {
        motor1PWMOutput.on(speedVal,1000);
        if (!motor1Inverted) {
            motor1DigitalForward.high();
            motor1DigitalBackward.low();
        }else {
            motor1DigitalBackward.high();
            motor1DigitalForward.low();
        }
    }

    /**
     *will drive motor 1 backward (speedVal should be from 0-100)*/
    @Override
    public void motor1Backward(double speedVal) {
        motor1PWMOutput.on(speedVal,1000);
        if (!motor1Inverted) {
            motor1DigitalBackward.high();
            motor1DigitalForward.low();
        }else {
            motor1DigitalForward.high();
            motor1DigitalBackward.low();
        }
    }

    /**
     *will drive motor 2 forward (speedVal should be from 0-100)*/
    @Override
    public void motor2Forward(double speedVal) {
        motor2PWMOutput.on(speedVal,1000);
        if (!motor2Inverted) {
            motor2DigitalForward.high();
            motor2DigitalBackward.low();
        }else {
            motor2DigitalBackward.high();
            motor2DigitalForward.low();
        }
    }

    /**
     *will drive motor 2 backward (speedVal should be from 0-100)*/
    @Override
    public void motor2Backward(double speedVal) {
        motor2PWMOutput.on(speedVal,1000);
        if (!motor2Inverted) {
            motor2DigitalBackward.high();
            motor2DigitalForward.low();
        }else {
            motor2DigitalForward.high();
            motor2DigitalBackward.low();
        }
    }

    /**
     *will give an id to this controller*/
    @Override
    public void setID(int id) {
        this.controllerID = id;
    }

    /**
     *gives a pi4j context*/
    @Override
    public void setContext(Context context) {
        this.pi4j = context;
    }

    /**
     *will invert motor 1 ex: (if it's forward by default it will be backwards)*/
    @Override
    public void setmotor1Inverted(boolean inverted) {
        this.motor1Inverted = inverted;
    }

    /**
     *will invert motor 2 ex: (if it's forward by default it will be backwards)*/
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

    /**
     *will shut down motor 1*/
    @Override
    public void motor1Kill() {
        motor1PWMOutput.off();
        motor1DigitalForward.low();
        motor1DigitalBackward.low();
    }

    /**
     *will shut down motor2*/
    @Override
    public void motor2Kill() {
        motor2PWMOutput.off();
        motor2DigitalForward.low();
        motor2DigitalBackward.low();
    }

    /**
     *DO NOT USE, WILL RETURN 0 AT ALL TIMES*/
    @Override
    public int getMotor1Speed() {return 0;}

    /**
     *DO NOT USE, WILL RETURN 0 AT ALL TIMES*/
    @Override
    public int getMotor2Speed() {
        return 0;
    }

    public Context getContext(){
        return pi4j;
    }
}
