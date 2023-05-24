package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class is a JUnit test class for the {@link Geometries#findIntersections(Ray)} method.
 *
 * @author Yair and Noam
 */
class GeometriesTests
{
	/**
	 * Test method for {@link Geometries#findIntersections(Ray)}.
	 */
	@Test
	void testFindIntersections()
	{
		Geometries g = new Geometries(new Sphere(2, new Point(0, 0, 0)),
				new Plane(
						new Point(0, 0, -3),
						new Point(1, 0, 3),
						new Point(1, 1, -3)),
				new Triangle(
						new Point(3, 0, 0),
						new Point(0, 3, 0),
						new Point(2, 2, 3)),
				new Polygon(
						new Point(-4, 0, -1),
						new Point(0, -4, 0),
						new Point(0, -4, 3),
						new Point(-4, 0, 2)));
		
		
		// ============ Equivalence Partitions Tests ==============
		// TC01: couple of geometries
		List<Point> result01 = g.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -2)));
		assertEquals(2, result01.size(), "couple of geometries 01");
		
		
		// =============== Boundary Values Tests ==================
		// TC11: No one intersect
		assertNull(g.findIntersections(new Ray(new Point(0, 0, 4), new Vector(1, 1, 1))), "No one intersect 11");
		
		// TC12: just one intersect
		List<Point> result12 = g.findIntersections(new Ray(new Point(0, 0, -4), new Vector(1, 1, 1)));
		assertEquals(1, result12.size(), "just one intersect 12");
		
		// TC13: every element intersect
		List<Point> result13 = g.findIntersections(new Ray(new Point(-5, -5, 3), new Vector(1, 1, -0.3)));
		assertEquals(5, result13.size(), "all of them intersect");
		
	}
}