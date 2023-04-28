package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometrical object in 3D space.
 */
public interface Geometry extends Intersectable
{
    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param p the point at which to compute the normal vector.
     * @return the normal vector of the geometry at the specified point.
     */
    public abstract Vector getNormal(Point p);
}
