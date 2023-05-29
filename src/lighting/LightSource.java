package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface represents a light source in a scene that provides methods for calculating
 * the intensity and direction of light at a given point.
 */
public interface LightSource
{
    /**
     * Returns the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the light
     */
    public Color getIntensity(Point p);
    
    /**
     * Returns the direction of the light at a given point.
     *
     * @param p the point at which the direction is evaluated
     * @return the direction of the light
     */
    public Vector getL(Point p);
}
