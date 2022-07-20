package pibotlib.graphics.utils;

import pibotlib.lib.constants.Constants;

public class DriverStationState {
        private static String state = Constants.DriverStationStates.DISABLED;

        public static void switchState(){
            if (state.equals(Constants.DriverStationStates.DISABLED)){
                state = Constants.DriverStationStates.ENABLED;
            }else if (state.equals(Constants.DriverStationStates.ENABLED)){
                state = Constants.DriverStationStates.DISABLED;
            }
        }

        public static void setKill(){
            state = Constants.DriverStationStates.KILL;
        }

        public static void setAuto(){
            state = Constants.DriverStationStates.AUTO;
        }

        public static String getState() {
            return state;
        }
}
