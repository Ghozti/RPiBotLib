package pibotlib.lib.sim.characterization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import pibotlib.lib.sim.utils.constants.UnitConstants;
import pibotlib.lib.sim.utils.units.Units;
import pibotlib.lib.time.ElapseTimer;

public class SimRobot {

    //robot attributes
    Sprite robotGraphics;
    double robotLength;
    double robotWidth;
    double robotWheelDiameter;

    double robotWeight;

    double maxDistancePerSecond;

    int motorCount;

    float maxTorque;

    float maxMotorRPM;

    float gearRatio;

    //bla bla bla x y bla bla

    float startX, startY;
    float speedX,speedY;

    //physics shinanigans
    volatile float velocityX;
    volatile float velocityY;

    float acceleration;

    float normalForce;

    float slowDownFactor;

    float accelerationX;
    float accelerationY;

    ElapseTimer timer = new ElapseTimer();

    Units dimensionsUnit, wheelDiameterUnit, distanceUnit, weightUnit;
    public void setRobotChassisDimensions(double robotLength, double robotWidth, Units unit){
        this.robotLength = robotLength * 32;
        this.robotWidth = robotWidth * 32;
        this.dimensionsUnit = unit;
    }

    public void setRobotWheelDiameter(double robotWheelDiameter,Units unit){
        this.robotWheelDiameter = robotWheelDiameter;
        this.wheelDiameterUnit = unit;
        }

    public void setMaxDistancePerSecond(double distancePerSecond,Units unit) {
        this.maxDistancePerSecond = distancePerSecond;
        this.distanceUnit = unit;
        switch (unit){
            case METERS:
                this.maxDistancePerSecond = distancePerSecond;
                break;
            case FEET:
                this.maxDistancePerSecond = distancePerSecond/UnitConstants.Distance.feetToM;
                break;
            case MILLIMETERS:
                this.maxDistancePerSecond = distancePerSecond/UnitConstants.Distance.mmToM;
                break;
        }
    }

    public void setRobotAcceleration(float acceleration, Units unit){
        switch (unit){
            case METERS:
                this.acceleration = acceleration;
                break;
            case FEET:
                this.acceleration = acceleration/UnitConstants.Distance.feetToM;
                break;
            case MILLIMETERS:
                this.acceleration = acceleration/UnitConstants.Distance.mmToM;
                break;
        }
    }

    public void setRobotWeight(double weight, Units weightUnit){
        this.robotWeight = weight;
        this.weightUnit = weightUnit;
        switch (weightUnit){
            case POUNDS:
                robotWeight /= UnitConstants.Weight.LbToKg;
                break;
            case GRAMS:
                robotWeight /= UnitConstants.Weight.GtoKG;
                break;
            case KILOGRAMS:
                this.robotWeight = weight;
                break;
        }
    }

    public void setChassisMotors(int motorCount, float maxMotorTorque, float gearRatio, float maxRPM){
        this.motorCount = motorCount;
        this.gearRatio = gearRatio;
        this.maxTorque = maxMotorTorque;
        this.maxMotorRPM = maxRPM;
    }

    public void buildRobot(){
        robotGraphics = new Sprite(new Texture(Gdx.files.internal("core/assets/simRobot.png")),0,0, (int) robotWidth, (int) robotLength);
        robotGraphics.setOriginCenter();
        robotGraphics.setPosition(400,400);
        startX = 400;
        startY = 400;
    }

    /*
    * 32 x 32 is the pixel size of the robot image
    * 64 px in the screen will equal to 1 inch irl
    * a robot of size of 1 x 1 will be using a scale of 1
     */

    public void drive(float outputX,float outputY){
        float x = (float) ((-Math.sin((robotGraphics.getRotation()) * (Math.PI /180))) * outputX) * Gdx.graphics.getDeltaTime();
        float y = (float) ((Math.cos((robotGraphics.getRotation()) * (Math.PI /180))) * outputY) * Gdx.graphics.getDeltaTime();
        robotGraphics.translate(x, y);
    }

