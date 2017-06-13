package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/12.
 */

public class LeftCommand extends Command {

    @Override
    public void execute() {
        if (machine != null) {
            machine.left();
        }
    }
}
