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
		return null;
	}
}

