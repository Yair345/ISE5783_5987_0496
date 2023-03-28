package geometries;

import primitives.Point;
import primitives.Vector;

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
    public  Plane(Point p1, Point p2, Point p3)
    {
        this.q0 = p1;
        
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        
        if (!isZero(v1.dotProduct(v2))) // todo: check if the vectors are on the same line
            throw new IllegalArgumentException("The points are on the same line");
    
        this.normal = v1.crossProduct(v2).normalize();
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
}

