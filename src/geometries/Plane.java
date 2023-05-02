package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in 3D space.
 */
public class Plane implements Geometry
{
    /**
     * The point on the plane.
     */
    private Point q0;
    
    /**
     * The normal vector of the plane.
     */
    private Vector normal;
    
    /**
     * Constructs a new Plane object from three non-collinear points.
     *
     * @param p1 the first point on the plane.
     * @param p2 the second point on the plane.
     * @param p3 the third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3)
    {
        this.q0 = p1;
        Vector v1 = null, v2 = null;

        try
        {
            v1 = p1.subtract(p2);
            v2 = p1.subtract(p3);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Two dots are collides");
        }

        try
        {
            this.normal = v1.crossProduct(v2).normalize();
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The points are on the same line");
        }
    }
    
    /**
     * Constructs a new Plane object from a point and a normal vector.
     *
     * @param q0 the point on the plane.
     * @param normal the normal vector of the plane.
     */
    public Plane(Point q0, Vector normal)
    {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
    
    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane.
     */
    public Vector getNormal()
    {
        return normal;
    }
    
    /**
     * Returns the normal vector of the plane at the specified point.
     *
     * @param p the point at which to compute the normal vector.
     * @return the normal vector of the plane at the specified point.
     */
    @Override
    public Vector getNormal(Point p)
    {
        return normal;
    }
    
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        double temp, t;
        Vector vec;
        try
        {
            vec = q0.subtract(ray.getP0());
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
        temp = vec.dotProduct(normal);
        t = normal.dotProduct(ray.getDir());

        if (t == 0) // they are parallel
        {
            return null;
        }
        t = temp / t;

        if (t <= 0) //direction is opposite
        {
            return null;
        }

        Vector p = ray.getDir().scale(t);
        List<Point> intersect = new LinkedList<>();
        intersect.add(ray.getP0().add(p));

        return intersect;
    }
}

