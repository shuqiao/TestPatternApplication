package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/12.
 */

public abstract class Command {
    protected TetrisMachine machine;

    abstract void execute();

    public void setTetrisMachine(TetrisMachine machine) {
        this.machine = machine;
    }
}
