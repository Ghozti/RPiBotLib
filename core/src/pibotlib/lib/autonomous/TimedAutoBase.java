package pibotlib.lib.autonomous;

import java.util.ArrayList;

public class TimedAutoBase {

    ArrayList<TimedCommand> commands;

    public TimedAutoBase(){
        commands = new ArrayList<>();
    }

    public void addCommand(TimedCommand command){
        commands.add(command);
    }

    public void runAuto() {
        //run auto here
        for (TimedCommand command : commands) {
            command.execute();
        }
    }
}
