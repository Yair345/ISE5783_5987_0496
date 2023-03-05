package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
    private Point q0;
    private Vector normal;

    public  Plane(Point p1, Point p2, Point p3)
    {
        this.q0 = p1;
        this.normal = null;
    }

    public Plane(Point q0, Vector normal)
    {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    public Vector getNormal()
    {
        return normal;
    }

    @Override
    public Vector getNormal(Point p)
    {
        return normal;
    }
}
