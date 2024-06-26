package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

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
    
    /**
     * Calculates the distance between the shape and a given point.
     *
     * @param point the point to calculate the distance from
     * @return the distance between the shape and the point
     */
    double getDistance(Point point);
    
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
    List<Point> generateBeamPoints(Point p0, Vector horizontal, Vector vertical, double radius, int numOfPoints);
}
