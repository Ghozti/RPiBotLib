package pibotlib.lib.sim.utils.commands;

public abstract class SimCommand {

    private boolean isNew = true;

    public abstract void init();

    public abstract void execute();

    public abstract boolean isFinished();

    public abstract void end();

    protected boolean isNew(){
        return isNew;
    }

    protected void setUsed(){
        isNew = false;
    }
}
