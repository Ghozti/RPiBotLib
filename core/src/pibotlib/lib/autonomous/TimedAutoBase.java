package pibotlib.lib.autonomous;

import java.util.ArrayList;

/**
 *class used to develop timed-based autonomous code*/
public class TimedAutoBase {

    private final ArrayList<TimedCommand> commands;
    boolean autoStarted;


    public TimedAutoBase(){
        commands = new ArrayList<>();
    }

    /**
     *will add a new TimedCommand to the commands list*/
    public void addCommand(TimedCommand command){
        commands.add(command);
    }

    /**
     *will execute every command in order*/
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
