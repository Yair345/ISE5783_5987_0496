package primitives;

import geometries.Intersectable;

/**
 * The Box class represents an axis-aligned bounding box in three-dimensional space.
 * It is defined by its minimum and maximum coordinates in the x, y, and z axes.
 */
public class Box
{
	/**
	 * Minimum of x
	 */
	public double minX = Double.POSITIVE_INFINITY;
	
	/**
	 * Minimum of y
	 */
	public double minY = Double.POSITIVE_INFINITY;
	
	/**
	 * Minimum of z
	 */
	public double minZ = Double.POSITIVE_INFINITY;
	
	/**
	 * Maximum of x
	 */
	public double maxX = Double.NEGATIVE_INFINITY;
	
	/**
	 * Maximum of y
	 */
	public double maxY = Double.NEGATIVE_INFINITY;
	
	/**
	 * Maximum of z
	 */
	public double maxZ = Double.NEGATIVE_INFINITY;
	
	/**
	 * Calculates the distance between two geometries based on their bounding boxes.
	 *
	 * @param geoI the first geometry
	 * @param geoJ the second geometry
	 * @return the distance between the bounding boxes of the geometries
	 */
	public static double distance(Intersectable geoI, Intersectable geoJ)
	{
		double midX, midY, midZ;
		
		Box boxI = geoI.box;
		Box boxJ = geoJ.box;
		
		midX = (boxI.minX + boxI.maxX) / 2d;
		midY = (boxI.minY + boxI.maxY) / 2d;
		midZ = (boxI.minZ + boxI.maxZ) / 2d;
		Point pGeoI = new Point(midX, midY, midZ);
		
		midX = (boxJ.minX + boxJ.maxX) / 2d;
		midY = (boxJ.minY + boxJ.maxY) / 2d;
		midZ = (boxJ.minZ + boxJ.maxZ) / 2d;
		Point pGeoJ = new Point(midX, midY, midZ);
		
		return pGeoI.distance(pGeoJ);
	}
	
	/**
	 * Check if the ray intersects with the box
	 *
	 * @param ray the ray
	 * @return true or false
	 */
	public Boolean checkIntersectionWithBox(Ray ray)
	{
		Vector rayVector = ray.getDir();
		Point rayPoint = ray.getP0();
		
		double pCX = rayPoint.getX();
		double pCY = rayPoint.getY();
		double pCZ = rayPoint.getZ();
		
		double pVX = rayVector.getX();
		double pVY = rayVector.getY();
		double pVZ = rayVector.getZ();
		
		double tXmin = (minX - pCX) / pVX;
		double tXmax = (maxX - pCX) / pVX;
		
		if (tXmin > tXmax)
		{
			double temp = tXmin;
			tXmin = tXmax;
			tXmax = temp;
		}
		
		double tYmin = (minY - pCY) / pVY;
		double tYmax = (maxY - pCY) / pVY;
		if (tYmin > tYmax)
		{
			double temp = tYmin;
			tYmin = tYmax;
			tYmax = temp;
		}
		
		if ((tXmin > tYmax) || (tYmin > tXmax))
			return false;
		
		if (tYmin > tXmin)
			tXmin = tYmin;
		
		if (tYmax < tXmax)
			tXmax = tYmax;
		
		
		double tZmin = (minZ - pCZ) / pVZ;
		double tZmax = (maxZ - pCZ) / pVZ;
		
		if (tZmin > tZmax)
		{
			double temp = tZmin;
			tZmin = tZmax;
			tZmax = temp;
		}
		
		return ((tXmin <= tZmax) && (tZmin <= tXmax));
	}
	
	public boolean checkInfinite()
	{
		return  Double.isInfinite(minX) ||
				Double.isInfinite(minY) ||
				Double.isInfinite(minZ) ||
				Double.isInfinite(maxX) ||
				Double.isInfinite(maxY) ||
				Double.isInfinite(maxZ);
	}
}
