package com.karimi.seller.widget.text_watcher;

import android.text.Editable;
import android.text.TextWatcher;

public class EditTextWatcher implements TextWatcher {

    private ListenerTextWatcher listenerTextWatcher;

    public EditTextWatcher(ListenerTextWatcher listener) {
        this.listenerTextWatcher = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (listenerTextWatcher != null){
            if (s == null || s.length() == 0 ){
                listenerTextWatcher.onTextChanged(null);
            }else listenerTextWatcher.onTextChanged(s.toString());
        }
    }
}
