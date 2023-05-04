package primitives;

import java.util.Objects;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction.
 *
 * @author Yair Lasri and Noam Benisho
 */
public class Ray
{
	
	/**
	 * The starting point of the ray.
	 */
	private final Point p0;
	
	/**
	 * The normalized direction of the ray.
	 */
	private final Vector dir;
	
	/**
	 * Constructs a new Ray object with the given starting point and direction.
	 *
	 * @param p0  the starting point of the ray.
	 * @param dir the direction of the ray.
	 */
	public Ray(Point p0, Vector dir)
	{
		this.p0 = new Point(p0.xyz);
		Vector temp = new Vector(dir.xyz);
		this.dir = temp.normalize();
	}
	
	/**
	 * Returns the starting point of the ray.
	 *
	 * @return the starting point of the ray.
	 */
	public Point getP0()
	{
		return p0;
	}
	
	/**
	 * Returns the normalized direction of the ray.
	 *
	 * @return the normalized direction of the ray.
	 */
	public Vector getDir()
	{
		return dir;
	}
	
	/**
	 * Returns the point on the ray at a given distance from its origin.
	 *
	 * @param t the distance from the origin of the ray to the desired point
	 * @return the point on the ray at the specified distance from its origin
	 */
	public Point getPoint(double t)
	{
		Vector temp = dir.scale(t);
		return p0.add(temp);
	}
	
	/**
	 * Determines whether the specified object is equal to this Ray object.
	 *
	 * @param o the object to compare.
	 * @return true if the specified object is equal to this Ray object, false otherwise.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Ray ray))
		{
			return false;
		}
		return p0.equals(ray.p0) && dir.equals(ray.dir);
	}
	
	/**
	 * Computes the hash code for this Ray object.
	 *
	 * @return the hash code for this Ray object.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(p0, dir);
	}
	
	/**
	 * Returns a string representation of this Ray object.
	 *
	 * @return a string representation of this Ray object.
	 */
	@Override
	public String toString()
	{
		return "Ray: " +
				"p0 = " + p0 +
				", dir = " + dir;
	}
}
