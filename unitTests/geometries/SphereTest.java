package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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
    void getNormal() {
        Sphere s = new Sphere(5, new Point(0, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        /**
         * TC01: Test the normal of a point on the surface of the sphere.
         */
        assertEquals(new Vector(1, 0, 0), s.getNormal(new Point(5, 0, 0)),
                "Sphere's normal is not right");
    }

}