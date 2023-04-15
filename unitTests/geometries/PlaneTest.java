package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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

}