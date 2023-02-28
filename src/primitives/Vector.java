package primitives;

public class Vector extends Point
{
	
	/**
	 * Constructs a new Vector with the given coordinates (x,y,z).
	 *
	 * @param x the x-coordinate of the point.
	 * @param y the y-coordinate of the point.
	 * @param z the z-coordinate of the point.
	 *
	 * @throws throw IllegalArgumentException in case of ZERO vector.
	 */
	public Vector(double x, double y, double z)
	{
		super(x, y, z);
		if (xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector cannot be Vector(0,0,0)");
		}
	}
	
	/**
	 * Secondary Constructor
	 * Constructs a new Vector with the given Double3 object.
	 *
	 * @param double3 the Double3 object containing the x,y,z coordinates of the point.
	 */
	Vector(Double3 double3)
	{
		super(double3);
		if (xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector cannot be Vector(0,0,0)");
		}
	}
	
	public double length()
	{
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared()
	{
		double x = xyz.d1;
		double y = xyz.d2;
		double z = xyz.d3;
		
		return x*x + y*y + z*z;
	}
	
	public Vector normalize()
	{
		double len = length();
		
		double x = xyz.d1 / len;
		double y = xyz.d2 / len;
		double z = xyz.d3 / len;
		
		return new Vector(x, y, z);
	}
}
