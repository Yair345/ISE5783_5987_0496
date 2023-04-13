package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Tube class represents a tube in 3D space.
 */
public class Tube extends RadialGeometry
{
	/**
	 * The axis ray of the tube.
	 */
	protected Ray axisRay;
	
	/**
	 * Constructs a new Tube object with the specified radius and axis ray.
	 *
	 * @param radius the radius of the Tube object.
	 * @param axisRay the axis ray of the Tube object.
	 */
	public Tube(double radius, Ray axisRay)
	{
		super(radius);
		this.axisRay = new Ray(axisRay.getP0(), axisRay.getDir());
	}
	
	/**
	 * Returns the axis ray of the tube.
	 *
	 * @return the axis ray of the tube.
	 */
	public Ray getAxisRay()
	{
		return axisRay;
	}
	
	/**
	 * Returns the normal vector to the tube surface at the specified point.
	 *
	 * @param p the point at which to compute the normal vector.
	 * @return the normal vector to the tube surface at the specified point.
	 */
	@Override
	public Vector getNormal(Point p)
	{
		Point p0 = axisRay.getP0();
		Vector v0 = axisRay.getDir();
		
		Vector v = p.subtract(p0);
		double t = v.dotProduct(v0);
		
		// on the round surface
		Point o = axisRay.getP0();
		
		try
		{
			v0 = v0.scale(t);
			o = o.add(v0);
		}
		catch (IllegalArgumentException e) // in case of t == 0
		{ }
		
		Vector normal = p.subtract(o);
		
		return normal.normalize();
	}
}

