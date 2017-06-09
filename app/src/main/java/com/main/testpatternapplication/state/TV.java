package com.main.testpatternapplication.state;

/**
 * Created by shuqiao on 2017/6/9.
 */

public class TV {
    private TvState state = new TvOffState();

    public void on() {
        state = new TvOnState();
    }

    public void off() {
        state = new TvOffState()   ;
    }

    public void next() {
        state.nextChannel();
    }

    public void pre() {
        state.preChannel();
    }

    public void up() {
        state.turnUp();
    }

    public void down() {
        state.turnDown();
    }

    public void setState(TvState state) {
        this.state = state;
    }
}
