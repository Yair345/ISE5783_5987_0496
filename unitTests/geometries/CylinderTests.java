package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder
 *
 * @author Yair and Noam
 */
class CylinderTests
{
	
	/**
	 * Tests the constructor of the Cylinder class.
	 * Creates a Cylinder object with valid parameters to ensure successful construction,
	 * and checks that IllegalArgumentException is thrown for invalid parameters.
	 * Specifically, it tests for negative radius and height, and for an illegal vector.
	 * If any of the tests fail, the function will throw a failure message with the relevant error message.
	 */
	@Test
	public void testConstructor()
	{
		try
		{
			Cylinder c = new Cylinder(5,
					new Ray(new Point(0,0,0), new Vector(1, 1, 1)),
					3);
		}
		catch (IllegalArgumentException e)
		{
			fail("Cylinder Failed constructing a correct polygon" + e.getMessage());
		}
		
		// Negative radius
		assertThrows(IllegalArgumentException.class,
				() -> new Cylinder(-5,
						new Ray(new Point(0,0,0), new Vector(1, 1, 1)), 3),
				"Cylinder Negative radius");
		
		// Negative height
		assertThrows(IllegalArgumentException.class,
				() -> new Cylinder(5,
						new Ray(new Point(0,0,0), new Vector(1, 1, 1)), -3),
				"Cylinder Negative height");
		
		// Illegal vector
		assertThrows(IllegalArgumentException.class,
				() -> new Cylinder(5,
						new Ray(new Point(0,0,0), new Vector(0, 0, 0)), 3),
				"Cylinder Illegal vector");
	}
	
	/**
	 * Tests the getNormal function of the Cylinder class.
	 * Creates a Cylinder object with valid parameters and tests the function for various points.
	 * Equivalence partition tests are performed for a point on the round surface, bottom base, and top base.
	 * Boundary value tests are performed for the center of the bottom base and top base.
	 * If any of the tests fail, the function will throw a failure message with the relevant error message.
	 */
	@Test
	void testGetNormal()
	{
		Cylinder c = new Cylinder(3,
				new Ray(new Point(0,0,0), new Vector(0, 1, 0)),
				5);
		
		// ============ Equivalence Partitions Tests ==============
		// TC01: On the round surface
		assertEquals(new Vector(1,0,0),
				c.getNormal(new Point(3, 4, 0)),
				"Cylinder round surface");
		
		// TC02: Bottom base
		assertEquals(new Vector(0, -1, 0),
				c.getNormal(new Point(1, 0, 0)),
				"Cylinder bottom base");
		
		// TC03: Top base
		assertEquals(new Vector(0, 1, 0),
				c.getNormal(new Point(1, 5, 0)),
				"Cylinder top base");
		
		
		// =============== Boundary Values Tests ==================
		// TC11: Bottom base center
		assertEquals(new Vector(0, -1, 0),
				c.getNormal(new Point(0, 0, 0)),
				"Cylinder bottom base center");
		
		// TC12: Top base center
		assertEquals(new Vector(0, 1, 0),
				c.getNormal(new Point(0, 5, 0)),
				"Cylinder top base center");
	}
}