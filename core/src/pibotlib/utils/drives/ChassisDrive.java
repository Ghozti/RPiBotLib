package pibotlib.utils.drives;

import pibotlib.utils.motorcontrollers.MotorController;

public class ChassisDrive {

    MotorController leftController, rightController;

    public ChassisDrive(MotorController leftcontroller, MotorController rightController){
        this.leftController = leftcontroller;
        this.rightController = rightController;
    }

    public MotorController getLeftController(){
        return leftController;
    }

    public MotorController getRightController(){
        return rightController;
    }
}
