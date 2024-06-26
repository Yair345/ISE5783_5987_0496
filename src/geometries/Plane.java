package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Plane class represents a plane in 3D space.
 *
 * @author Yair and Noam
 */
public class Plane extends Geometry
{
	/**
	 * The point on the plane.
	 */
	private Point q0;
	
	/**
	 * The normal vector of the plane.
	 */
	private Vector normal;
	
	/**
	 * Constructs a new Plane object from three non-collinear points.
	 *
	 * @param p1 the first point on the plane.
	 * @param p2 the second point on the plane.
	 * @param p3 the third point on the plane.
	 */
	public Plane(Point p1, Point p2, Point p3)
	{
		this.q0 = p1;
		Vector v1 = null, v2 = null;
		
		try
		{
			v1 = p1.subtract(p2);
			v2 = p1.subtract(p3);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Two dots are collides");
		}
		
		try
		{
			this.normal = v1.crossProduct(v2).normalize();
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("The points are on the same line");
		}
	}
	
	/**
	 * Constructs a new Plane object from a point and a normal vector.
	 *
	 * @param q0     the point on the plane.
	 * @param normal the normal vector of the plane.
	 */
	public Plane(Point q0, Vector normal)
	{
		this.q0 = q0;
		this.normal = normal.normalize();
	}
	
	/**
	 * Returns the normal vector of the plane.
	 *
	 * @return the normal vector of the plane.
	 */
	public Vector getNormal()
	{
		return normal;
	}
	
	/**
	 * Returns the normal vector of the plane at the specified point.
	 *
	 * @param p the point at which to compute the normal vector.
	 * @return the normal vector of the plane at the specified point.
	 */
	@Override
	public Vector getNormal(Point p)
	{
		return normal;
	}
	
	/**
	 * Creates the bounding box for the intersectable object.
	 * This method must be implemented by the subclasses to define the specific bounding box.
	 */
	@Override
	protected void createBox()
	{
		box.minX = Double.NEGATIVE_INFINITY;
		box.minY = Double.NEGATIVE_INFINITY;
		box.minZ = Double.NEGATIVE_INFINITY;
		
		box.maxX = Double.POSITIVE_INFINITY;
		box.maxY = Double.POSITIVE_INFINITY;
		box.maxZ = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Helper method to find the geometric intersections of a ray with the plane.
	 *
	 * @param ray the ray to intersect with the plane
	 * @param maxDistance the maximum distance for intersection
	 * @return a list of GeoPoint objects representing the intersections, or null if no intersection was found
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		double temp, t;
		Vector vec;
		
		try
		{
			vec = q0.subtract(ray.getP0());
		}
		catch (IllegalArgumentException e)
		{ // start at the point that the plane defined
			return null;
		}
		
		// are they parallel?
		t = alignZero(normal.dotProduct(ray.getDir()));
		
		if (t == 0)
		{ // they are parallel
			return null;
		}
		
		temp = vec.dotProduct(normal);
		t = alignZero(temp / t);
		
		if (t > 0)
		{ // direction is opposite
			if (alignZero(t - maxDistance) < 0)
			{
				return List.of(new GeoPoint(this, ray.getPoint(t)));
			}
		}
		
		return null;
	}
}

