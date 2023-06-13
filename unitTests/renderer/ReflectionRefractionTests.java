/**
 *
 */
package unittests.renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests
{
	private Scene scene = new Scene.SceneBuilder("Test scene").build();
	
	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres()
	{
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150)
				.setVPDistance(1000);
		
		scene.geometries.add( //
							  new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
									  .setMaterial(new Material().setKd(0.4)
														   .setKs(0.3)
														   .setShininess(100)
														   .setKt(0.3)),
							  new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
									  .setMaterial(new Material().setKd(0.5)
														   .setKs(0.5)
														   .setShininess(100)));
		scene.lights.add( //
						  new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
								  .setKl(0.0004)
								  .setKq(0.0000006));
		
		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors()
	{
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500)
				.setVPDistance(10000); //
		
		scene.ambientLight = new AmbientLight(new Color(255, 255, 255), 0.1);
		
		scene.geometries.add( //
							  new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
									  .setMaterial(new Material().setKd(0.25)
														   .setKs(0.25)
														   .setShininess(20)
														   .setKt(new Double3(0.5, 0, 0))),
							  new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
									  .setMaterial(new Material().setKd(0.25)
														   .setKs(0.25)
														   .setShininess(20)),
							  new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
										   new Point(670, 670, 3000)) //
									  .setEmission(new Color(20, 20, 20)) //
									  .setMaterial(new Material().setKr(1)),
							  new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
										   new Point(-1500, -1500, -2000)) //
									  .setEmission(new Color(20, 20, 20)) //
									  .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
		
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
								 .setKl(0.00001)
								 .setKq(0.000005));
		
		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	/** Produce a picture of a two triangles lighted by a spot light with a
	 * partially
	 * transparent Sphere producing partial shadow */
	@Test
	public void trianglesTransparentSphere()
	{
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200)
				.setVPDistance(1000);
		
		scene.ambientLight = new AmbientLight(new Color(WHITE), 0.15);
		
		scene.geometries.add( //
							  new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
										   new Point(75, 75, -150)) //
									  .setMaterial(new Material().setKd(0.5)
														   .setKs(0.5)
														   .setShininess(60)), //
							  new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
									  .setMaterial(new Material().setKd(0.5)
														   .setKs(0.5)
														   .setShininess(60)), //
							  new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
									  .setMaterial(new Material().setKd(0.2)
														   .setKs(0.2)
														   .setShininess(30)
														   .setKt(0.6)));
		
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
								 .setKl(4E-5)
								 .setKq(2E-7));
		
		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	@Test
	public void starOfDavid()
	{
		Camera camera = new Camera(
				new Point(50, 80, 10),
				new Vector(-50, -80, -30),
				new Vector(0, -0.375, 1))
				.setVPSize(200, 200)
				.setVPDistance(150);
		
		scene.ambientLight = new AmbientLight(new Color(BLUE), 0.15);
		
		Point[] v = {
				new Point(26.3, 0, -20),
				new Point(-17, -25, -20),
				new Point(-17, 25, -20),
				new Point(-3, 0, 20.5),
				new Point(12, -25, 8),
				new Point(12, 25, 8),
				new Point(-31.3, 0, 8),
				new Point(-3, 0, -32.5)
		};
		
		Material matStar = new Material()
				.setKd(0.2)
				.setKs(0.5)
				.setKt(0.4)
				//.setKd(0.5)
				.setShininess(90);
				//.setShininess(60);

		Material matGlass = new Material()
				.setKd(0.5)
				.setKs(0.5)
				.setKr(0.6)
				.setShininess(50);

		Material matSphere = new Material()
				.setKd(0.5)
				.setKs(0.5)
				.setKr(0.6)
				.setKt(0.8)
				.setShininess(50);

		scene.geometries.add(
				new Triangle(v[0], v[1], v[2])
						.setMaterial(matStar),
				new Triangle(v[0], v[1], v[3])
						.setMaterial(matStar),
				new Triangle(v[1], v[2], v[3])
						.setMaterial(matStar),
				new Triangle(v[0], v[2], v[3])
						.setMaterial(matStar),
				
				new Triangle(v[4], v[5], v[6])
						.setMaterial(matStar),
				new Triangle(v[4], v[5], v[7])
						.setMaterial(matStar),
				new Triangle(v[4], v[6], v[7])
						.setMaterial(matStar),
				new Triangle(v[5], v[6], v[7])
						.setMaterial(matStar),
				
				new Plane(
						new Point(0,0,-70),
						new Vector(0,0,1))
						.setMaterial(matGlass),
				new Plane(
						new Point(0,0,70),
						new Vector(0,0,-1))
						.setMaterial(matGlass),
				new Plane(new Point(-70,0,0),
						  new Vector(1, 0,0))
						.setMaterial(matGlass),
				new Plane(new Point(0, -70, 0),
						  new Vector(0,1,0))
						.setMaterial(matGlass),

				new Sphere(40,
						new Point(-3, 0, -10))
						.setMaterial(matSphere)
		);
		
		scene.lights.add(
				new SpotLight(
						new Color(255, 255, 255),
						new Point(70, 70, 70),
						new Vector(-40, 0, -100)
				)
				.setKl(4E-5)
				.setKq(2E-7)
		);
		
		ImageWriter imageWriter = new ImageWriter("starOfDavid", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
//	@Test
//	public void hungarianQube()
//	{
//		Camera camera = new Camera(
//				new Point(300, 0, 0),
//				new Vector(-1, 0, 0),
//				new Vector(0, 0, 1))
//				.setVPSize(200, 200)
//				.setVPDistance(100);
//
//		scene.ambientLight = new AmbientLight(new Color(WHITE), 0.15);
//
//		Material mat = new Material()
//				.setKd(0.5)
//				.setKs(0.5);
//				//.setKr(0.1)
//				//.setShininess(30);
//
//
//		Point[] v = {
//				new Point(45,45,45),
//				new Point(45,45,-45),
//				new Point(45,-45,45),
//				new Point(45,-45,-45),
//				new Point(-45,45,45),
//				new Point(-45,45,-45),
//				new Point(-45,-45,45),
//				new Point(-45,-45,-45),
//		};
//
//		Point[] edge = {
//				/**
//				 * edges
//				*/
//				/**
//				 * front
//				*/
//				// vertical front right
//				new Point(45, 44, 45),
//				new Point(45, 45, 45),
//				new Point(45, 45, -45),
//				new Point(45, 44, -45),
//
//				// vertical front left
//				new Point(45, -45, 45),
//				new Point(45, -44, 45),
//				new Point(45, -44, -45),
//				new Point(45, -45, -45),
//
//				// horizontal front top
//				new Point(45, -45, 45),
//				new Point(45, 45, 45),
//				new Point(45, 45, 44),
//				new Point(45, -45, 44),
//
//				// horizontal front bottom
//				new Point(45, -45, -44),
//				new Point(45, 45, -44),
//				new Point(45, 45, -45),
//				new Point(45, -45, -45),
//
//				/**
//				 * left
//				*/
//				// vertical left right
//				new Point(44, -45, 45),
//				new Point(45, -45, 45),
//				new Point(45, -45, -45),
//				new Point(44, -45, -45),
//
//				// vertical left left
//				new Point(-45, -45, 45),
//				new Point(-44, -45, 45),
//				new Point(-44, -45, -45),
//				new Point(-45, -45, -45),
//
//				// horizontal left top
//				new Point(-45, -45, 45),
//				new Point(45, -45, 45),
//				new Point(45, -45, 44),
//				new Point(-45, -45, 44),
//
//				// horizontal left bottom
//				new Point(-45, -45, -44),
//				new Point(45, -45, -44),
//				new Point(45, -45, -45),
//				new Point(-45, -45, -45),
//
//				/**
//				 * right
//				*/
//				// vertical right right
//				new Point(44, 45, 45),
//				new Point(45, 45, 45),
//				new Point(45, 45, -45),
//				new Point(44, 45, -45),
//
//				// vertical right left
//				new Point(-45, 45, 45),
//				new Point(-44, 45, 45),
//				new Point(-44, 45, -45),
//				new Point(-45, 45, -45),
//
//				// horizontal right top
//				new Point(-45,45, 45),
//				new Point(45, 45, 45),
//				new Point(45, 45, 44),
//				new Point(-45,45, 44),
//
//				// horizontal right bottom
//				new Point(-45, 45, -44),
//				new Point(45, 45, -44),
//				new Point(45, 45, -45),
//				new Point(-45, 45, -45),
//
//				/**
//				 * inside
//				*/
//				/**
//				 * front
//				*/
//				// vertical front right
//				new Point(45, 14, 45),
//				new Point(45, 16, 45),
//				new Point(45, 16, -45),
//				new Point(45, 14, -45),
//
//				// vertical front left
//				new Point(45, -16, 45),
//				new Point(45, -14, 45),
//				new Point(45, 14, -45),
//				new Point(45, -16, -45),
//
//				// horizontal front top
//				new Point(45, -45, 16),
//				new Point(45, 45, 16),
//				new Point(45, 45, 14),
//				new Point(45, -45, 14),
//
//				// horizontal front bottom
//				new Point(45, -45, -14),
//				new Point(45, 45, -14),
//				new Point(45, 45, -16),
//				new Point(45, -45, -16),
//
//
//				/**
//				 * left
//				*/
//				// vertical left right
//				new Point(14, -45, 45),
//				new Point(16, -45, 45),
//				new Point(16, -45, -45),
//				new Point(14, -45, -45),
//
//				// vertical left left
//				new Point(-16, -45, 45),
//				new Point(-14, -45, 45),
//				new Point(-14, -45, -45),
//				new Point(-16, -45, -45),
//
//				// horizontal left top
//				new Point(-45, -45, 16),
//				new Point(45, -45, 16),
//				new Point(45, -45, 14),
//				new Point(-45, -45, 14),
//
//				// horizontal left bottom
//				new Point(-45, -45, -14),
//				new Point(45, -45, -14),
//				new Point(45, -45, -16),
//				new Point(-45, -45, -16),
//
//
//				/**
//				 * right
//				*/
//				// vertical right right
//				new Point(14, 45, 45),
//				new Point(16, 45, 45),
//				new Point(16, 45, -45),
//				new Point(14, 45, -45),
//
//				// vertical right left
//				new Point(-16, 45, 45),
//				new Point(-14, 45, 45),
//				new Point(-14, 45, -45),
//				new Point(-16, 45, -45),
//
//				// horizontal right top
//				new Point(-45, 45, 16),
//				new Point(45, 45, 16),
//				new Point(45, 45, 14),
//				new Point(-45, 45, 14),
//
//				// horizontal right bottom
//				new Point(-45, 45, -14),
//				new Point(45, 45, -14),
//				new Point(45, 45, -16),
//				new Point(-45, 45, -16),
//
//		};
//
//		scene.geometries.add(
//				// white
//				// front
//				new Triangle(v[0], v[1], v[5])
//						.setMaterial(mat)
//						.setEmission(new Color(white)),
//				new Triangle(v[4], v[1], v[5])
//						.setMaterial(mat)
//						.setEmission(new Color(WHITE))
//
////				// left
////				new Triangle(v[2], v[3], v[7])
////						.setMaterial(mat)
////						.setEmission(new Color(GREEN)),
////				new Triangle(v[2], v[7], v[6])
////						.setMaterial(mat)
////						.setEmission(new Color(GREEN)),
////
////				// right
////				new Triangle(v[0], v[4], v[5])
////						.setMaterial(mat)
////						.setEmission(new Color(BLUE)),
////				new Triangle(v[2], v[5], v[1])
////						.setMaterial(mat)
////						.setEmission(new Color(BLUE)),
////
////				new Triangle(edge[0], edge[1], edge[2])
////						.setMaterial(mat)
////						.setEmission(Color.BLACK),
////				new Triangle(edge[0], edge[2], edge[3])
////						.setMaterial(mat)
////						.setEmission(Color.BLACK)
//
//		);
//
////		for (int i = 0; i < edge.length; i += 4)
////		{
////			scene.geometries.add(
////					new Triangle(edge[i], edge[i + 1], edge[i + 2])
////							.setEmission(Color.BLACK)
////							.setMaterial(mat),
////					new Triangle(edge[i], edge[i + 2], edge[i + 3])
////							.setEmission(new Color(BLACK))
////							.setMaterial(mat)
////					);
////		}
//
//		scene.lights.add(
////				new DirectionalLight(new Color(black), new Vector(-1,-1,-1))
//				new PointLight(new Color(BLUE), new Point(100, 50, 0))
//						.setKq(2E-7)
//						.setKl(4E-5)
//						);
//
//		ImageWriter imageWriter = new ImageWriter("hungarianQube", 600, 600);
//		camera.setImageWriter(imageWriter) //
//				.setRayTracer(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
//	}
}
