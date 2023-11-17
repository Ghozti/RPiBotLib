package pibotlib.lib.autonomous;

import pibotlib.lib.addons.sensors.UltraSonicSensor;

public class RoomMapper {

    /*
    * The RoomMapper class will be used for self navigation mapping within a room.
    * The robot will need several iterations of trial and error using the ultra sonic sensor to calculate it's distance to the nearest object
    * The robot will save each trial and use it to learn about it's environment and where the room's obstacles are
    * The robot will save this data in a matrix style file
    * The robot must start in the same position each time
    * Each time the robot hits an obstacle, it will try to turn or self correct itself to a direction where there is no obstacle
    * The robot will try to mark down the perimeter of the room first
    * ---------------------------
    * | x -------------------->x|
    * |                        ||
    * |                        ||
    * |                        ||
    * |                        ||
    * |                        ||
    * |                        ||
    * |                        v|
    * |                        x|
    * ---------------------------
     */

    UltraSonicSensor ultraSonicSensor;
    public RoomMapper(UltraSonicSensor ultraSonicSensor){
        this.ultraSonicSensor = ultraSonicSensor;
    }

}
