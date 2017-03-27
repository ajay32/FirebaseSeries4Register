package com.hackingbuzz.firebaseseries4register.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hackingbuzz.firebaseseries4register.activites.MainActivity;

/**
 * Created by Avi Hacker on 3/26/2017.
 */

public class InputValidation {

    private Context context;

    // context just tell you . this class this connected with its main class where context is comming from
    public InputValidation(Context context) {
        this.context = context;
    }

    // matching password and confirm password

    public boolean isEditTextMatches(EditText editText1, EditText editText2) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            hideKeyboardFrom(editText2);
            return false;
        } else {

        }
        return true;
    }


    // method to Hide keyboard
    private void hideKeyboardFrom(View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
