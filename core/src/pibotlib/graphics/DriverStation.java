package pibotlib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pibotlib.graphics.utils.Font;
import Robot.Robot;
import pibotlib.lib.constants.Constants;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.lib.gamecontrollers.LocalXboxController;

public class DriverStation implements Screen {

    SpriteBatch batch;
    Texture img;
    DriverStationButton enableButton, disableButton , autoButton, teleopButton;
    com.badlogic.gdx.math.Rectangle mouseHitbox;
    Font font;
    LocalXboxController controller;

    @Override
    public void show() {
        System.out.println("new stuff");
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("PiBotLib Driver Station.png"));
        enableButton = new DriverStationButton("Enable (1).png",80,50);
        disableButton = new DriverStationButton("disable.png",280,50);
        autoButton = new DriverStationButton("auto.png",280,250);
        teleopButton = new DriverStationButton("teleop(1).png",80,250);
        mouseHitbox = new com.badlogic.gdx.math.Rectangle(Gdx.input.getX(),-Gdx.input.getY(),15,15);
        font = new Font(100);
        controller = new LocalXboxController();
        //Thread thread = new Thread(new Robot(controller));
        //thread.start();
    }

    private void update(){
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
        font.draw(batch,controller.getLeftXAxis() + " LX",600,600,0,false);
        font.draw(batch,controller.getLeftYAxis() + " LY",600,500,0,false);
        font.draw(batch,controller.getRightXAxis() + " RX",600,400,0,false);
        font.draw(batch,controller.getRightYAxis() + " RY",600,300,0,false);
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
            enableButton.changePath("Enable.png");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                if (!DriverStationState.getState().equals(Constants.DriverStationStates.ENABLED)) {
                    DriverStationState.switchState();
                }
            }
        }else {
            enableButton.changePath("Enable (1).png");
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
            disableButton.changePath("disable(1).png");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                if (!DriverStationState.getState().equals(Constants.DriverStationStates.DISABLED)) {
                    DriverStationState.switchState();
                }
            }
        }else {
            disableButton.changePath("disable.png");
        }
    }

    private void updateAutoButton(){
        if (autoButton.getHitbox().overlaps(mouseHitbox)){
            autoButton.changePath("auto(1).png");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                autoButton.changePath("auto(1).png");
                if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.AUTO)) {
                    DriverStationState.switchRobotMode();
                }
            }
        }else {
            if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.AUTO)) {
                autoButton.changePath("auto.png");
            }
        }
    }

    private void updateTeleopButton(){
        if (teleopButton.getHitbox().overlaps(mouseHitbox)){
            teleopButton.changePath("teleop(1).png");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                teleopButton.changePath("teleop(1).png");
                if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.TELEOP)) {
                    DriverStationState.switchRobotMode();
                }
            }
        }else {
            if (!DriverStationState.getRobotMode().equals(Constants.RobotSates.TELEOP)) {
                teleopButton.changePath("teleop.png");
            }
        }
    }
}
