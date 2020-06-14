package cutscenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import loaders.ImageLoader;

/**
 * This is the intro cutscene, when Jolly Roger explains the point of the game.
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneJollyRoger extends CutScene {
	
	private int startXPosition;
	private int startYPosition;
	private float boatSpeed;
	private float boatStartXPosition;
	private int width;
	private int height;
	private int boatSize;
	
	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutSceneJollyRoger(String name) {
		super(name);
		startXPosition          = 90;
		startYPosition          = 10;
		width                   = 10;
		height                  = 7;
		boatSpeed               = 0.0075f;
		boatStartXPosition      = startXPosition - 2;
		anyCutSceneIsInProgress = true;
		boatSize                = 1;
	}
	
	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderCutScene(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.boatSide,
				boatStartXPosition, 
				startYPosition - 1,
				boatSize,
				-boatSize
				);
		if (!cutSceneConcluded) {
				batch.draw(
						imageLoader.cutsceneJollyRoger,
						startXPosition, 
						startYPosition + 4,
						width,
						-height
						);
		}
	}
	
	@Override
	public void updateCutScene() {
		super.updateCutScene();
		boatStartXPosition += boatSpeed;
		
		// if cutscene is over, use endCutScene() in parent class.
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getStartXPosition() {
		return startXPosition;
	}

	/**
	 * 
	 * @return int
	 */
	public int getStartYPosition() {
		return startYPosition;
	}
}
