package pibotlib.utils.motorcontrollers;

import com.pi4j.context.Context;

public interface MotorController {

    void motor1Forward(int speedVal);
    void motor1Backward(int speedVal);
    void motor2Forward(int speedVal);
    void motor2Backward(int speedVal);

    void setID(int id);
    void setContext(Context context);

    void setmotor1Inverted(boolean inverted);
    void setmotor2Inverted(boolean inverted);

    int getId();

    boolean motor1Inverted();
    boolean motor2Inverted();

    int getMotor1ForwardChannel();
    int getMotor1BackwardChannel();
    int getMotor2ForwardChannel();
    int getMotor2BackwardChannel();

    int getMotor1Speed();
    int getMotor2Speed();
}
