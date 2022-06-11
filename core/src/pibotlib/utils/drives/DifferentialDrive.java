package pibotlib.utils.drives;

import pibotlib.utils.motorcontrollers.MotorController;

public class DifferentialDrive extends ChassisDrive{

    public DifferentialDrive(MotorController leftcontroller, MotorController rightController) {
        super(leftcontroller, rightController);
    }

    public void arcadeDrive(double x, double y){
        if (x > 0){//forward
            getLeftController().motor1Forward(x);
            getLeftController().motor2Forward(x);
            getRightController().motor1Forward(x);
            getRightController().motor2Forward(x);
        }else if (x < 0){//backward
            getLeftController().motor1Backward(-x);
            getLeftController().motor2Backward(-x);
            getRightController().motor1Backward(-x);
            getRightController().motor2Backward(-x);
        } else {//halt
            getLeftController().motor1Kill();
            getRightController().motor2Kill();
        }

        if(y < 0) {//left turn
            System.out.println("left");
            getLeftController().motor1Backward(-y);
            getLeftController().motor2Backward(-y);
            getRightController().motor1Forward(-y);
            getRightController().motor2Forward(-y);
        }else if(y > 0) {//right turn
            System.out.println("right");
            getRightController().motor1Backward(y);
            getRightController().motor2Backward(y);
            getLeftController().motor1Forward(y);
            getLeftController().motor2Forward(y);
        } else {//halt
            getLeftController().motor1Kill();
            getRightController().motor2Kill();
        }
    }
}
