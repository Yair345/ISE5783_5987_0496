package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of intersectable geometries.
 * This class implements the Intersectable interface, allowing it to be intersected by a Ray object.
 */
public class Geometries implements Intersectable
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
	 * Computes the intersections between the given ray and the geometries in the collection.
	 *
	 * @param ray the ray to intersect with the geometries.
	 * @return a list of intersection points between the ray and the geometries, or null if there are no intersections.
	 */
	@Override
	public List<Point> findIntersections(Ray ray)
	{
		List<Point> result = null;
		for (var element : elements)
		{
			List<Point> elemPoints = element.findIntersections(ray);
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
	}
	
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
