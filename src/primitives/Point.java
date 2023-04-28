package primitives;

import java.util.Objects;

import static primitives.Double3.ZERO;

/**
 * This class present a point
 *
 * @author Yair Lasri
 */
public class Point
{
	final Double3 xyz;
	
	/**
	 * Constructs a new Point with the given coordinates (x,y,z).
	 *
	 * @param x the x-coordinate of the point.
	 * @param y the y-coordinate of the point.
	 * @param z the z-coordinate of the point.
	 */
	public Point(double x, double y, double z)
	{
		this(new Double3(x, y, z));
	}
	
	/**
	 * Secondary Constructor
	 * Constructs a new Point with the given Double3 object.
	 *
	 * @param double3 the Double3 object containing the x,y,z coordinates of the point.
	 */
	Point(Double3 double3)
	{
		xyz = double3;
	}
	
	/**
	 * Returns true if the specified object is equal to this Point.
	 * Two Points are considered equal if they have the same coordinates.
	 *
	 * @param o the object to be compared for equality with this Point.
	 * @return true if the specified object is equal to this Point, false otherwise.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Point point)) return false;
		return xyz.equals(point.xyz);
	}
	
	/**
	 * Returns a hash code for this Point.
	 *
	 * @return a hash code value for this Point.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(xyz);
	}
	
	/**
	 * Returns a string representation of this Point.
	 *
	 * @return a string representation of this Point.
	 */
	@Override
	public String toString()
	{
		return "Point: " + xyz;
	}
	
	/**
	 * Returns the square of the distance between this Point and the specified Point.
	 *
	 * @param other the other Point to compute the distance to.
	 * @return the square of the distance between this Point and the specified Point.
	 */
	public double distanceSquared(Point other)
	{
		double dx = other.xyz.d1 - xyz.d1;
		double dy = other.xyz.d2 - xyz.d2;
		double dz = other.xyz.d3 - xyz.d3;
		
		return dx*dx + dy*dy + dz*dz;
	}
	
	/**
	 * Returns the distance between this Point and the specified Point.
	 *
	 * @param other the other Point to compute the distance to.
	 * @return the distance between this Point and the specified Point.
	 */
	public double distance(Point other)
	{
		return Math.sqrt(distanceSquared(other));
	}
	
	/**
	 * Returns a new Point that is the result of adding the coordinates of this Point and the coordinates of the specified Vector.
	 *
	 * @param vector the Vector to add to this Point.
	 * @return a new Point that is the result of adding the coordinates of this Point and the coordinates of the specified Vector.
	 */
	public Point add(Vector vector)
	{
		return new Point(xyz.add(vector.xyz));
	}
	
	/**
	 * Returns a new Vector that is the result of subtracting the coordinates of the specified Point from the coordinates of this Point.
	 *
	 * @param point the Point to subtract from this Point.
	 * @return a new Vector that is the result of subtracting the coordinates of the specified Point from the coordinates of this Point.
	 */
	public Vector subtract(Point point)
	{
		return new Vector(xyz.subtract(point.xyz));
	}
	
	public double getX()
	{
		return xyz.d1;
	}
}
