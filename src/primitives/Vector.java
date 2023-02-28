package primitives;

/**
 * This class present Vector that starts from (0,0,0)
 *
 * @author Yair Lasri
 */
public class Vector extends Point
{
	
	/**
	 * Constructs a new Vector with the given coordinates (x,y,z).
	 *
	 * @param x the x-coordinate of the point.
	 * @param y the y-coordinate of the point.
	 * @param z the z-coordinate of the point.
	 *
	 * @throws IllegalArgumentException in case of ZERO vector.
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
	
	/**
	 * Calculates the length (magnitude) of this vector.
	 *
	 * @return the length (magnitude) of this vector.
	 */
	public double length()
	{
		return Math.sqrt(lengthSquared());
	}
	
	/**
	 * Calculates the squared length (magnitude) of this vector.
	 *
	 * @return the squared length (magnitude) of this vector.
	 */
	public double lengthSquared()
	{
		double x = xyz.d1;
		double y = xyz.d2;
		double z = xyz.d3;
		
		return x*x + y*y + z*z;
	}
	
	/**
	 * Returns a new vector that has the same direction as this vector, but with a length of 1.
	 *
	 * @return a new vector that has the same direction as this vector, but with a length of 1.
	 */
	public Vector normalize()
	{
		double len = length();
		
		double x = xyz.d1 / len;
		double y = xyz.d2 / len;
		double z = xyz.d3 / len;
		
		return new Vector(x, y, z);
	}
	
	/**
	 * Computes the dot product of this vector with the specified vector.
	 *
	 * @param other the vector to compute the dot product with.
	 *
	 * @return the dot product of this vector with the specified vector.
	 */
	public double dotProduct(Vector other)
	{
		double x = xyz.d1 * other.xyz.d1;
		double y = xyz.d2 * other.xyz.d2;
		double z = xyz.d3 * other.xyz.d3;
		
		return x + y + z;
	}
	
	/**
	 * Computes the cross product of this vector with the specified vector.
	 *
	 * @param other the vector to compute the cross product with.
	 *
	 * @return the cross product of this vector with the specified vector.
	 */
	public Vector crossProduct(Vector other)
	{
		double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
		double y = -1 * (xyz.d1 * other.xyz.d3 - xyz.d3 * other.xyz.d1);
		double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
		
		return new Vector(x, y, z);
	}
	
}
