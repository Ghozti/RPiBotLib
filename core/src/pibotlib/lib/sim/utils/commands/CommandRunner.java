package pibotlib.lib.sim.utils.commands;

import java.util.ArrayList;

public class CommandRunner {

    ArrayList<SimCommand> commandList;
    int current;
    SimCommand currentCommand;
    boolean endRun = false;

    public CommandRunner(ArrayList<SimCommand> list){
        current = 0;
        commandList = list;
        currentCommand = list.get(current);
    }

    private int next(){
        return current++;
    }

    public void runCommands(){
        if (!endRun) {
            if (currentCommand.isNew()) {
                currentCommand.init();
                currentCommand.setUsed();
            } else {
                if (currentCommand.isFinished()) {
                    currentCommand.end();
                    if(commandList.size()-1 == current){
                        endRun = true;
                    }else {
                        currentCommand = commandList.get(next());
                    }
                } else {
                    currentCommand.execute();
                    currentCommand.isFinished();
                }
            }
        }
    }
}
