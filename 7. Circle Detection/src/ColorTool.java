
public class ColorTool {

	/**
	 * Private constructor, prevent from being instantiated.
	 */
	private ColorTool() {
	}
	
	/**
	 * Return the red value for a given RGB value
	 * @param color a single RGB value
	 * @return red value
	 */
	public static int getRed(int color) {
		return (color & 0xff0000) >> 16;
	}

	/**
	 * Return the green value for a given RGB value
	 * @param color a single RGB value
	 * @return green value
	 */
	public static int getGreen(int color) {
		return (color & 0xff00) >> 8;
	}

	/**
	 * Return the blue value for a given RGB value
	 * @param color a single RGB value
	 * @return blue value
	 */
	public static int getBlue(int color) {
		return color & 0xff;
	}
	
	/**
	 * Return a single RGB value for the given red, green, blue values
	 * @param red red value
	 * @param green green value
	 * @param blue blue value
	 * @return Return a single RGB value
	 */
	public static int getSingleRGB(int red, int green, int blue) {
		return (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
	}
	
}
