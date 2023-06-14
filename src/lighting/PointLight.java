package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;

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
    
    private static Random random = new Random();
    
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
    
    /**
     * Calculates the distance between the shape and a given point.
     *
     * @param point the point to calculate the distance from
     * @return the distance between the shape and the point
     */
    @Override
    public double getDistance(Point point)
    {
        return alignZero(position.distance(point));
    }

    @Override
    public List<Point> generateBeamPoints(Point p0, Vector horizontal, Vector vertical, double radius, int numOfPoints)
    {
        List<Point> beamPoints = new LinkedList<>();
        double angleDelta = 360.0 / numOfPoints;

        for (int i = 0; i < numOfPoints; i++)
        {
            // Calculate the angle in radians
            double angle = Math.toRadians(angleDelta * i);
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);
            double newRadius = radius * random.nextDouble();
            double radiusCos = newRadius * cosAngle;
            double radiusSin = newRadius * sinAngle;

            // Calculate the position of the point on the circle
            double x = p0.getX() + radiusCos * horizontal.getX() + radiusSin * vertical.getX();
            double y = p0.getY() + radiusCos * horizontal.getY() + radiusSin * vertical.getY();
            double z = p0.getZ() + radiusCos * horizontal.getZ() + radiusSin * vertical.getZ();

            // Create the point and add it to the list
            beamPoints.add(new Point(x, y, z));
        }

        return beamPoints;
    }

//    @Override
//    public List<Vector> generateBeam(Point p, LightSource light)
//    {
//        List<Vector> vectors = new LinkedList();
//
//        double radius = light.getDistance(p) / 25;
//
//        //grid of vectors around the light
//        for (double i = -radius; i < radius; i += radius / 10)
//        {
//            for (double j = -radius; j < radius; j += radius / 10)
//            {
//                if (i != 0 && j != 0)
//                {
//                    //create a point on the grid
//                    Point point = position.add(new Vector(i, 0.1d, j));
//                    if (point.equals(position))
//                    {
//                        //if the point is the same as the light position,
//                        // add the vector from the point to the light
//                        vectors.add(p.subtract(point).normalize());
//                    }
//                    else
//                    {
//                        try
//                        {
//                            if (point.subtract(position).dotProduct(point.subtract(position)) <= radius * radius)
//                            {
//                                //if the point is in the radius of the light, add the vector from the point to the light
//                                vectors.add(p.subtract(point).normalize());
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            //if the point is not in the radius of the light, add the vector from the point to the light
//                            vectors.add(p.subtract(point).normalize());
//                        }
//
//                    }
//                }
//
//            }
//        }
//        vectors.add(getL(p));
//        return vectors;
//    }
}
