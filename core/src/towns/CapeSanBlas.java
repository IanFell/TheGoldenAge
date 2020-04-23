package towns;

import helpers.GameAttributeHelper;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CapeSanBlas extends Town {

	/**
	 * Constructor.
	 */
	public CapeSanBlas() {
		super();
		townBorder.x      = GameAttributeHelper.CHUNK_THREE_X_POSITION_START - 59;
		townBorder.y      = GameAttributeHelper.CHUNK_SIX_Y_POSITION_START - 75;
		townBorder.width  = 35;
		townBorder.height = 60;
	}
}
