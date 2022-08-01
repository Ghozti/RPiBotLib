package pibotlib.lib.autonomous;

import java.util.ArrayList;

public class TimedAutoBase {

    ArrayList<TimedCommand> commands;
    boolean autoStarted;

    public TimedAutoBase(){
        commands = new ArrayList<>();
    }

    public void addCommand(TimedCommand command){
        commands.add(command);
    }

    public void runAuto() {
        //run auto here
        if (!autoStarted) {
            autoStarted = true;

            for (TimedCommand command : commands) {
                command.execute();
            }

            commands.clear();
        }
    }
}
