package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometrical object in 3D space.
 *
 * @author Yair and Noam
 */
public abstract class Geometry extends Intersectable
{
    /**
     * Base color of a shape
     */
    protected Color emission = Color.BLACK;

    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param p the point at which to compute the normal vector.
     * @return the normal vector of the geometry at the specified point.
     */
    public abstract Vector getNormal(Point p);

    /**
     * Returns the emission color of the geometry.
     *
     * @return The emission color.
     */
    public Color getEmission()
    {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to be set.
     * @return The geometry instance with the updated emission color.
     */
    public Geometry setEmission(Color emission)
    {
        this.emission = emission;

        return this;
    }
}
