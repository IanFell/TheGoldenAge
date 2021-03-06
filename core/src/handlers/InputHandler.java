package handlers;

import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import helpers.ControllerInputHelper;
import input.computer.Keyboard;
import input.computer.Mouse;
import input.controllers.Arcade;
import input.controllers.ControllerInput;
import input.controllers.LogitechF310;
import input.controllers.PlayStation4Pad;
import input.controllers.Switch;
import input.controllers.XBox360Pad;

/**
 * Handles all input, whether that be computer or game pads.
 * 
 * In this template, the keyboard and mouse have examples of moving the 
 * game world objects around.  The gamepads simply print out which buttons 
 * are pressed.
 * 
 * @author Fabulous Fellini
 *
 */
public class InputHandler {

	private Keyboard keyboard = new Keyboard();
	private Mouse mouse       = new Mouse();

	public final static int INPUT_COMPUTER   = 0;
	public final static int INPUT_CONTROLLER = 1;
	public final static int INPUT_ARCADE     = 2;

	public static int inputType = INPUT_COMPUTER;

	public static boolean isPlaystationController = false;

	/**
	 * GamePads.  This can be any gamepad, as it will
	 * be instantiated after we grab the controller's name.
	 */
	private ControllerInput controllerInput;

	/**
	 * Initialize input.  First we get the name of the first controller found.
	 * Then we use the name to instantiate a certain type of controller.
	 */
	public void init() {
		String controllerName = ControllerInputHelper.getControllerName();
		// System.out.println(controllerName + " found.");

		// Instantiate correct controller based off controller name.
		/*
		if (controllerName.contains("Logitech")) {
			controllerInput         = new LogitechF310();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = false;
		} 
		if (controllerName.contains("Xbox")) {
			controllerInput         = new XBox360Pad();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = false;
		}
		if (controllerName.contains("Wireless Controller")) {
			controllerInput         = new PlayStation4Pad();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = true;
		}
		if (controllerName.contains("Generic   USB  Joystick")) {
			controllerInput         = new Arcade();
			inputType               = INPUT_ARCADE;
			isPlaystationController = false;
		}*/






		if (controllerName.contains("Xbox")) {
			controllerInput         = new XBox360Pad();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = false;
		}
		else if (controllerName.contains("Wireless Controller")) {
			controllerInput         = new PlayStation4Pad();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = true;
		}
		else if (controllerName.contains("Generic   USB  Joystick")) {
			controllerInput         = new Arcade();
			inputType               = INPUT_ARCADE;
			isPlaystationController = false;
		}
		else if (!controllerName.contentEquals("No controller found")) {
			controllerInput         = new LogitechF310();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = false;
		}

		// This is a switch controller.  For some reason it registers as an XBox controller.
		// Put it down here so if we get this message it will load the switch controller and
		// not XBox.
		if (controllerName.contains("360 For Windows") || controllerName.contains("Pro Controller")) {
			controllerInput         = new Switch();
			inputType               = INPUT_CONTROLLER;
			isPlaystationController = false;
		}

		// If we have found a controller, initialize it.
		if (controllerInput != null) {
			controllerInput.init();
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void handleInput(MyGame myGame) {
		if (controllerInput != null) {
			handleControllerInput(myGame);
		} else {
			handleKeyboardAndMouse(myGame);
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void handleKeyboardAndMouse(MyGame myGame) {
		keyboard.handleInput(myGame);
		mouse.handleInput(myGame);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void handleControllerInput(MyGame myGame) {
		// Handle controller input if controller exists.
		if (controllerInput != null) {
			controllerInput.handleInput(PlayerController.getCurrentPlayer(myGame), myGame);
		}
	}
}
