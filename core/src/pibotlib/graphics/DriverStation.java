package pibotlib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pibotlib.graphics.utils.Font;
import pibotlib.lib.Robot;
import pibotlib.utils.constants.Constants;
import pibotlib.graphics.utils.DriverStationState;
import pibotlib.utils.gamecontrollers.LocalXboxController;

public class DriverStation implements Screen {

    SpriteBatch batch;
    Texture img;
    DriverStationButton enableButton, disableButton;
    com.badlogic.gdx.math.Rectangle mouseHitbox;
    Font font;
    Music enableSound, disableSound;
    Robot robot;
    LocalXboxController controller;

    public DriverStation(){
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("PiBotLib Driver Station.png"));
        enableButton = new DriverStationButton("Enable (1).png",80,50);
        disableButton = new DriverStationButton("disable.png",280,50);
        mouseHitbox = new com.badlogic.gdx.math.Rectangle(Gdx.input.getX(),-Gdx.input.getY(),15,15);
        font = new Font(100);
        enableSound = Gdx.audio.newMusic(Gdx.files.internal("autonstart.mp3"));
        disableSound = Gdx.audio.newMusic(Gdx.files.internal("buzzer.mp3"));
        //Thread thread = new Thread(new Robot());
        //thread.start();
        controller = new LocalXboxController();
    }

    private void update(){
        mouseHitbox.x = Gdx.input.getX();
        mouseHitbox.y = Math.abs(Gdx.input.getY() - (int) Constants.Graphical.Screen.height);
        updateEnableButton();
        updateDisableButton();
        if (DriverStationState.getState().equals("Enabled") && robot != null){
            robot.runRobot();
        }
    }

    @Override
    public void render(float delta) {
        update();
        ScreenUtils.clear(0, 0,0, 1f);
        batch.begin();
        batch.draw(img,0,0,1080,720);
        batch.draw(enableButton.getTexture(),enableButton.getX(),enableButton.getY(),enableButton.getWidth(), enableButton.getHeight());
        batch.draw(disableButton.getTexture(),disableButton.getX(),disableButton.getY(),disableButton.getWidth(), disableButton.getHeight());
        font.draw(batch,"Robot State: ",480,200,0,false);
        font.draw(batch, DriverStationState.getState(),480,100,0,false);
        font.draw(batch,controller.getLeftXAxis() + "",600,600,0,false);
        font.draw(batch,controller.getLeftYAxis() + "",600,500,0,false);
        font.draw(batch,controller.getRightXAxis() + "",600,400,0,false);
        font.draw(batch,controller.getRightYAxis() + "",600,300,0,false);
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
                if (!DriverStationState.getState().equals("Enabled")) {
                    DriverStationState.switchState();
                    enableSound.play();
                    disableSound.stop();
                }
            }
        }else {
            enableButton.changePath("Enable (1).png");
        }
    }

    private void updateDisableButton(){

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if (!DriverStationState.getState().equals("Disabled")) {
                DriverStationState.switchState();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)){
            DriverStationState.setKill();
        }

        if (disableButton.getHitbox().overlaps(mouseHitbox)){
            disableButton.changePath("disable(1).png");
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                if (!DriverStationState.getState().equals("Disabled")) {
                    DriverStationState.switchState();
                    disableSound.play();
                    enableSound.stop();
                }
            }
        }else {
            disableButton.changePath("disable.png");
        }
    }
}
