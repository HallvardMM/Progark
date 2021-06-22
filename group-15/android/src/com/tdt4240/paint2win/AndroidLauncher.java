package com.tdt4240.paint2win;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tdt4240.paint2win.Paint2Win;

public class AndroidLauncher extends AndroidApplication {

	/**
	 * Initializes the game on android devices and opens the Paint2Win class in core
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Paint2Win(), config);
	}
}
