package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import static java.lang.Math.sqrt;

/**
 * The Sphere class represents a sphere in 3D space.
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
    public List<Point> findIntersections(Ray ray)
    {
        Vector u;
        double tm, th;

        try
        {
            u = center.subtract(ray.getP0());

            tm = u.dotProduct(ray.getDir());

            double d = u.lengthSquared() - tm * tm;

            if (d >= radius * radius) // there are no intersections
            {
                return null;
            }

            th = sqrt(radius * radius - d);
        }
        catch(IllegalArgumentException e)
        {
            tm = 0;
            th = radius;
        }

        double t1 = tm + th;
        double t2 = tm - th;

        if(t1 <= 0 && t2 <= 0)
        {
            return null;
        }

        List<Point> intersections = new LinkedList<>();

        if (t1 > 0)
        {
            Vector temp = ray.getDir().scale(t1);
            intersections.add(ray.getP0().add(temp));
        }

        if (t2 > 0)
        {
            Vector temp = ray.getDir().scale(t2);
            intersections.add(ray.getP0().add(temp));
        }

        return intersections;
    }
}

