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
            command.execute(null,0,0);
            command = null;
        }
    }

    public static void main(String[] args) {
        TimedAutoBase base = new TimedAutoBase();

        base.addCommand(new TimedCommand(5000,"command 1"));
        base.addCommand(new TimedCommand(5000,"command 2"));
        base.addCommand(new TimedCommand(5000,"command 3"));

        base.runAuto();
    }
}
