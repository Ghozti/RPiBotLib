package pibotlib.graphics;

import com.badlogic.gdx.Game;
import pibotlib.lib.sim.Robot;

public class SimLauncher extends Game {
    @Override
    public void create() {
        setScreen(new Robot());
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        screen.resize(width, height);
    }
}
