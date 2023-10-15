package pibotlib.lib.sim.utils;

public interface SimCommand {

    void init();

    void execute();

    boolean isFinished();

    void end();
}
