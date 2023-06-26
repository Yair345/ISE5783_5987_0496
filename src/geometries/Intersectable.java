package geometries;

import primitives.Box;
import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * This abstract class represents an object in 3D space that can be intersected by a ray.
 *
 * @author Yair and Noam
 */
public abstract class Intersectable
{
	/**
	 * The bounding box of the intersectable object
	 */
	public Box box = new Box();
	
	/**
	 * Creates the bounding box for the intersectable object.
	 * This method must be implemented by the subclasses to define the specific bounding box.
	 */
	protected abstract void createBox();
	
	/**
	 * Finds the geometric intersections between the intersectable object and a ray in a bounding volume hierarchy (BVH).
	 *
	 * @param ray the ray to intersect with
	 * @return a list of GeoPoint objects representing the intersections, or null if no intersections occur
	 */
	public List<GeoPoint> findGeoIntersectionsBVH(Ray ray)
	{
		return box.checkIntersectionWithBox(ray) == null ? null : findGeoIntersections(ray);
	}
	
//	public List<GeoPoint> findGeoIntersectionsBVH(Ray ray , double maxDistance)
//	{
//		return box.checkIntersectionWithBox(ray) == null ? null : findGeoIntersections(ray , maxDistance);
//	}
	
	/**
	 * Finds the geometric intersections of a ray with the elements in the scene.
	 *
	 * @param ray the ray to intersect with the elements
	 * @param maxDistance the maximum distance for intersection
	 * @return a list of GeoPoint objects representing the intersections, or an empty list if no intersections were found
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance)
	{
		return findGeoIntersectionsHelper(ray, maxDistance);
	}
	
	/**
	 * Returns a list of intersection points between the object and a given ray.
	 *
	 * @param ray The ray to intersect with the object.
	 * @return A list of intersection points between the object and the ray.
	 */
	public List<Point> findIntersections(Ray ray)
	{
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}
	
	/**
	 * Finds the geometric intersections between a ray and the geometry.
	 *
	 * @param ray The ray to intersect with the geometry.
	 * @return A list of geometric intersection points between the ray and the geometry.
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
	
	/**
	 * Helper method for finding the geometric intersections between a ray and the geometry.
	 * Subclasses should override this method to provide the specific implementation.
	 *
	 * @param ray The ray to intersect with the geometry.
	 * @return A list of geometric intersection points between the ray and the geometry.
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance);
	
	/**
	 * The GeoPoint class represents a geometric intersection point between a ray and a geometry.
	 */
	public static class GeoPoint
	{
		public Geometry geometry;
		public Point point;
		
		/**
		 * Constructs a GeoPoint object with the specified geometry and point.
		 *
		 * @param geometry The geometry of the intersection.
		 * @param point    The intersection point.
		 */
		public GeoPoint(Geometry geometry, Point point)
		{
			this.geometry = geometry;
			this.point = point;
		}
		
		/**
		 * Checks if this GeoPoint is equal to another object.
		 *
		 * @param o The object to compare.
		 * @return true if the objects are equal, false otherwise.
		 */
		
		@Override
		public boolean equals(Object o)
		{
			if (this == o) {return true;}
			if (o == null || getClass() != o.getClass()) {return false;}
			GeoPoint geoPoint = (GeoPoint) o;
			return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
		}
		
		/**
		 * Computes the hash code of this GeoPoint.
		 *
		 * @return The hash code.
		 */
		@Override
		public int hashCode()
		{
			return Objects.hash(geometry, point);
		}
		
		/**
		 * Returns a string representation of this GeoPoint.
		 *
		 * @return The string representation.
		 */
		@Override
		public String toString()
		{
			return "GeoPoint{" +
					"geometry=" + geometry +
					", point=" + point +
					'}';
		}
	}
}
