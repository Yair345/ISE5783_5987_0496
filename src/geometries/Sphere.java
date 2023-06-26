package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;

/**
 * The Sphere class represents a sphere in 3D space.
 *
 * @author Yair and Noam
 */
public class Sphere extends RadialGeometry
{
    /**
     * The center point of the sphere.
     */
    private Point center;

    /**
     * Constructs a new Sphere object with the specified radius.
     *
     * @param radius the radius of the Sphere object.
     */
    public Sphere(double radius, Point _center)
    {
        super(radius);
        center = _center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */
    public Point getCenter()
    {
        return center;
    }

    /**
     * Returns the normal vector to the sphere surface at the specified point.
     *
     * @param p the point at which to compute the normal vector.
     * @return the normal vector to the sphere surface at the specified point.
     */
    @Override
    public Vector getNormal(Point p)
    {
        Vector temp = p.subtract(center);

        return temp.normalize();
    }
    
    @Override
    public void createBox()
    {
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();
        
        box.minX = x - radius;
        box.maxX = x + radius;
        box.minY = y - radius;
        box.maxY = y + radius;
        box.minZ = z - radius;
        box.maxZ = z + radius;
    }
    
    /**
     * Helper method to find the geometric intersections of a ray with the sphere.
     *
     * @param ray the ray to intersect with the sphere
     * @param maxDistance the maximum distance for intersection
     * @return a list of GeoPoint objects representing the intersections, or null if no intersection was found
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        Vector u;
        double tm, th;

        try
        {
            u = center.subtract(ray.getP0()); // if the ray start at the center we go to catch

            tm = alignZero(u.dotProduct(ray.getDir()));

            double d = alignZero(u.lengthSquared() - tm * tm); // we will use sqrt only once in this function

            if (d >= radius * radius) // there are no intersections
            {
                return null;
            }

            th = alignZero(sqrt(radius * radius - d));
        }
        catch(IllegalArgumentException e)
        {
            tm = 0;
            th = radius;
        }

        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        // we take only the positive values because the direction of ray
        if(t1 <= 0 && t2 <= 0)
        {
            return null;
        }
        
        if(t1 > 0 && t2 > 0
                && alignZero(t1 - maxDistance) <= 0
                && alignZero(t2 - maxDistance) <= 0)
        {
            return List.of(
                    new GeoPoint(this, ray.getPoint(t1)),
                    new GeoPoint(this, ray.getPoint(t2)));
        }

        // might be only 1 intersection
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0)
        {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }
        
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0)
        {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }
        
        return null;
    }
}

