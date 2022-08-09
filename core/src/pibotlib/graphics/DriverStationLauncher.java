package pibotlib.graphics;

import Robot.Robot;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pibotlib.graphics.DriverStation;
import pibotlib.lib.addons.TimedRobotBase;

public class DriverStationLauncher extends Game {

	DriverStation driverStation = new DriverStation();

	public DriverStationLauncher(TimedRobotBase robot){
		driverStation.setRobot(robot);
	}
	
	@Override
	public void create () {
		setScreen(driverStation);
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
