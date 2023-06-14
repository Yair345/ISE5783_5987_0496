package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

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
    
    /**
     * Returns the distance between the shape and a given point.
     * This implementation always returns positive infinity, indicating an infinite distance.
     *
     * @param point the point to calculate the distance from
     * @return the positive infinity distance
     */
    @Override
    public double getDistance(Point point)
    {
        return Double.POSITIVE_INFINITY;
    }
    
    /**
     * Generates a list of points forming a beam pattern centered at the given point.
     *
     * @param p0 The center point of the beam
     * @param horizontal The horizontal direction vector of the beam
     * @param vertical The vertical direction vector of the beam
     * @param radius The radius of the beam
     * @param numOfPoints The number of points to generate in the beam
     * @return A list of points forming the beam pattern
     */
    @Override
    public List<Point> generateBeamPoints(Point p0, Vector horizontal, Vector vertical, double radius, int numOfPoints)
    {
        return null;
    }

}
