package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The `RayTest` class is responsible for testing the `findClosestPoint` method of the `Ray` class.
 */
class RayTest
{
    /**
     * The ray used for testing, with its origin at (0,0,0) and direction (1,0,0).
     */
    Ray ray = new Ray(
            new Point(0, 0, 0),
            new Vector(1, 0, 0));

    /**
     * The list of points used for testing.
     */
    List<Point> list = new LinkedList<Point>();

    /**
     * Point a used for testing.
     */
    Point a = new Point(2, 2, 2);

    /**
     * Point b used for testing.
     */
    Point b = new Point(1, 1, 0);

    /**
     * Point c used for testing.
     */
    Point c = new Point(3, 3, 3);

    /**
     * Point d used for testing.
     */
    Point d = new Point(0, 1, 0);

    /**
     * Test case for the `findClosestPoint` method.
     */
    @Test
    void findClosestPoint()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Closest point to the ray in the middle
        list.add(a);
        list.add(b);
        list.add(c);

        assertEquals(b, ray.findClosestPoint(list), "wrong point 01");

        // =============== Boundary Values Tests ==================
        // TC11: Closest point to the ray at the beginning
        list.remove(a);
        assertEquals(b, ray.findClosestPoint(list), "wrong point 11");

        // TC12: Closest point to the ray at the end
        list.add(d);
        assertEquals(d, ray.findClosestPoint(list), "wrong point 12");

        // TC13: Null list
        list.remove(b);
        list.remove(c);
        list.remove(d);
        assertNull(ray.findClosestPoint(list), "wrong point 13");
    }
}
