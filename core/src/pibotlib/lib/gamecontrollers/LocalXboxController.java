package pibotlib.lib.gamecontrollers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

public class LocalXboxController {

    Controller controller;

    public LocalXboxController(){
        controller = Controllers.getCurrent();
    }

    public double getLeftXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftX) * 100.0) / 100.0;
    }

    public double getLeftYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftY) * 100.0) / 100.0;
    }

    public double getRightXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightY) * 100.0) / 100.0;
    }

    public double getRightYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightX) * 100.0) / 100.0;
    }

    public boolean getA(){
        return controller.getButton(controller.getMapping().buttonA);
    }

    public boolean getB(){
        return controller.getButton(controller.getMapping().buttonB);
    }

    public boolean getX(){
        return controller.getButton(controller.getMapping().buttonX);
    }

    public boolean getY(){
        return controller.getButton(controller.getMapping().buttonY);
    }
}
