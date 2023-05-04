package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Triangle class represents a triangle polygon in 3D space.
 *
 * @author Yair and Noam
 */
public class Triangle extends Polygon
{
    /**
     * Constructs a new Triangle object with the specified vertices.
     *
     * @param p1 the first vertex of the triangle.
     * @param p2 the second vertex of the triangle.
     * @param p3 the third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3)
    {
        super(p1, p2, p3);
    }
    
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> intersections = plane.findIntersections(ray); // first find the suspect point
        
        // if it intersects the plane
        if (intersections == null)
        {
            return null;
        }
        
        Point intersect = intersections.get(0);
        
        int ans = super.inTriangle(intersect, vertices.get(0), vertices.get(1), vertices.get(2));
        
        if (ans != 1)
        {
            return null;
        }
        
        return intersections;
    }
}

