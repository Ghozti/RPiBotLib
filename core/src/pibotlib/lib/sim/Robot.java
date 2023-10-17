package pibotlib.lib.sim;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pibotlib.lib.sim.characterization.SimRobotNew;
import pibotlib.lib.sim.utils.commands.CommandRunner;
import pibotlib.lib.sim.utils.commands.SimCommand;
import pibotlib.lib.sim.utils.units.Units;

import java.util.ArrayList;

public class Robot implements Screen {

    SpriteBatch batch;
    SimRobotNew robot;

    CommandRunner commandRunner;
    ArrayList<SimCommand> commands = new ArrayList<>();
    @Override
    public void show() {
        batch = new SpriteBatch();
        robot = new SimRobotNew();
        robot.setRobotWheelDiameter(6,Units.INCHES);
        robot.setRobotChassisDimensions(1,1,Units.METERS);
        robot.setMaxDistancePerSecond(23, Units.FEET);
        robot.setRobotWeight(56,Units.KILOGRAMS);
        robot.setRobotAcceleration(3,Units.FEET);
        robot.setChassisMotors(6,2.6f,10.75f,6000);
        robot.buildRobot();

        commands.add(new SampleCommand(robot));
        commandRunner = new CommandRunner(commands);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0,0, 1f);
        batch.begin();
        robot.setMotorPower(1f);
        robot.draw(batch);
        commandRunner.runCommands();
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
    public void dispose() {
        batch.dispose();
    }
}
