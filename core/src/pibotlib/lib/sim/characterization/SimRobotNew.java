package pibotlib.lib.sim.characterization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import pibotlib.lib.sim.utils.constants.UnitConstants;
import pibotlib.lib.sim.utils.units.Units;

public class SimRobotNew {

    /*
     * ***ALL UNITS WILL BE TRANSLATED TO EITHER METERS, OR KILOGRAMS***
     */

    Sprite robotGraphics;
    float robotLength, robotWidth;
    float wheelDiameter;

    float robotWeight;

    float maxDistancePerSecond;

    float acceleration;

    int chassisMotorCount;

    float maxTorque, maxMotorRPM, gearRatio;

    // sim field coordinates
    float startX, startY;
    //physics
    float velocityX, velocityY;
    float accelerationX, accelerationY;

    float normalForce;
    float frictionalSlowDownFactor;

    float motorPower;
    //sim fields
    boolean hasMoved;

    float encoderTickCOunt = 36;//per meter
    float rightEncoder, leftEncoder;

    float ticksPerMeter = 10;

    public void setRobotChassisDimensions(float length, float width, Units dimentsionUnit) {
        switch (dimentsionUnit) {
            case INCHES:
                this.robotLength = (length / UnitConstants.Distance.inchToM);
                this.robotWidth = (width / UnitConstants.Distance.inchToM);
                break;
            case MILLIMETERS:
                this.robotLength = (length / UnitConstants.Distance.mmToM);
                this.robotWidth = (width / UnitConstants.Distance.mmToM);
                break;
            case METERS:
                this.robotLength = length;
                this.robotWidth = width;
                break;
        }

        robotLength *= (36/robotLength);
        robotWidth *= (36/robotWidth);
    }

    public void setRobotWheelDiameter(float wheelDiamater, Units wheelDiameterUnit) {
        switch (wheelDiameterUnit) {
            case INCHES:
                this.wheelDiameter = wheelDiamater / UnitConstants.Distance.inchToM;
                break;
            case MILLIMETERS:
                this.wheelDiameter = wheelDiamater / UnitConstants.Distance.mmToM;
                break;
            case METERS:
                this.wheelDiameter = wheelDiamater;
                break;
            case FEET:
                this.wheelDiameter = wheelDiamater/UnitConstants.Distance.feetToM;
                break;
        }
    }

    public void setMaxDistancePerSecond(float distancePerSecond, Units distanceUnit) {
        switch (distanceUnit) {
            case INCHES:
                this.maxDistancePerSecond = distancePerSecond / UnitConstants.Distance.inchToM;
                break;
            case MILLIMETERS:
                this.wheelDiameter = distancePerSecond / UnitConstants.Distance.mmToM;
                break;
            case METERS:
                this.maxDistancePerSecond = distancePerSecond;
                break;
            case FEET:
                this.maxDistancePerSecond = distancePerSecond/UnitConstants.Distance.feetToM;
                break;
        }
    }

