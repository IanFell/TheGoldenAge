package com.mygdx.mygame.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;

/**
 * Launch game on desktop.
 * 
 * @author Fabulous Fellini
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("artwork/logos/GoldenAgeIcon.png", FileType.Internal);
		config.title         = "GOLDENAGE";
		config.foregroundFPS = GameAttributeHelper.FRAMES_PER_SECOND;
		config.vSyncEnabled  = true;
		config.width         = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height        = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen    = true;
		new LwjglApplication(new MyGame(), config);
	}
}
