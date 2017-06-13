package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/12.
 */

public class DownCommand extends Command {
    @Override
    void execute() {
        if (machine != null) {
            machine.down();
        }
    }
}
