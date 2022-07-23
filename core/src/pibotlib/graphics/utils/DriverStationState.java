package pibotlib.graphics.utils;

import pibotlib.lib.constants.Constants;

public class DriverStationState {

        private static volatile String state = Constants.DriverStationStates.DISABLED;
        private static volatile String robotMode = Constants.RobotSates.TELEOP;

        public static void switchState(){
            if (state.equals(Constants.DriverStationStates.DISABLED)){
                state = Constants.DriverStationStates.ENABLED;
            }else if (state.equals(Constants.DriverStationStates.ENABLED)){
                state = Constants.DriverStationStates.DISABLED;
            }
        }

        public static void switchRobotMode(){
            if (robotMode.equals(Constants.RobotSates.AUTO)){
                robotMode = Constants.RobotSates.TELEOP;
            }else if (robotMode.equals(Constants.RobotSates.TELEOP)){
                robotMode = Constants.RobotSates.AUTO;
            }
        }

        public static void setKill(){
            state = Constants.DriverStationStates.KILL;
        }

        public static String getState() {
            return state;
        }

        public static String getRobotMode(){return robotMode;}
}
