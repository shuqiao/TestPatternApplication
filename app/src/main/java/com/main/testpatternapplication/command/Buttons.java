package com.main.testpatternapplication.command;

/**
 * Created by shuqiao on 2017/6/13.
 */

public class Buttons {
    private LeftCommand leftCommand = new LeftCommand();
    private RightCommand rightCommand = new RightCommand();
    private UpCommand upCommand = new UpCommand();
    private DownCommand downCommand = new DownCommand();

    public void setLeftCommand(LeftCommand leftCommand) {
        this.leftCommand = leftCommand;
    }

    public void setRightCommand(RightCommand rightCommand) {
        this.rightCommand = rightCommand;
    }

    public void setUpCommand(UpCommand upCommand) {
        this.upCommand = upCommand;
    }

    public void setDownCommand(DownCommand downCommand) {
        this.downCommand = downCommand;
    }

    public void left() {
        if (leftCommand != null) {
            leftCommand.execute();
        }
    }

    public void right() {
        if (rightCommand != null) {
            rightCommand.execute();
        }
    }

    public void up() {
        if (upCommand != null) {
            upCommand.execute();
        }
    }

    public void down() {
        if (downCommand != null) {
            downCommand.execute();
        }
    }
}
