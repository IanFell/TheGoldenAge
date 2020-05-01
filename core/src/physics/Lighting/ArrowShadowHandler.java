package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.arrowhandler.ArrowHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowShadowHandler {

	public void renderArrowShadows(SpriteBatch batch, ImageLoader imageLoader) {
		float offset = 1.3f;
		batch.draw(
				imageLoader.arrowShadowRight,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getX() - offset,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getWidth(),
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getHeight()
				);
		batch.draw(
				imageLoader.arrowShadowLeft,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getX() + offset,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getWidth(),
				-ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getHeight()
				);
		batch.draw(
				imageLoader.arrowShadowUp,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getX() - offset,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getWidth(),
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getHeight()
				);
	}

}
