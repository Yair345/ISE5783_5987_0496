package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * This interface represents an object in 3D space that can be intersected by a ray.
 *
 * @author Yair and Noam
 */
public interface Intersectable
{
	/**
	 * Returns a list of intersection points between the object and a given ray.
	 * @param ray The ray to intersect with the object.
	 * @return A list of intersection points between the object and the ray.
	 */
	List<Point> findIntersections(Ray ray);
}