    boolean timerStart = false;
    boolean timerPause = false;
    boolean timerResume = false;
    float c = 0;
    public void update(){
        normalForce = (float) (robotWeight * -9.8);//measured in newtowns
        slowDownFactor = (float) (.6 * normalForce);//TODO save it as constant
        slowDownFactor = (float) (slowDownFactor/robotWeight)*Gdx.graphics.getDeltaTime();
        if (speedX != 0 && speedY != 0){
            velocityX = (float) (((Math.abs(robotGraphics.getX())- Math.abs(startX)))/ timer.elapsedSeconds);
            velocityY = (float) (((Math.abs(robotGraphics.getY())- Math.abs(startY)))/ timer.elapsedSeconds);
        }else {
            velocityX = 0;
        }

        if (hasMoved){
            if (!timerStart){
                timer.startTimer();
                timerStart = true;
            }

            if (!timerResume){
                timerPause = false;
                timerResume = true;
                timer.resumeTimer();
            }
        }else {
            if (!timerPause){
                timerResume = false;
                timerPause = true;
                timer.reset();
            }
            startX = robotGraphics.getX();
            startY = robotGraphics.getY();
        }
    }

    boolean hasMoved = false;
    float x = 0,y = 0;
    float rotationSlowDown = 0;
    float rotationSpeed = 0;
    float rotationSlowDownLog = 0;
    char lastPressed = ' ';

    public void draw(Batch batch){
        robotGraphics.draw(batch);
        if (Gdx.input.isKeyPressed(Input.Keys.E)){
            if (Gdx.input.isKeyJustPressed(Input.Keys.E) && lastPressed == 'Q'){
                rotationSpeed = -rotationSpeed * .8f;
            }
            robotGraphics.rotate(-rotationSpeed);
            lastPressed = 'E';
            rotationSpeed += -(slowDownFactor);
            if (rotationSpeed > maxDistancePerSecond){
                rotationSpeed = (float) maxDistancePerSecond;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && lastPressed == 'E'){
                rotationSpeed = -rotationSpeed * .8f;
            }
            robotGraphics.rotate(rotationSpeed);
            lastPressed = 'Q';
            rotationSpeed += -(slowDownFactor);
            if (rotationSpeed > maxDistancePerSecond){
                rotationSpeed = (float) maxDistancePerSecond;
            }
        }else {
            rotationSlowDown = ((slowDownFactor*2));

            if (rotationSpeed > 0){
                rotationSpeed += rotationSlowDown;
            }else{
                rotationSpeed = 0;
            }

            if (lastPressed == 'E')
                robotGraphics.rotate(-rotationSpeed * Gdx.graphics.getDeltaTime());

            if (lastPressed == 'Q')
                robotGraphics.rotate (rotationSpeed * Gdx.graphics.getDeltaTime());

            rotationSlowDownLog = robotGraphics.getRotation() + rotationSpeed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            x += acceleration;
            y += acceleration;
            speedX += acceleration;
            speedY += acceleration;
            if (x > maxDistancePerSecond){
                x = (float) maxDistancePerSecond;
                speedX = (float) maxDistancePerSecond;
            }
            if (y > maxDistancePerSecond){
                y = (float) maxDistancePerSecond;
                speedY = (float) maxDistancePerSecond;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            x -= acceleration;
            y -= acceleration;
            speedX -= acceleration;
            speedY -= acceleration;
            if (x < -.1){
                x = (float) -maxDistancePerSecond;
                speedX = (float) -maxDistancePerSecond;
            }
            if (y < -maxDistancePerSecond){
                y = (float) -maxDistancePerSecond;
                speedY = (float) -maxDistancePerSecond;
            }
        }else {
            if (x > .1) {
                x += (slowDownFactor);
                speedX += (slowDownFactor);
            }else if(x < -maxDistancePerSecond%slowDownFactor){
                x -= (slowDownFactor);
                speedX -= (slowDownFactor);
            }else {
                x = 0;
                speedX = 0;
            }

            if (y > .1) {
                y += (slowDownFactor);
                speedY += (slowDownFactor);
            }else if(y < -.1){
                y -= (slowDownFactor);
                speedY -= (slowDownFactor);
            }else {
                y = 0;
                speedY = 0;
            }
        }

        if(speedX != 0 || speedY != 0){
            if (!hasMoved) {
                startX = robotGraphics.getX();
                startY = robotGraphics.getY();
                hasMoved = true;
            }
        }else {
            hasMoved = false;
        }
        drive(x,y);
        update();
    }
}
