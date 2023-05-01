package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.swing.table.TableRowSorter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 *
 * @author Yair and Noam
 */
class TriangleTest
{

    /**
     * Test method for the {@link Triangle#Triangle(Point, Point, Point)} constructor.
     * Tests for the boundary values and equivalence partitions of the constructor.
     */
    @Test
    public void TestConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct Triangle
        try {
            new Triangle(new Point(0, 0, 0), new Point(1, 1, 1), new Point(1, 0, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct triangle" + e.getMessage());
        }

        // =============== Boundary Values Tests ==================

        // TC10: triangle with 3 dots on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)),
                "Can't create a triangle with 3 dots on the same line");

        // TC11: plane with 2 dots collides
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(0, 0, 0), new Point(0, 0, 0), new Point(2, 2, 2)),
                "Can't create a triangle with 2 dots collides");
    }


    /**
     * This method tests the calculation of the normal vector of a triangle with a given point.
     *
     * Equivalence Partitions:
     * TC01: There is a simple single test here - using param
     *
     * @see Triangle#getNormal(Point)
     */
    @Test
    public void testGetNormal() {
        // Create a new triangle with three points
        Triangle t = new Triangle(new Point(1, 2, 3), new Point(1, 1, 1), new Point(-1, 2, 0));

        // Test the normal vector calculation with a given point
        assertEquals(new Vector(3, 4, -2).normalize(), t.getNormal(new Point(1, 1, 1)),
                "Triangle's normal is not right - using param");
    }

    @Test
    public void testFindIntersections()
    {
        Triangle triangle = new Triangle(new Point(1, 0, 1), new Point(0, 1, 1), new Point(-1, 0, 1));
        Point a = new Point(0, 0, -1);
        Point b = new Point(2, 0, 1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Point in the triangle
        List<Point> result01 = triangle.findIntersections(new Ray(a,
                new Vector(0, 0.5, 2)));
        assertEquals(1, result01.size(), "Wrong number of points 01");

        assertEquals(List.of(new Point(0, 0.5, 1)), result01, "Ray crosses triangle 01");

        // TC02: Point doesn't in the triangle
        assertNull(triangle.findIntersections(new Ray(a,
                        new Vector(1, 1, 2))),
                "Ray's line doesn't intersect triangle 02");

        // TC03: Point in front of the triangle vertex
        assertNull(triangle.findIntersections(new Ray(a,
                        new Vector(0, 2, 2))),
                "Ray's line doesn't intersect triangle 03");

        // =============== Boundary Values Tests ==================

        // TC11: Point in the triangle edge
        assertNull(triangle.findIntersections(new Ray(a,
                        new Vector(0.5, 0, 2))),
                "Ray's line doesn't intersect triangle 11");

        // TC12: Point in the triangle vertex
        assertNull(triangle.findIntersections(new Ray(a,
                        new Vector(1, 0, 2))),
                "Ray's line doesn't intersect triangle 12");

        // TC13: Point in the continuation of triangle edge
        assertNull(triangle.findIntersections(new Ray(a,
                        new Vector(2, 0, 2))),
                "Ray's line doesn't intersect triangle 13");
    }

}