package pibotlib.lib.pid;

import pibotlib.lib.addons.sensors.DisplayAble;
import pibotlib.lib.time.ElapseTimer;

public class PIDController implements DisplayAble {

    final double kP;
    final double kI;
    final double kD;

    final double iLimit;
    double maxTolerance;
    double minTolerance;
    double target;
    double maxTarget;
    double minTarget;

    volatile double error = 0;
    volatile double output = 0;
    volatile double errorSum = 0;
    volatile double errorRate = 0;
    volatile double lastError = 0;
    volatile double currentTime = 0;
    volatile double sensorValue;

    volatile boolean greaterThanMax = false;
    volatile boolean lowerThanMin = false;
    volatile boolean isOnTarget = false;

    boolean autoKill;

    ElapseTimer timer;

    public PIDController(double kP, double kI, double kD, double iLimit, double maxTolerance, double minTolerance, double target){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.iLimit = iLimit;
        this.maxTolerance = maxTolerance;
        this.minTolerance = minTolerance;
        this.target = target;
        minTarget = target - minTolerance;
        maxTarget = target + maxTolerance;
        timer = new ElapseTimer();
    }

    public PIDController(double kP, double kI, double kD, double iLimit, double maxTolerance, double minTolerance, double target, boolean autoKill){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.iLimit = iLimit;
        this.maxTolerance = maxTolerance;
        this.minTolerance = minTolerance;
        this.target = target;
        minTarget = target - minTolerance;
        maxTarget = target + maxTolerance;
        this.autoKill = autoKill;
        timer = new ElapseTimer();
    }

    boolean timerStarted = false;

    public double getOutput(double sensorValue){

        this.sensorValue = sensorValue;

        if (!timerStarted){
            timer.startTimer();
            timerStarted = true;
        }
        currentTime = timer.getElapsedMilliseconds();
        if(sensorValue > target){
            error = target - sensorValue;
            greaterThanMax = true;
            lowerThanMin = false;
        }

        if(sensorValue < target){
            error = target - sensorValue;
            greaterThanMax = false;
            lowerThanMin = true;
        }

        if(Math.abs(error) < iLimit && Math.round(error) != 0){
            errorSum += error * currentTime;
        }

        errorRate = (error - lastError)/currentTime;
        lastError = error;

        isOnTarget = maxTarget >= sensorValue && minTarget <= sensorValue;

        if(isOnTarget && autoKill){
            error = 0;
            errorSum = 0;
            errorRate = 0;
            timer.reset();
            timer.stopTimer();
            timerStarted = false;
            return 0;
        }

        output = (kP * error) + (kI * errorSum) + (kD * errorRate);
        return output;//only PI for now
    }

    public void pause(){
        error = 0;
        errorSum = 0;
        errorRate = 0;
        timer.reset();
        timer.stopTimer();
        timerStarted = false;
    }

    public void setTarget(double minTolerance, double maxTolerance, double target){
        this.target = target;
        this.maxTolerance = maxTarget;
        this.minTolerance = minTolerance;
        this.maxTarget = target + maxTolerance;
        this.minTarget = target - minTolerance;
    }

    public double getP(){return kP;}
    public double getI(){return kI;}
    public double getD(){return kD;}

    public double getILimit(){return iLimit;}

    public double getMaxTolerance(){return maxTolerance;}
    public double getMinTolerance(){return minTolerance;}

    public synchronized double getError(){return error;}
    public synchronized double getErrorSum(){return errorSum;}

    public synchronized double getCurrentTime(){return currentTime;}

    public synchronized double getSensorValue(){return sensorValue;}

    public synchronized boolean isGreaterThanMax(){return greaterThanMax;}
    public synchronized boolean isLowerThanMin(){return lowerThanMin;}
    public synchronized boolean isOnTarget(){return isOnTarget;}

    @Override
    public String getValueToString() {
        return "error: " + getError() + "\n" + "sensor: " + getSensorValue() + "\n" + "output: " + output + "\n" + "is on target: " + isOnTarget() + "\n" + " Elapsed: " + getCurrentTime();
    }

    @Override
    public String getName() {
        return "PID Controller";
    }
}