package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/12.
 */

public class UpCommand extends Command {
    @Override
    void execute() {
        if (machine != null) {
            machine.up();
        }
    }
}
