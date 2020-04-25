package gameobjects.collectibles.shadows;

import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class AmmoShadow extends GameObjectShadow {

	/**
	 * Constructor.
	 * 
	 * @param float   x
	 * @param float   y
	 * @param float   width
	 * @param float   height
	 * @param Texture shadowTexture
	 */
	public AmmoShadow(float x, float y, float width, float height, Texture shadowTexture) {
		super(x, y, width, height, shadowTexture);
	}
}
