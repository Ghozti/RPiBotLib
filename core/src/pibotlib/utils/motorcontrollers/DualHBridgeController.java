package pibotlib.utils.motorcontrollers;

public class DualHBridgeController implements MotorController{

    public DualHBridgeController(int motor1Channel, int motor2Channel){

    }

    @Override
    public void motor1Forward(int speedVal) {

    }

    @Override
    public void motor1Backward(int speedVal) {

    }

    @Override
    public void motor2Forward(int speedVal) {

    }

    @Override
    public void motor2Backward(int speedVal) {

    }

    @Override
    public void setID(int id) {

    }

    @Override
    public void setmotor1Inverted(boolean inverted) {

    }

    @Override
    public void setmotor2Inverted(boolean inverted) {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean motor1Inverted() {
        return false;
    }

    @Override
    public boolean motor2Inverted() {
        return false;
    }

    @Override
    public int getMotor1ForwardChannel() {
        return 0;
    }

    @Override
    public int getMotor1BackwardChannel() {
        return 0;
    }

    @Override
    public int getMotor2ForwardChannel() {
        return 0;
    }

    @Override
    public int getMotor2BackwardChannel() {
        return 0;
    }

    @Override
    public int getMotor1Speed() {
        return 0;
    }

    @Override
    public int getMotor2Speed() {
        return 0;
    }
}
