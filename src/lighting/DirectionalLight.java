package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source, which emits light in a specific direction
 * from an infinite distance away.
 */
public class DirectionalLight extends Light implements LightSource
{
    private Vector direction;
    
    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the intensity of the directional light
     * @param direction the direction in which the light is emitted
     */
    public DirectionalLight(Color intensity, Vector direction)
    {
        super(intensity);
        this.direction = direction;
    }
    
    /**
     * Returns the intensity of the directional light at a given point.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the directional light
     */
    @Override
    public Color getIntensity(Point p)
    {
        return intensity;
    }
    
    /**
     * Returns the direction of the directional light at a given point.
     *
     * @param p the point at which the direction is evaluated
     * @return the direction of the directional light
     */
    @Override
    public Vector getL(Point p)
    {
        return direction.normalize();
    }
}
