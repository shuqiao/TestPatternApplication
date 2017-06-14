package com.main.testpatternapplication.memento;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by shuqiao on 2017/6/14.
 */

public class NoteEditText extends EditText {

    public NoteEditText(Context context) {
        super(context);
    }

    public NoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Memento getSaveMemento() {
        String text = getText().toString();

        if (TextUtils.isEmpty(text)) {
            return null;
        } else {
            Memento memento = new Memento();
            memento.text = getText().toString();
            memento.cursor = getSelectionStart();
            return memento;
        }
    }

    public void restoreMemento(Memento memento) {
        if (memento == null) {
            return;
        }

        setText(memento.text);
        setSelection(memento.cursor);
    }
}
