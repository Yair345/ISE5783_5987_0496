package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The SpotLight class represents a spotlight, which is a type of point light source that emits light
 * in a specific direction within a limited cone angle.
 */
public class SpotLight extends PointLight
{
    private Vector direction;
    
    /**
     * Constructs a SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity the intensity of the spotlight
     * @param position the position of the spotlight
     * @param direction the direction in which the spotlight emits light
     */
    public SpotLight(Color intensity, Point position, Vector direction)
    {
        super(intensity, position);
        this.direction = direction;
    }
    
    /**
     * Returns the intensity of the spotlight at a given point, taking into account the direction and cone angle.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the spotlight
     */
    @Override
    public Color getIntensity(Point p)
    {
        Color temp = super.getIntensity(p);
        Vector vec = super.getL(p);
        double t = vec.dotProduct(direction.normalize());

        return temp.scale(0 < t ? t : 0);
    }
    
    /**
     * Sets the narrow beam angle of the spotlight.
     *
     * @param i the narrow beam angle value
     * @return the updated SpotLight object
     */
    public SpotLight setNarrowBeam(int i)
    {
        return this;
    }
}
