package pibotlib.lib.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Constants {

    public static class LibConstants {
        public final static String LIB_VERSION = "1.1.2";
    }

    public static class Graphical {

        public static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/assets.atlas"));

        public static class Screen {
            public static final float width = 1080;
            public static final float height = 720;
        }

        public static class DriverStation {
            public static final float buttonWidth = 150;
            public static final float buttonHeight = 150;
        }
    }

    public static class DriverStationStates{
        public static final String ENABLED = "enabled";
        public static final String DISABLED = "disabled";
        public static final String KILL = "kill";
    }

    public static class RobotSates{
        public static final String AUTO = "autonomous";
        public static final String TELEOP = "tele-op";
    }
}
