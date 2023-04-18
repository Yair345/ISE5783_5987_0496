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
	private final double height;
	
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
		if (height < 0)
			throw new IllegalArgumentException("Height must be bigger then 0");
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
		Point p0 = axisRay.getP0();
		Vector v0 = axisRay.getDir();
		
		Vector v;
		double t = 0;
		
		try
		{
			v = p.subtract(p0);
			t = v.dotProduct(v0);
		}
		catch (IllegalArgumentException e)
		{
			t = 0;
		}
		
		if (t == 0) // bottom base
		{
			return v0.scale(-1);
		}
		else if (t == height) // top base
		{
			return v0;
		}
		
		// on the round surface
		Point o = axisRay.getP0();
		
		v0 = v0.scale(t);
		
		o = o.add(v0); // desire point
		
		Vector normal = p.subtract(o);
		
		return normal.normalize();
	}
}
