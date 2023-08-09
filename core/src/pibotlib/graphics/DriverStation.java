package pibotlib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import pibotlib.graphics.utils.Font;
import pibotlib.lib.addons.TimedRobotBase;
import pibotlib.lib.addons.sensors.DisplayAble;
import pibotlib.lib.constants.Constants;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.gamecontrollers.LocalXboxController;

import java.util.ArrayList;

public class DriverStation implements Screen {

    SpriteBatch batch;
    TextureRegion img;
    DriverStationButton enableButton, disableButton , autoButton, teleopButton;
    com.badlogic.gdx.math.Rectangle mouseHitbox;
    Font font, versionFont, interfaceFont, displayableFont;
    LocalXboxController controller;
    static ArrayList<DisplayAble> sensors = new ArrayList<>();
    static ArrayList<DisplayAble> pidControllers = new ArrayList<>();
    TimedRobotBase robot;
    boolean robotStarted;

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = Constants.Graphical.atlas.findRegion("PiBotLib Driver Station");
        enableButton = new DriverStationButton("enable1",80,50);
        disableButton = new DriverStationButton("disable",280,50);
        autoButton = new DriverStationButton("auto",280,250);
        teleopButton = new DriverStationButton("teleop1",80,250);
        mouseHitbox = new com.badlogic.gdx.math.Rectangle(Gdx.input.getX(),-Gdx.input.getY(),15,15);
        font = new Font(100);
        versionFont = new Font(20);
        interfaceFont = new Font(35);
        displayableFont = new Font(23);

        try {
            controller = new LocalXboxController();
        }catch (Exception e){
            System.err.println("Xbox controller not found...");;
        }
    }

    private void update(){
        if (robot != null && !robotStarted){
            Thread thread = new Thread(robot);
            thread.start();
            robotStarted = true;
        }
        mouseHitbox.x = Gdx.input.getX();
        mouseHitbox.y = Math.abs(Gdx.input.getY() - (int) Constants.Graphical.Screen.height);
        updateEnableButton();
        updateDisableButton();
        updateAutoButton();
        updateTeleopButton();
    }

    @Override
    public void render(float delta) {
        update();
        ScreenUtils.clear(0, 0,0, 1f);
        batch.begin();
        batch.draw(img,0,0,1080,720);
        batch.draw(enableButton.getTexture(),enableButton.getX(),enableButton.getY(),enableButton.getWidth(), enableButton.getHeight());
        batch.draw(disableButton.getTexture(),disableButton.getX(),disableButton.getY(),disableButton.getWidth(), disableButton.getHeight());
        batch.draw(autoButton.getTexture(),autoButton.getX(),autoButton.getY(),autoButton.getWidth(), autoButton.getHeight());
        batch.draw(teleopButton.getTexture(),teleopButton.getX(),teleopButton.getY(),teleopButton.getWidth(), teleopButton.getHeight());

        font.draw(batch,"Robot State: ",480,200,0,false);
        font.draw(batch,"Robot Mode: ",480,400,0,false);

        font.draw(batch, DriverStationState.getState(),480,100,0,false);
        font.draw(batch, DriverStationState.getRobotMode(),480,300,0,false);

        interfaceFont.draw(batch,"Controller: ",80,590,0,false);

        try {
            interfaceFont.draw(batch, controller.getLeftXAxis() + " LX", 80, 520, 0, false);
            interfaceFont.draw(batch, controller.getLeftYAxis() + " LY", 80, 450, 0, false);
            interfaceFont.draw(batch, controller.getRightXAxis() + " RX", 250, 520, 0, false);
            interfaceFont.draw(batch, controller.getRightYAxis() + " RY", 250, 450, 0, false);
        }catch (Exception e){

        }

        interfaceFont.draw(batch,"Sensors:",480,590,0,false);//sensors.get(0).getName()
        if ((sensors.size() >= 1)) {
            displayableFont.draw(batch, sensors.get(0).getName() + " :", 420, 550, 0, false);
            displayableFont.draw(batch, sensors.get(0).getValueToString(), 615, 550, 0, false);
        }
        if ((sensors.size() >= 2)) {
            displayableFont.draw(batch, sensors.get(1).getName() + " :", 420, 485, 0, false);
            displayableFont.draw(batch, sensors.get(1).getValueToString(), 615, 485, 0, false);
        }
        if ((sensors.size() == 3)) {
            displayableFont.draw(batch, sensors.get(2).getName() + " :", 420, 450, 0, false);
            displayableFont.draw(batch, sensors.get(2).getValueToString(), 615, 450, 0, false);
        }

        interfaceFont.draw(batch,"PID:",930,590,0,false);
        if ((pidControllers.size() == 1)) {
            displayableFont.draw(batch, pidControllers.get(0).getName() + " :", 850, 550, 0, false);
            displayableFont.draw(batch, pidControllers.get(0).getValueToString(), 850, 520, 0, false);
        }
        versionFont.draw(batch,"Version: " + Constants.LibConstants.LIB_VERSION ,920,20,0,false);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    private void updateEnableButton(){
        if (enableButton.getHitbox().overlaps(mouseHitbox)){
            enableButton.changePath("enable");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                if (!DriverStationState.getState().equals(Constants.DriverStationStates.ENABLED)) {
                    DriverStationState.switchState();
                }
            }
        }else {
            enableButton.changePath("enable1");
        }
    }

    private void updateDisableButton(){

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if (!DriverStationState.getState().equals(Constants.DriverStationStates.DISABLED)) {
                DriverStationState.switchState();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)){
            DriverStationState.setKill();
        }

        if (disableButton.getHitbox().overlaps(mouseHitbox)){
            disableButton.changePath("disable1");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                if (!DriverStationState.getState().equals(Constants.DriverStationStates.DISABLED)) {
                    DriverStationState.switchState();
                }
            }
        }else {
            disableButton.changePath("disable");
        }
    }

    private void updateAutoButton(){
        if (autoButton.getHitbox().overlaps(mouseHitbox)){
            autoButton.changePath("auto1");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                autoButton.changePath("auto1");
                if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.AUTO)) {
                    DriverStationState.switchRobotMode();
                }
            }
        }else {
            if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.AUTO)) {
                autoButton.changePath("auto");
            }
        }
    }

    private void updateTeleopButton(){
        if (teleopButton.getHitbox().overlaps(mouseHitbox)){
            teleopButton.changePath("teleop1");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                teleopButton.changePath("teleop1");
                if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.TELEOP)) {
                    DriverStationState.switchRobotMode();
                }
            }
        }else {
            if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.TELEOP)) {
                teleopButton.changePath("teleop");
            }
        }
    }

    /**
     *adds a sensor to the graphical driver station. NOTE: only 3 sensors are allowed at a time*/
    public static void addSensor(DisplayAble sensor){
        if (sensors.size() == 3){
            System.out.println("Driver station sensor capacity full");
        }else {
            sensors.add(sensor);
        }
    }

    public static void addPidController(DisplayAble controller){
        if (pidControllers.size() == 1){
            System.out.println("Driver station sensor capacity full");
        }else {
            pidControllers.add(controller);
        }
    }

    public void setRobot(TimedRobotBase robot){
        this.robot = robot;
    }
}
