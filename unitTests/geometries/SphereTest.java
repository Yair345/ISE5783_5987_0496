package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Testing Sphere
 *
 * @author Yair and Noam
 */
class SphereTest
{

    @Test
    public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct Sphere with radius
        try {
            new Sphere(5, new Point(0, 0, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere with radius" + e.getMessage());
        }

        // =============== Boundary Values Tests ==================

        // TC10: Sphere with negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(-5, new Point(0, 0, 0)),
                "Can't create a sphere with negative radius");
    }


    /**
     * Tests the {@link Sphere#getNormal(Point)} method.
     */
    @Test
    void testGetNormal() {
        Sphere s = new Sphere(5, new Point(0, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        /**
         * TC01: Test the normal of a point on the surface of the sphere.
         */
        assertEquals(new Vector(1, 0, 0), s.getNormal(new Point(5, 0, 0)),
                "Sphere's normal is not right");
    }
    
    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */ @Test
    public void testFindIntersections()
    {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
        Point b = new Point(1, 0, 1);
        Point c = new Point(1, 0, -1);
        
        // ============ Equivalence Partitions Tests ==============
        
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(1, 1, 0))),
                "Ray's line out of sphere 01");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result02 = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result02.size(), "Wrong number of points 02");
        
        if (result02.get(0).getX() > result02.get(1).getX())
            result02 = List.of(result02.get(1), result02.get(0));
        assertEquals(List.of(p1, p2), result02, "Ray crosses sphere 02");

        // TC03: Ray starts inside the sphere (1 point)
        List<Point> result03 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(0.5, 0, 1)));
        assertEquals(1, result03.size(), "Wrong number of points 02");
        assertEquals(List.of(b), result03, "Ray crosses sphere 03");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2.5, 0, 0),
                        new Vector(1, 0, 0))),
                "Ray's line out of sphere 04");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        List<Point> result11 = sphere.findIntersections(new Ray(
                new Point(1, -1, 0), new Vector(0, 1, 1)));
        assertEquals(1, result11.size(), "Wrong number of points 11");
        assertEquals(List.of(b), result11, "Ray crosses sphere 11");
        
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(
                        new Point(1, -1, 0), new Vector(0, -1, -1))),
                "Ray's line out of sphere 12");
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        List<Point> result13 = sphere.findIntersections(new Ray(
                new Point(1, 0, 2), new Vector(0, 0, -3)));
        assertEquals(2, result13.size(), "Wrong number of points 13");
        assertEquals(List.of(b, c), result13, "Ray crosses sphere 13");
        
        // TC14: Ray starts at sphere and goes inside (1 point)
        List<Point> result14 = sphere.findIntersections(new Ray(
                b, new Vector(0, 0, -3)));
        assertEquals(1, result14.size(), "Wrong number of points 14");
        assertEquals(List.of(c), result14, "Ray crosses sphere 14");
        
        // TC15: Ray starts inside (1 point)
        List<Point> result15 = sphere.findIntersections(new Ray(
                new Point(1, 0, 0.5), new Vector(0, 0, -3)));
        assertEquals(1, result15.size(), "Wrong number of points 15");
        assertEquals(List.of(c), result15, "Ray crosses sphere 15");
        
        // TC16: Ray starts at the center (1 point)
        List<Point> result16 = sphere.findIntersections(new Ray(
                new Point(1, 0, 0), new Vector(0, 0, -3)));
        assertEquals(1, result16.size(), "Wrong number of points 16");
        assertEquals(List.of(c), result16, "Ray crosses sphere 16");
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(
                        b, new Vector(0, 0, 1))),
                "Ray's line out of sphere 17");
        
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(
                        new Point(1, 0, 2), new Vector(0, 0, 1))),
                "Ray's line out of sphere 18");
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(
                        new Point(0, 0, 1), new Vector(1, 0, 0))),
                "Ray's line out of sphere 19");
        
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(
                        b, new Vector(-1, 0, 0))),
                "Ray's line out of sphere 20");
        
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(
                        new Point(0, 0, 1), new Vector(-1, 0, 0))),
                "Ray's line out of sphere 21");
        
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(
                        new Point(1, 0, 2), new Vector(-1, 0, 0))),
                "Ray's line out of sphere 22");
    }
    
}