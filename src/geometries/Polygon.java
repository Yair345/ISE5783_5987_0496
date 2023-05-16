package geometries;

import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon implements Geometry
{
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }
   
   /**
    * Returns the normal vector of the polygon at the specified point.
    * The normal vector is the same for every point on the polygon surface.
    * @param point The point on the polygon surface.
    *
    * @return The normal vector of the polygon at the specified point.
    */
   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }
   
   /**
    * Finds the intersections between the polygon and a given ray.
    * @param ray the ray to find intersections with
    *
    * @return a list of intersection points, or null if there are no intersections
    */
   @Override
   public List<Point> findIntersections(Ray ray)
   {
      List<Point> intersections = plane.findIntersections(ray); // first find the suspect point
      
      // if it intersects the plane
      if (intersections == null)
      {
         return null;
      }
      
      Point intersect = intersections.get(0); // for not using get function a couple of times
      int sideCounter = 0, ans;
      boolean found = false;
      
      for (int i = 1; i < vertices.size() - 1; i++)
      {
         ans = inTriangle(intersect, vertices.get(0), vertices.get(i), vertices.get(i + 1));
         
         if (ans == 1) // inside the triangle that contained in the polygon
         {
            found = true;
            break;
         }
         
         if (ans == 3) // vertex
         {
            return null;
         }
         
         if (ans == 2) // side of the triangle
         {
            sideCounter++;
            if (sideCounter == 2)
            {
               found = true;
               break;
            }
         }
      }
      
      if (!found)
      {
         return null;
      }
      
      return intersections;
   }
   
   /**
    Computes the intersection between a point and a triangle
    
    Using <a href="https://www.youtube.com/watch?v=HYAgJN3x4GA">this</a> method
    
    @param p The point to check intersection with
    @param a First point of the triangle
    @param b Second point of the triangle
    @param c Third point of the triangle
    
    @return 0 if no intersection, 1 if intersection inside the triangle, 2 if intersection with an edge,
    3 if intersection with a vertex
    */
   protected int inTriangle(Point p, Point a, Point b, Point c)
   {
      double w1, w2, temp; // w1 and w2 is a coefficients of vec1 and vec2
      double ax = a.getX(), ay = a.getY(), bx = b.getX(), by = b.getY(), cx = c.getX(), cy = c.getY(), px = p.getX(), py = p.getY();
      
      /*
       Development of the equation: p = a + w1 * (c - a) + w2 * (b - a)
       the equation separate into 2 equation: one for x coordination and another one for y coordination
       we also can replace the x or y coordination in z but at the end it will be the same
       */
      if (cy == ay) // edge case
      {
         temp = (cy - ay) * (bx - ax) - (cx - ax) * (by - ay);
         w1 = (ax * (by - ay) + (py - ay) * (bx - ax) - px * (by - ay)) / temp;
         w2 = (py - ay - w1 * (cy - ay)) / (by - ay);
      }
      else
      {
         temp = (by - ay) * (cx - ax) - (bx - ax) * (cy - ay);
         w1 = (ax * (cy - ay) + (py - ay) * (cx - ax) - px * (cy - ay)) / temp;
         w2 = (py - ay - w1 * (by - ay)) / (cy - ay);
      }
      
      if (w1 < 0 || w2 < 0 || w1 > 1 || w2 > 1 || w1 + w2 > 1)
      {
         return 0; // no intersection
      }

      if (w1 == 1 || w2 == 1 || (w1 == 0 && w2 == 0))
      {
         return 3; // intersect the vertex
      }

      if (w1 + w2 == 1 || w1 == 0 || w2 == 0)
      {
         return 2; // intersect the edge
      }
      
      // intersect inside the triangle
      return 1;
   }

}
