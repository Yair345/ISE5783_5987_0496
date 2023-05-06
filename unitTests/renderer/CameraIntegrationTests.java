package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The CameraIntegrationTests class represents the unit tests for the sumOfIntersections method of the Camera class
 * and the findIntersections method of the Intersectable objects - Sphere, Plane, and Triangle.
 * It includes several test cases to verify the correctness of the methods.
 *
 * @author Yair and Noam
 */
public class CameraIntegrationTests
{
	/**
	 *
	 * The sumOfIntersections method calculates the sum of the number of intersections between a camera ray and an Intersectable object.
	 * It constructs rays for each pixel in the view plane of the camera and checks the number of intersections between each ray and the Intersectable object.
	 *
	 * @param camera - the camera that constructs the rays
	 * @param intersectable - the object to find intersections with
	 *
	 * @return the sum of the number of intersections for each ray
	 */
	private int sumOfIntersections(Camera camera, Intersectable intersectable)
	{
		int nx = 3, ny = 3, counter = 0;
		
		for (int i = 0; i < nx; i++)
		{
			for (int j = 0; j < ny; j++)
			{
				Ray ray = camera.constructRay(nx, ny, j, i);
				var intersections = intersectable.findIntersections(ray);
				if (intersections != null)
				{counter += intersections.size();}
			}
		}
		
		return counter;
	}
	
	/**
	 * The testSphereIntegration method tests the sumOfIntersections method for Sphere objects.
	 * It includes several test cases to verify the correctness of the method.
	 */
	@Test
	public void testSphereIntegration()
	{
		// TC01: 2 intersections
		Camera c1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		Sphere s1 = new Sphere(1, new Point(0, 0, -3));
		assertEquals(2, sumOfIntersections(c1, s1), "wrong number of intersections sphere 01");
		
		// TC02: 18 intersections
		Camera c2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		Sphere s2 = new Sphere(2.5, new Point(0, 0, -2.5));
		assertEquals(18, sumOfIntersections(c2, s2), "wrong number of intersections sphere 02");
		
		// TC03: 10 intersections
		Camera c3 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		Sphere s3 = new Sphere(2, new Point(0, 0, -2));
		assertEquals(10, sumOfIntersections(c3, s3), "wrong number of intersections sphere 03");
		
		// TC04: 9 intersections
		Camera c4 = new Camera(new Point(0, 0, -3), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		Sphere s4 = new Sphere(4, new Point(0, 0, -4));
		assertEquals(9, sumOfIntersections(c4, s4), "wrong number of intersections sphere 04");
		
		// TC05: no intersections
		Camera c5 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		Sphere s5 = new Sphere(0.5, new Point(0, 0, 1));
		assertEquals(0, sumOfIntersections(c5, s5), "wrong number of intersections sphere 05");
	}
	
	/**
	 * Tests the sum of intersections between a given camera and plane objects.
	 */
	@Test
	public void testPlaneIntegration()
	{
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		
		// TC01: 9 intersections
		Plane p1 = new Plane(new Point(0,0,-3), new Vector(0,0,1));
		assertEquals(9, sumOfIntersections(camera, p1), "wrong number of intersections plane 01");
		
		// TC02: 9 intersections
		Plane p2 = new Plane(new Point(0,0,-5), new Vector(0,1,2));
		assertEquals(9, sumOfIntersections(camera, p2), "wrong number of intersections plane 02");
		
		// TC03: 6 intersections
		Plane p3 = new Plane(new Point(0,0,-5), new Vector(0,1,1));
		assertEquals(6, sumOfIntersections(camera, p3), "wrong number of intersections plane 03");
		
		// TC04: no intersections
		Plane p4 = new Plane(new Point(0,0,5), new Vector(0,0,1));
		assertEquals(0, sumOfIntersections(camera, p3), "wrong number of intersections plane 04");
	}
	
	/**
	 * Tests the sum of intersections between a given camera and triangle objects.
	 */
	@Test
	public void testTriangleIntegration()
	{
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).
				setVPDistance(1).setVPSize(9, 9);
		
		// TC01: 1 intersections
		Triangle t1 = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
		assertEquals(1, sumOfIntersections(camera, t1), "wrong number of intersections triangle 01");
		
		// TC01: 2 intersections
		Triangle t2 = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
		assertEquals(2, sumOfIntersections(camera, t2), "wrong number of intersections triangle 02");
	}
}
