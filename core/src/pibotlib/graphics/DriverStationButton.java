package pibotlib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import pibotlib.lib.constants.Constants;

public class DriverStationButton {
    private TextureRegion texture;
    private float width, height, x ,y;
    private com.badlogic.gdx.math.Rectangle hitbox;

    public DriverStationButton(String path, float x, float y){
        this.texture = Constants.Graphical.atlas.findRegion(path);
        this.width = Constants.Graphical.DriverStation.buttonWidth;
        this.height = Constants.Graphical.DriverStation.buttonHeight;
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x,y,width,height);
    }

    public void changePath(String path) {
        texture = Constants.Graphical.atlas.findRegion(path);
    }

    public TextureRegion getTexture(){
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
