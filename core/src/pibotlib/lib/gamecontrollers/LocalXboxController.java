package pibotlib.lib.gamecontrollers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

/**
 *wrapper class for the Controller class found in libGDX*/
public class LocalXboxController {

    Controller controller;

    public LocalXboxController(){
        controller = Controllers.getCurrent();
    }

    /**
     *returns the left x-axis value*/
    public synchronized double getLeftXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftX) * 100.0) / 100.0;
    }

    /**
     *returns the left y-axis value*/
    public synchronized double getLeftYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisLeftY) * 100.0) / 100.0;
    }

    /**
     *returns the right x-axis value*/
    public synchronized double getRightXAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightX) * 100.0) / 100.0;
    }

    /**
     *returns the right y-axis value*/
    public synchronized double getRightYAxis(){
        return  Math.round(controller.getAxis(controller.getMapping().axisRightY) * 100.0) / 100.0;
    }

    /**
     *returns a boolean if the A button is pressed*/
    public synchronized boolean getA(){
        return controller.getButton(controller.getMapping().buttonA);
    }

    /**
     *returns a boolean if the B button is pressed*/
    public synchronized boolean getB(){
        return controller.getButton(controller.getMapping().buttonB);
    }

    /**
     *returns a boolean if the X button is pressed*/
    public synchronized boolean getX(){
        return controller.getButton(controller.getMapping().buttonX);
    }

    /**
     *returns a boolean if the Y button is pressed*/
    public synchronized boolean getY(){
        return controller.getButton(controller.getMapping().buttonY);
    }
}
