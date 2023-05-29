package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source, which emits light uniformly in all
 * directions from a specific position.
 */
public class PointLight extends Light implements LightSource
{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;
    
    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity the intensity of the point light
     * @param position the position of the point light
     */
    public PointLight(Color intensity, Point position)
    {
        super(intensity);
        this.position = position;
    }
    
    /**
     * Sets the constant attenuation factor for the point light.
     *
     * @param kC the constant attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKc(double kC)
    {
        this.kC = kC;

        return this;
    }
    
    /**
     * Sets the linear attenuation factor for the point light.
     *
     * @param kL the linear attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKl(double kL)
    {
        this.kL = kL;

        return this;
    }
    
    /**
     * Sets the quadratic attenuation factor for the point light.
     *
     * @param kQ the quadratic attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKq(double kQ)
    {
        this.kQ = kQ;

        return this;
    }
    
    /**
     * Returns the intensity of the point light at a given point, taking into account the attenuation factors.
     *
     * @param p the point at which the intensity is evaluated
     * @return the intensity of the point light
     */
    @Override
    public Color getIntensity(Point p)
    {
        double d = p.distance(position);
        double temp = kC + (kL * d) + (kQ * d * d);

        return intensity.scale(1 / temp);
    }
    
    /**
     * Returns the direction from the point light to a given point.
     *
     * @param p the point at which the direction is evaluated
     * @return the direction from the point light to the given point
     */
    @Override
    public Vector getL(Point p)
    {
        try
        {
            return p.subtract(position).normalize();
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
}
