package pibotlib.lib.drives;

import pibotlib.lib.motorcontrollers.MotorController;

public abstract class ChassisDrive {

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
