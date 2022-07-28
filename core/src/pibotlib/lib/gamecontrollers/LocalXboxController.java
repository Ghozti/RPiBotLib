package pibotlib.lib.gamecontrollers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

public class LocalXboxController {

    Controller controller;

    public LocalXboxController(){
        controller = Controllers.getCurrent();
    }

    public synchronized double getLeftXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftX) * 100.0) / 100.0;
    }

    public synchronized double getLeftYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftY) * 100.0) / 100.0;
    }

    public synchronized double getRightXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightX) * 100.0) / 100.0;
    }

    public synchronized double getRightYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightY) * 100.0) / 100.0;
    }

    public synchronized boolean getA(){
        return controller.getButton(controller.getMapping().buttonA);
    }

    public synchronized boolean getB(){
        return controller.getButton(controller.getMapping().buttonB);
    }

    public synchronized boolean getX(){
        return controller.getButton(controller.getMapping().buttonX);
    }

    public synchronized boolean getY(){
        return controller.getButton(controller.getMapping().buttonY);
    }
}
