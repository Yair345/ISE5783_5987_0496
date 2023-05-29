package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents an ambient light source, which provides a uniform level of illumination
 * to all objects in a scene.
 */
public class AmbientLight extends Light
{
	/**
	 * Represents the absence of ambient light.
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	/**
	 * Constructs an AmbientLight object with the specified intensity and reflection coefficients.
	 *
	 * @param Ia the intensity of the ambient light
	 * @param Ka the reflection coefficient for ambient light
	 */
	public AmbientLight(Color Ia, Double3 Ka)
	{
		super(Ia.scale(Ka));
	}
	
	/**
	 * Constructs an AmbientLight object with the specified intensity and reflection coefficient.
	 *
	 * @param Ia the intensity of the ambient light
	 * @param Ka the reflection coefficient for ambient light
	 */
	public AmbientLight(Color Ia, double Ka)
	{
		super(Ia.scale(Ka));
	}
}
