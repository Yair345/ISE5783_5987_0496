package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube
 *
 * @author Yair and Noam
 */
class TubeTests
{
	/**
	 * Tests the constructor of the Tube class.
	 * Creates a Tube object with valid parameters to ensure successful construction,
	 * and checks that IllegalArgumentException is thrown for invalid parameters.
	 * Specifically, it tests for negative radius and for an illegal vector.
	 * If any of the tests fail, the function will throw a failure message with the relevant error message.
	 */
	@Test
	public void testConstructor()
	{
		try
		{
			Tube tube = new Tube(5,
					new Ray(new Point(0,0,0), new Vector(1, 1, 1)));
		}
		catch (IllegalArgumentException e)
		{
			fail("Tube Failed constructing a correct polygon" + e.getMessage());
		}
		
		// Negative radius
		assertThrows(IllegalArgumentException.class,
				() -> new Tube(-5,
						new Ray(new Point(0,0,0), new Vector(1, 1, 1))),
				"Tube Negative radius");
		
		// Illegal vector
		assertThrows(IllegalArgumentException.class,
				() -> new Tube(5,
						new Ray(new Point(0,0,0), new Vector(0, 0, 0))),
				"Tube Illegal vector");
	}
	
	/**
	 * Tests the getNormal() function of the Tube class.
	 * Creates a Tube object with valid parameters and checks that the function returns the expected normal vector
	 * on the round surface of the tube.
	 * Additionally, tests for the case where p - p0 is orthogonal to v, as a boundary test.
	 * If any of the tests fail, the function will throw a failure message with the relevant error message.
	 */
	@Test
	void testGetNormal()
	{
		Tube tube = new Tube(3,
				new Ray(new Point(0,0,0), new Vector(0, 1, 0)));
		
		// ============ Equivalence Partitions Tests ==============
		// TC01: On the round surface
		assertEquals(new Vector(1,0,0),
				tube.getNormal(new Point(3, 4, 0)),
				"Tube round surface");
		
		
		// =============== Boundary Values Tests ==================
		// TC11: p - p0 orthogonal to v
		assertEquals(new Vector(1, 0, 0),
				tube.getNormal(new Point(3, 0, 0)),
				"Tube p - p0 orthogonal to v");
	}
}