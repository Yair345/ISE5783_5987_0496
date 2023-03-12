package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Cylinder class extends the Tube class and represents a cylinder in 3D space.
 */
public class Cylinder extends Tube
{
	/**
	 * The height of the cylinder.
	 */
	private double height;
	
	/**
	 * Constructs a new Cylinder object with the specified radius, axis ray, and height.
	 *
	 * @param radius the radius of the cylinder.
	 * @param axisRay the axis ray of the cylinder.
	 * @param height the height of the cylinder.
	 */
	public Cylinder(double radius, Ray axisRay, double height)
	{
		super(radius, axisRay);
		this.height = height;
	}
	
	/**
	 * Returns the height of the cylinder.
	 *
	 * @return the height of the cylinder.
	 */
	public double getHeight()
	{
		return height;
	}
	
	/**
	 * Returns the normal vector of the cylinder at the specified point.
	 * In this implementation, always returns null.
	 *
	 * @param p the point at which to compute the normal vector.
	 * @return the normal vector of the cylinder at the specified point,
	 *         which is always null in this implementation.
	 */
	@Override
	public Vector getNormal(Point p)
	{
		return null;
	}
}
