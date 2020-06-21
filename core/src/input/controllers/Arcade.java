package input.controllers;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;

public class Arcade extends ControllerInput {
	
	/**
	 * Constructor.
	 */
	public Arcade() {
		super();
		this.BUTTON_ATTACK = 0;
		this.BUTTON_JUMP   = 1;
		this.BUTTON_UI     = 2;
		this.BUTTON_SELECT = 3;
		
		//System.exit(0);
	}
	
	/**
	 * 
	 * @param GameObject player
	 */
	@Override
	protected void pollMainFourButtons(GameObject player, MyGame myGame) {
		System.out.println(controller.getButton(0));
		System.out.println("getting here");
		System.out.println(controller.getButton(0));
		if(controller.getButton(BUTTON_ATTACK)) {
			
		}
	}

}
