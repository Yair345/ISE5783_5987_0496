package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction.
 *
 * @author Yair Lasri and Noam Benisho
 */
public class Ray
{
	private static final double DELTA = 0.1;
	
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
	 * Constructs a ray with a given head point, direction vector, and surface normal.
	 * Adjusts the head point to avoid self-intersections by adding or subtracting a small delta value along the normal direction.
	 *
	 * @param head the starting point of the ray
	 * @param dir  the direction vector of the ray
	 * @param n    the surface normal
	 */
	public Ray(Point head, Vector dir, Vector n)
	{
		double nl = alignZero(n.dotProduct(dir));
		this.p0 = isZero(nl) ?
				  head :
				  head.add(n.scale(nl < 0 ?
								   -DELTA :
								   DELTA));
		
		this.dir = dir.normalize();
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
	 * Finds the closest point from a list of points.
	 *
	 * @param points The list of points to search from.
	 * @return The closest point, or null if the list is null or empty.
	 */
	public Point findClosestPoint(List<Point> points)
	{
		return points == null || points.isEmpty() ?
			   null
												  :
			   findClosestGeoPoint(points.stream()
										   .map(p -> new GeoPoint(null, p))
										   .toList()).point;
	}
	
	/**
	 * Finds the closest GeoPoint in a given list of GeoPoints to a reference point.
	 *
	 * @param list The list of GeoPoints to search from.
	 * @return The closest GeoPoint to the reference point, or null if the list is empty.
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> list)
	{
		GeoPoint closestPoint;
		double minDistance, temp;
		
		// null list
		if (list.size() == 0)
		{
			return null;
		}
		
		closestPoint = list.get(0);
		minDistance = p0.distanceSquared(closestPoint.point);
		
		for (Intersectable.GeoPoint p : list)
		{
			temp = p0.distanceSquared(p.point);
			
			if (temp < minDistance)
			{
				minDistance = temp;
				closestPoint = p;
			}
		}
		
		return closestPoint;
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
