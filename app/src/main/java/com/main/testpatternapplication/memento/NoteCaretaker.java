package com.main.testpatternapplication.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class NoteCaretaker {
    private static final int MAX_NUM = 30;

    private int maxNum = MAX_NUM;
    private int index = 0;

    private List<Memento> mementoList;

    public NoteCaretaker() {
        this(MAX_NUM);
    }

    public NoteCaretaker(int maxNum) {
        this.maxNum = maxNum;
        this.index = 0;
        this.mementoList = new ArrayList<>(maxNum);
    }

    public synchronized void save(Memento memento) {
        if (memento == null) {
            return;
        }

        if (index == maxNum - 1) {
            mementoList.remove(0);
        }

        mementoList.add(memento);
        index = mementoList.size() - 1;

    }

    public synchronized Memento pre() {
        if (mementoList.size() == 0) {
            return null;
        } else {
            index = index > 0 ? (index - 1) : index;
            return mementoList.get(index);
        }
    }

    public synchronized Memento next() {
        int size = mementoList.size();
        if (size == 0) {
            return null;
        } else {
            index = index == (size - 1) ? (index) : (index + 1);
            return mementoList.get(index);
        }
    }
}
