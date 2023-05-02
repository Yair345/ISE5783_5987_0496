package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 *
 * @author Yair and Noam
 */
class PlaneTest
{
    /**
     * Test method for the {@link Plane#Plane(Point, Point, Point)} and {@link Plane#Plane(Point, Vector)} constructors.
     * Tests for the boundary values and equivalence partitions of the constructors.
     */
    @Test
    public void testConstructor()
    {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct plane with 3 dots
        try
        {
            new Plane(new Point(1, 2, 3), new Point(1, 1, 1), new Point(-1, 2, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail("Failed constructing a correct plane with 3 dots" + e.getMessage());
        }

        // TC02: Correct plane with dot and vector
        try
        {
            new Plane(new Point(1, 2, 3), new Vector(1, 2, 3));
        }
        catch (IllegalArgumentException e)
        {
            fail("Failed constructing a correct plane with dot and vector" + e.getMessage());
        }

        // =============== Boundary Values Tests ==================
        // TC10: plane with 3 dots on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)),
                "Can't create a plane with 3 dots on the same line");

        // TC11: plane with 2 dots collides
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(2, 2, 2)),
                "Can't create a plane with 2 dots collides");
    }


    /**
     * Test method for the {@link Plane#getNormal()} and {@link Plane#getNormal(Point)} methods.
     * Tests for the boundary values and equivalence partitions of the methods.
     */
    @Test
    public void testGetNormal()
    {
        Plane p = new Plane(new Point(1, 2, 3), new Point(1, 1, 1), new Point(-1, 2, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here - no param
        assertEquals(new Vector(3, 4, -2).normalize(), p.getNormal(),
                "Plane's normal is not right - no param");

        // TC02: There is a simple single test here - using param
        assertEquals(new Vector(3, 4, -2).normalize(), p.getNormal(new Point(1, 1, 1)),
                "Plane's normal is not right - using param");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections()
    {
        Plane plane = new Plane(new Point(1, 0, 1), new Vector(0, 0, 1));
        Point a = new Point(0, 0, -1);
        Point b = new Point(2, 0, 1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's intersect the plane
        List<Point> result01 = plane.findIntersections(new Ray(a,
                new Vector(2, 0, 2)));
        assertEquals(1, result01.size(), "Wrong number of points 01");

        assertEquals(List.of(b), result01, "Ray crosses plane 01");

        // TC02: Ray's doesn't intersect the plane
        assertNull(plane.findIntersections(new Ray(a,
                        new Vector(0, 2, -1))),
                "Ray's line doesn't intersect plane 02");

        // =============== Boundary Values Tests ==================

        // TC11: Ray is parallel and contained in the plane
        assertNull(plane.findIntersections(new Ray(b,
                        new Vector(-1, 0, 0))),
                "Ray's line doesn't intersect plane 11");

        // TC12: Ray is parallel and doesn't contain in the plane
        assertNull(plane.findIntersections(new Ray(new Point(2, 0, 0),
                        new Vector(-1, 0, 0))),
                "Ray's line doesn't intersect plane 12");

        // TC13: Ray is orthogonal and start in the plane
        assertNull(plane.findIntersections(new Ray(b,
                        new Vector(0, 0, -1))),
                "Ray's line doesn't intersect plane 13");

        // TC14: Ray is orthogonal and start before the plane
        List<Point> result14 = plane.findIntersections(new Ray(new Point(2, 0 , 0),
                new Vector(0, 0, 1)));
        assertEquals(1, result14.size(), "Wrong number of points 14");

        assertEquals(List.of(b), result14, "Ray crosses plane 14");

        // TC15: Ray is orthogonal and start after the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,0, 0),
                        new Vector(0, 0, -1))),
                "Ray's line doesn't intersect plane 15");

        // TC16: Ray isn't orthogonal and start in the plane
        assertNull(plane.findIntersections(new Ray(b,
                        new Vector(-1, 0, -1))),
                "Ray's line doesn't intersect plane 16");

        // TC17: Ray isn't orthogonal and start in the point that define the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,0, 1),
                        new Vector(-1, 0, -2))),
                "Ray's line doesn't intersect plane 17");
    }
}