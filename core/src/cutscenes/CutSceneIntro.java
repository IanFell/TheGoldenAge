package cutscenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;

/**
 * This is an outdated cutscene.  The "intro" cutscene now is CutSceneJollyRoger.
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneIntro extends CutScene {

	private int dialogueLength;
	private int dialogueOffset;
	private int startXPosition;
	private int startYPosition;
	private int width;
	private int height;
	private float boatSpeed;
	private float boatStartXPosition;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutSceneIntro(String name) {
		super(name);
		dialogueLength          = 200;
		dialogueOffset          = 50;
		startXPosition          = 90;
		startYPosition          = 10;
		width                   = 7;
		height                  = 3;
		boatSpeed               = 0.0075f;
		boatStartXPosition      = startXPosition - 2;
		anyCutSceneIsInProgress = true;
		
		/**
		 * Comment this out to play the fullength cutscene.
		 * Else start cutscene at very end so it also renders the UI.
		 */
		//timer = dialogueLength * 9 - 2;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderCutScene(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		/*
		batch.draw(
				imageLoader.boatSide,
				boatStartXPosition, 
				startYPosition - 1,
				1,
				-1
				);
		if (!cutSceneConcluded) {
			if (timer > 0 && timer < dialogueLength) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[0],
						startXPosition, 
						startYPosition + 1,
						width,
						-height
						);
			}
			if (timer > dialogueLength - dialogueOffset  && timer < dialogueLength * 2) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[1],
						startXPosition + 3.5f, 
						startYPosition + 4.5f,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 2 - dialogueOffset  && timer < dialogueLength * 3) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[2],
						startXPosition + 4.5f, 
						(float) (startYPosition + 0.5f),
						width,
						-height
						);
			}
			if (timer > dialogueLength * 3 - dialogueOffset  && timer < dialogueLength * 4) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[3],
						startXPosition - 1, 
						startYPosition + 4.5f,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 4 - dialogueOffset  && timer < dialogueLength * 5) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[4],
						startXPosition - 1.5f, 
						startYPosition + 0.5f,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 5 - dialogueOffset  && timer < dialogueLength * 6) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[5],
						startXPosition + 4.5f, 
						startYPosition + 4,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 6 - dialogueOffset  && timer < dialogueLength * 7) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[6],
						startXPosition - 1, 
						startYPosition + 0.5f,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 7 - dialogueOffset  && timer < dialogueLength * 8) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[7],
						startXPosition + 4, 
						startYPosition + 4,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 8 - dialogueOffset  && timer < dialogueLength * 9) {
				batch.draw(
						imageLoader.cutSceneDialogueOne[8],
						startXPosition - 1, 
						startYPosition + 0.5f,
						width,
						-height
						);
			}
			if (timer > dialogueLength * 9) {
				cutSceneConcluded            = true;
				selectedCutSceneIsInProgress = false;
				anyCutSceneIsInProgress      = false;
			}
		} */
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	public void updateCutScene(MyGame myGame) {
		super.updateCutScene(myGame);
		boatStartXPosition += boatSpeed;
	}
}
