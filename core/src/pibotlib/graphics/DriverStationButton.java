package pibotlib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import pibotlib.utils.constants.Constants;

public class DriverStationButton {
    private Texture texture;
    private String path;
    private float width, height, x ,y;
    private com.badlogic.gdx.math.Rectangle hitbox;

    public DriverStationButton(String defaultPath, float x, float y){
        this.path = defaultPath;
        this.texture = new Texture(Gdx.files.internal(defaultPath));
        this.width = Constants.Graphical.DriverStation.buttonWidth;
        this.height = Constants.Graphical.DriverStation.buttonHeight;
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x,y,width,height);
    }

    public void changePath(String path) {
        texture = new Texture(Gdx.files.internal(path));
    }

    public Texture getTexture(){
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }
}
