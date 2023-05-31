package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of intersectable geometries.
 * This class implements the Intersectable interface, allowing it to be intersected by a Ray object.
 *
 * @author Yair and Noam
 */
public class Geometries extends Intersectable
{
	
	/**
	 * A list of intersectable elements
	 */
	List<Intersectable> elements;
	
	/**
	 * Constructs an empty Geometries object.
	 */
	public Geometries()
	{
		elements = new LinkedList<Intersectable>();
	}
	
	/**
	 * Constructs a Geometries object with the given intersectable geometries.
	 *
	 * @param geometries the intersectable geometries to add to the collection.
	 */
	public Geometries(Intersectable... geometries)
	{
		elements = new LinkedList<Intersectable>();
		Collections.addAll(elements, geometries);
	}
	
	/**
	 * Adds the given intersectable geometries to the collection.
	 *
	 * @param geometries the intersectable geometries to add to the collection.
	 */
	public void add(Intersectable... geometries)
	{
		if (geometries == null)
		{
			throw new IllegalArgumentException("Add geometries is null");
		}
		elements = new LinkedList<Intersectable>();
		Collections.addAll(elements, geometries);
	}
	
	/**
	 * Helper method to find the geometric intersections of a ray with the elements in the scene.
	 *
	 * @param ray the ray to intersect with the elements
	 * @param maxDistance the maximum distance for intersection
	 * @return a list of GeoPoint objects representing the intersections, or null if no intersections were found
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		List<GeoPoint> result = null;
		for (var element : elements)
		{
			List<GeoPoint> elemPoints = element.findGeoIntersections(ray, maxDistance);
			if (elemPoints != null)
			{
				if (result == null)
				{
					result = new LinkedList<>();
				}
				result.addAll(elemPoints);
			}
		}
		return result;
		
		
		// for making Eliezer happy
//		List<Point> result = new LinkedList<>();
//
//		for (var element: elements)	{
//			List<Point> elemPoints = element.findIntersections(ray);
//			if(elemPoints != null){
//				result.addAll(elemPoints);
//			}
//		}
//
//		if(result.isEmpty()){
//			return null;
//		}
//		return result;
	}
}
