package parser;

import renderer.ImageWriter;

/**
 * The PseudoClasses class contains static variables for storing pseudo
 * classes used for JSON serialization and deserialization.
 *
 * @author Yair and Noam
 */
public class PseudoClasses
{
	public static JsonScene jsonScene;
	
	public static JsonImageWriter imageWriter;
}

/**
 * The JsonImageWriter class represents the JSON structure for an image writer.
 */
class JsonImageWriter
{
	private int nX;
	private int nY;
	private String name;
	
	/**
	 * Constructs a JsonImageWriter object with the specified dimensions and name.
	 *
	 * @param nX the width of the image
	 * @param nY the height of the image
	 * @param name the name of the image writer
	 */
	public JsonImageWriter(int nX, int nY, String name)
	{
		this.name = name;
		this.nX = nX;
		this.nY = nY;
	}
	
	/**
	 * Constructs a JsonImageWriter object from an existing ImageWriter object.
	 *
	 * @param imageWriter the ImageWriter object to convert
	 */
	public JsonImageWriter(ImageWriter imageWriter)
	{
		this(imageWriter.getNx(), imageWriter.getNy(), imageWriter.getImageName());
	}
	
	/**
	 * Getters
	 */
	public int getnX()
	{
		return nX;
	}
	
	public int getnY()
	{
		return nY;
	}
	
	public String getName()
	{
		return name;
	}
}

/**
 * The JsonScene class represents the JSON structure for a scene.
 */
class JsonScene
{
	PseudoScene scene;
}

/**
 * The PseudoScene class represents the pseudo structure for a scene.
 */
class PseudoScene
{
	String background;
	PseudoAmbientLight ambientLight;
	PseudoGeometryData geometryData;
}

/**
 * The PseudoAmbientLight class represents the pseudo structure for an ambient light.
 */
class PseudoAmbientLight
{
	String intensity;
}

/**
 * The PseudoGeometryData class represents the pseudo structure for geometry data.
 */
class PseudoGeometryData
{
	PseudoTriangle[] triangles;
	PseudoSphere[] spheres;
	PseudoPlane[] planes;
}

/**
 * The PseudoPlane class represents the pseudo structure for a plane.
 */
class PseudoPlane
{
	String normal;
	String p0;
}

/**
 * The PseudoTriangle class represents the pseudo structure for a triangle.
 */
class PseudoTriangle
{
	String p0;
	String p1;
	String p2;
}

/**
 * The PseudoSphere class represents the pseudo structure for a sphere.
 */
class PseudoSphere
{
	String center;
	String radius;
}