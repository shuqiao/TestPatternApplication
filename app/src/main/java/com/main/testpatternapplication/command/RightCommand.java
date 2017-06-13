package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/12.
 */

public class RightCommand extends Command {
    @Override
    void execute() {
        if (machine != null) {
            machine.right();
        }
    }
}
