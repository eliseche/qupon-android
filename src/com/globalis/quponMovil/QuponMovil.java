package com.globalis.quponMovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class QuponMovil extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent loginIntent = new Intent(this, Login.class);        
        startActivity(loginIntent);
    }
}