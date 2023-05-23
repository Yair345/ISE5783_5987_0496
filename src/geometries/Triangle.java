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

    /**
     * Helper method for finding the geometric intersections between a ray and the triangle.
     * Overrides the method from the superclass Geometry.
     *
     * @param ray The ray to intersect with the triangle.
     * @return A list of geometric intersection points between the ray and the triangle.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray); // first find the suspect point
        
        // if it intersects the plane
        if (intersections == null)
        {
            return null;
        }
        
        GeoPoint intersect = intersections.get(0);
        
        int ans = super.inTriangle(intersect.point, vertices.get(0), vertices.get(1), vertices.get(2));
        
        if (ans != 1)
        {
            return null;
        }

        return List.of(new GeoPoint(this, intersect.point));
    }
}

