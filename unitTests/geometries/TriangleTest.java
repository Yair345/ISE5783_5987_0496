package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import javax.swing.table.TableRowSorter;

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

}