    public void setRobotAcceleration(float acceleration, Units unit){
        switch (unit){
            case INCHES:
                this.acceleration = acceleration / UnitConstants.Distance.inchToM;
                break;
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

    public void setRobotWeight(float weight, Units weightUnit) {
        switch (weightUnit) {
            case POUNDS:
                robotWeight = weight / UnitConstants.Weight.LbToKg;
                break;
            case GRAMS:
                robotWeight = weight / UnitConstants.Weight.GtoKG;
                break;
            case KILOGRAMS:
                this.robotWeight = weight;
                break;
        }
    }

    public void setChassisMotors(int motorCount, float maxMotorTorque, float gearRatio, float maxRPM) {
        this.chassisMotorCount = motorCount;
        this.gearRatio = gearRatio;
        this.maxTorque = maxMotorTorque;
        this.maxMotorRPM = maxRPM;
    }

    public void buildRobot() {
        robotGraphics = new Sprite(new Texture(Gdx.files.internal("core/assets/simRobot.png")), 0, 0, (int) robotWidth, (int) robotLength);
        robotGraphics.setOriginCenter();
        robotGraphics.setPosition(400, 400);
        startX = 400;
        startY = 400;
    }

    public void drive(float outputX, float outputY) {
        float x = (float) ((-Math.sin((robotGraphics.getRotation()) * (Math.PI / 180))) * outputX) * Gdx.graphics.getDeltaTime();
        float y = (float) ((Math.cos((robotGraphics.getRotation()) * (Math.PI / 180))) * outputY) * Gdx.graphics.getDeltaTime();
        robotGraphics.translate(x, y);
    }

    private void getFrictionalSlowDown() {
        normalForce = (robotWeight * -9.8f);
        frictionalSlowDownFactor = .6f * normalForce;//.6 is the coef of friction
        frictionalSlowDownFactor = (frictionalSlowDownFactor/robotWeight);
    }


    float a_maxDistancePerSecond;
    float a_acceleration;

    public void setMotorPower(float motorPower){
        this.a_maxDistancePerSecond = maxDistancePerSecond * motorPower;
        this.a_acceleration = acceleration * motorPower;
    }

    public void resetEncoders(){
        rightEncoder = 0;
        leftEncoder = 0;
    }

    public float getLeftEncoder(){
        return leftEncoder;
    }

    public float getRightEncoder(){
        return rightEncoder;
    }

    private void updateEncoders(float left, float right) {
        leftEncoder += left * Gdx.graphics.getDeltaTime();
        rightEncoder += right * Gdx.graphics.getDeltaTime();
    }

    char lastPressed = ' ';
    float rotationalSlowDown;
    float rotationSpeed;

    float speedX, speedY;

    public void update(){
        drive(speedX*36,speedY*36);
    }

    public void draw(Batch batch){
        robotGraphics.draw(batch);
        arcadeDrive(0,0);
        update();
    }

    //TODO make the method below behave like an arcade drive

    public void arcadeDrive(float turnInput, float driveInput){
        setMotorPower(.5f);
        getFrictionalSlowDown();
        frictionalSlowDownFactor *= Gdx.graphics.getDeltaTime();

        if (!hasMoved){
            startX = robotGraphics.getX();
            startY = robotGraphics.getY();
        }

        hasMoved = true;

        if (Gdx.input.isKeyPressed(Input.Keys.E)){
            lastPressed = 'E';
            updateEncoders(encoderTickCOunt,-encoderTickCOunt);
            rotationSpeed += (a_acceleration);
            if (rotationSpeed > a_maxDistancePerSecond){
                rotationSpeed = a_maxDistancePerSecond;
            }
            robotGraphics.rotate(-rotationSpeed * 1.4f);
        }else if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            updateEncoders(-encoderTickCOunt,encoderTickCOunt);
            lastPressed = 'Q';
            rotationSpeed += a_acceleration;
            if (rotationSpeed > a_maxDistancePerSecond){
                rotationSpeed = a_maxDistancePerSecond;
            }
            robotGraphics.rotate(rotationSpeed * 1.4f);
        }else {
            rotationalSlowDown = ((frictionalSlowDownFactor*2));

            if (rotationSpeed > 0){
                rotationSpeed += rotationalSlowDown;
            }else{
                rotationSpeed = 0;
            }

            if (lastPressed == 'E')
                robotGraphics.rotate(-rotationSpeed * Gdx.graphics.getDeltaTime());

            if (lastPressed == 'Q')
                robotGraphics.rotate (rotationSpeed * Gdx.graphics.getDeltaTime());

        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            updateEncoders(encoderTickCOunt,encoderTickCOunt);
            speedX += a_acceleration;
            speedY += a_acceleration;
            if (speedX > a_maxDistancePerSecond){
                speedX = a_maxDistancePerSecond;
            }
            if (speedY > a_maxDistancePerSecond){
                speedY = a_maxDistancePerSecond;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            updateEncoders(-encoderTickCOunt,-encoderTickCOunt);
            speedX -= a_acceleration;
            speedY -= a_acceleration;
            if (speedX < -a_maxDistancePerSecond) {
                speedX = -a_maxDistancePerSecond;
            }
            if (speedY < -a_maxDistancePerSecond){
                speedY = -a_maxDistancePerSecond;
            }
        }else {
            if (speedX > a_maxDistancePerSecond%frictionalSlowDownFactor) {
                speedX += (frictionalSlowDownFactor);
            }else if(speedX < -a_maxDistancePerSecond%frictionalSlowDownFactor){
                speedX -= (frictionalSlowDownFactor);
            }else {
                speedX = 0;
            }

            if (speedY > a_maxDistancePerSecond%frictionalSlowDownFactor) {
                speedY += (frictionalSlowDownFactor);
            }else if(speedY < -a_maxDistancePerSecond%frictionalSlowDownFactor){
                speedY -= (frictionalSlowDownFactor);
            }else {
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
    }

    public void differentialDrive(float rotate, float drive){
        getFrictionalSlowDown();
        frictionalSlowDownFactor *= Gdx.graphics.getDeltaTime();

        if (!hasMoved){
            startX = robotGraphics.getX();
            startY = robotGraphics.getY();
        }

        hasMoved = true;

        if (rotate < 0){
            lastPressed = 'E';
            updateEncoders(encoderTickCOunt,-encoderTickCOunt);
            rotationSpeed += (a_acceleration);
            if (rotationSpeed > a_maxDistancePerSecond){
                rotationSpeed = a_maxDistancePerSecond;
            }
            robotGraphics.rotate(-rotationSpeed * 1.4f);
        }else if(rotate > 0){
            updateEncoders(-encoderTickCOunt,encoderTickCOunt);
            lastPressed = 'Q';
            rotationSpeed += a_acceleration;
            if (rotationSpeed > a_maxDistancePerSecond){
                rotationSpeed = a_maxDistancePerSecond;
            }
            robotGraphics.rotate(rotationSpeed * 1.4f);
        }else {
            rotationalSlowDown = ((frictionalSlowDownFactor*2));

            if (rotationSpeed > 0){
                rotationSpeed += rotationalSlowDown;
            }else{
                rotationSpeed = 0;
            }

            if (lastPressed == 'E')
                robotGraphics.rotate(-rotationSpeed * Gdx.graphics.getDeltaTime());

            if (lastPressed == 'Q')
                robotGraphics.rotate (rotationSpeed * Gdx.graphics.getDeltaTime());
        }

        if (drive > 0){

        }else if(drive < 0){

        }
    }
}