package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static primitives.Box.distance;

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
		for (Intersectable g: geometries)
		{
			g.createBox();
			this.createBox(g);
			elements.add(g);
		}
//		Collections.addAll(elements, geometries);
	}
	
	@Override
	public void createBox()
	{
		for(Intersectable l : elements)
		{
			l.createBox();
			this.createBox(l);
		}
	}
	
	/**
	 * Updates the bounding box of the current object by incorporating the bounding box of the provided Intersectable.
	 * The method compares the minimum and maximum coordinates of the two bounding boxes and updates the corresponding
	 * values in the current object's bounding box to ensure it encapsulates both objects completely.
	 *
	 * @param inter The Intersectable whose bounding box is to be included in the current object's bounding box.
	 */
	void createBox(Intersectable inter)
	{
		box.minX = inter.box.minX > box.minX ? box.minX : inter.box.minX;
		box.minY = inter.box.minY > box.minY ? box.minY : inter.box.minY;
		box.minZ = inter.box.minZ > box.minZ ? box.minZ : inter.box.minZ;
		
		box.maxX = inter.box.maxX < box.maxX ? box.maxX : inter.box.maxX;
		box.maxY = inter.box.maxY < box.maxY ? box.maxY : inter.box.maxY;
		box.maxZ = inter.box.maxZ < box.maxZ ? box.maxZ : inter.box.maxZ;
	}
	
	
	/**
	 * Builds the BVH (Bounding Volume Hierarchy) tree for efficient ray-object intersection tests.
	 * The BVH tree is constructed by iteratively merging the geometries in the scene.
	 * Note: This method assumes that the 'elements' list contains the geometries to be included in the BVH tree.
	 * After building the BVH tree, the 'elements' list will be modified to contain the root node of the BVH tree.
	 * The algorithm works as follows:
	 * Create a temporary list to hold the infinite geometries (e.g., Plane) separately, as they do not have a center.
	 * Remove the infinite geometries from the 'elements' list.
	 * Iterate until there are more than one element in the 'elements' list:
	 * Find the pair of geometries with the smallest distance between them.
	 * Create a Geometries object to represent the merged pair.
	 * Remove the two individual geometries from the 'elements' list.
	 * Add the merged Geometries object to the 'elements' list.
	 * Add the infinite geometries back to the 'elements' list.
	 * After the BVH tree is built, the 'elements' list will contain the root node of the BVH tree.
	 * The BVH tree improves the efficiency of ray-object intersection tests by organizing the geometries
	 * into a hierarchical structure, allowing for faster spatial querying and reducing the number of intersection tests.
	 */
	public void buildBvhTree()
	{
		//a plane list because they didn't have center...
		List<Intersectable> infiniteGeometries = new LinkedList<>();
		for (Intersectable geo : elements)
		{
			if (geo.box.checkInfinite())
				infiniteGeometries.add(geo);
		}
		
		elements.removeAll(infiniteGeometries);
		
		double distance = 0;
		Intersectable geo1 = null;
		Intersectable geo2 = null;
		
		while (elements.size() > 1)
		{
			double best = Double.POSITIVE_INFINITY;
			Intersectable geoI = elements.get(0);
			for (Intersectable geoJ : elements)
			{
				if (geoI != geoJ && (distance = distance(geoI, geoJ)) < best)
				{
					best = distance;
					geo1 = geoI;
					geo2 = geoJ;
				}
			}
			
			Geometries tempGeometries = new Geometries(geo1, geo2);
			elements.remove(geo1);
			elements.remove(geo2);
			elements.add(tempGeometries);
		}
		
		elements.addAll(infiniteGeometries);
	}
	
	
	/**
	 * Finds the geometric intersections between the given Ray and the objects contained in the BVH tree.
	 *
	 * @param ray The Ray to intersect with the objects.
	 * @return A list of GeoPoint objects representing the intersections between the Ray and the objects, or null if there are no intersections.
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsBVH(Ray ray)
	{
		List<GeoPoint> intersections = null;
		List<GeoPoint> tempIntersection = null;
		
		for (Intersectable geo : elements)
		{
			if (geo.box.checkIntersectionWithBox(ray))
			{
				tempIntersection = geo.findGeoIntersectionsBVH(ray);
			}
			
			if (tempIntersection != null)
			{
				if (intersections == null)
					intersections = new LinkedList<>();
				
				intersections.addAll(tempIntersection);
			}
		}
		
		return intersections;
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
