package renderer;

import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests
{
	private Intersectable sphere = new Sphere(60d, new Point(0, 0, -200))                                         //
			.setEmission(new Color(BLUE))                                                                                  //
			.setMaterial(new Material().setKd(0.5)
								 .setKs(0.5)
								 .setShininess(30));
	private Material trMaterial = new Material().setKd(0.5)
			.setKs(0.5)
			.setShininess(30);
	
	private Scene scene = new Scene.SceneBuilder("Test scene").build();
	private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
			.setVPSize(200, 200)
			.setVPDistance(1000)                                                                       //
			.setRayTracer(new RayTracerBasic(scene));
	
	/**
	 * Helper function for the tests in this module
	 */
	void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation)
	{
		scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE))
				.setMaterial(trMaterial));
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
								  .setKl(1E-5)
								  .setKq(1.5E-7));
		camera.setImageWriter(new ImageWriter(pictName, 400, 400)); //
		camera.renderImage(); //
		camera.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial()
	{
		sphereTriangleHelper("shadowSphereTriangleInitial", //
							 new Triangle(new Point(-40, -70, 0), new Point(-70, -40, 0), new Point(-68, -68, -4)), //
							 new Point(-100, -100, 200));
	}
	
	/**
	 * Sphere-Triangle shading - move triangle up-right
	 */
	
	@Test
	public void sphereTriangleMove1()
	{
		sphereTriangleHelper("shadowSphereTriangleMove2", //
							 new Triangle(new Point(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)), //
							 new Point(-100, -100, 200));
	}
	
	/**
	 * Sphere-Triangle shading - move triangle upper-righter
	 */
	
	@Test
	public void sphereTriangleMove2()
	{
		sphereTriangleHelper("shadowSphereTriangleMove1", //
							 new Triangle(new Point(-50, -20, 0), new Point(-20, -50, 0), new Point(-48, -48, -4)), //
							 new Point(-100, -100, 200));
	}
	
	/**
	 * Sphere-Triangle shading - move spot closer
	 */
	@Test
	public void sphereTriangleSpot1()
	{
		sphereTriangleHelper("shadowSphereTriangleSpot1", //
							 new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
							 new Point(-85, -85, 120));
	}
	
	/**
	 * Sphere-Triangle shading - move spot even more close
	 */
	@Test
	public void sphereTriangleSpot2()
	{
		sphereTriangleHelper("shadowSphereTriangleSpot2", //
							 new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
							 new Point(-80, -80, 75));
	}
	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere()
	{
		Scene scene2 = new Scene.SceneBuilder("Test scene2").setAmbientLight(new AmbientLight(new Color(WHITE), 0.15))
				.build();
		
		
		scene2.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
							 new Point(75, 75, -150)) //
						.setMaterial(new Material().setKs(0.8)
											 .setShininess(60)), //
				new Triangle(new Point(-150, -150, -115),
							 new Point(-70, 70, -140),
							 new Point(75, 75, -150)) //
						.setMaterial(new Material().setKs(0.8)
											 .setShininess(60)), //
				new Sphere(30d, new Point(0, 0, -11)) //
						.setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.5)
											 .setKs(0.5)
											 .setShininess(30)) //
							 );
		scene2.lights.add( //
						   new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
								   .setKl(4E-4)
								   .setKq(2E-5));
		
		Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
				.setVPSize(200, 200)
				.setVPDistance(1000)                                                                       //
				.setRayTracer(new RayTracerBasic(scene2));
		
		camera2.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)); //
		camera2.renderImage(); //
		camera2.writeToImage();
	}
	
	@Test
	public void triangleSphereDirectional()
	{
		Triangle triangle = new Triangle(new Point(-40, -70, 0), new Point(-70, -40, 0), new Point(-68, -68, -4));
		
		scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE))
				.setMaterial(trMaterial));
		scene.lights.add( //
						  new DirectionalLight(new Color(400, 240, 0), new Vector(1, 1, -3))); //
		camera.setImageWriter(new ImageWriter("triangleSphereDirectional", 400, 400)); //
		camera.renderImage(); //
		camera.writeToImage();
	}
	
//	@Test
//	public void checkBeam()
//	{
//		LightSource lightSource = new SpotLight(new Color(400, 240, 0), new Point(0,0,0), new Vector(1, 1, -3)) //
//				.setKl(1E-5)
//				.setKq(1.5E-7);
//
//		List<Point> beam = lightSource
//				.generateBeamPoints(new Point(0,0,0),
//									new Vector(1,0,0),
//									new Vector(0,1,0),
//									5.0,
//									100);
//
//		for (Point p: beam)
//		{
//			System.out.println(p);
//		}
//	}
	
}
