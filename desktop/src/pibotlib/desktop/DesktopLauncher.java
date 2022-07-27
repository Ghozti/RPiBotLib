package pibotlib.desktop;

import Robot.Robot;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pibotlib.DriverStationLauncher;
import pibotlib.lib.gamecontrollers.LocalXboxController;
import pibotlib.lib.time.ElapseTimer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setTitle("PiBot lib driver station");
		//config.setWindowSizeLimits(1080,720,1080,720);
		//config.setWindowedMode(1080,720);
		//config.setForegroundFPS(30);
		//config.setIdleFPS(10);
		//new Lwjgl3Application(new DriverStationLauncher(), config);
		Thread thread = new Thread(new Robot());
		thread.start();
	}
}
