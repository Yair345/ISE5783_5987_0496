package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera in 3D space.
 * It stores the camera's position (p0), the vector that points towards the target (vTo),
 * the up vector (vUp), and the right vector (vRight).
 * It also stores the width and height of the viewport and the distance between the camera and the viewport.
 *
 * @author Yair and Noam
 */
public class Camera
{
	/**
	 * the location of the camera in 3D space, represented as a Point object.
	 */
	private Point p0;
	
	/**
	* the direction the camera is facing, represented as a unit-length Vector object.
	*/
	private Vector vTo;
	
	/**
	 * the camera's "up" direction, represented as a unit-length Vector object.
	 */
	private Vector vUp;
	
	/**
	 * a vector perpendicular to both vTo and vUp, representing the camera's "right" direction.
	 * This is calculated from vTo and vUp in the constructor.
	 */
	private Vector vRight;
	
	/**
	 * the width of the view plane, in world coordinates.
	 */
	private double width;
	
	/**
	 * the height of the view plane, in world coordinates.
	 */
	private double height;
	
	/**
	 * the distance from the camera to the view plane, in world coordinates
	 */
	private double distance;
	
	/**
	 * Constructs a new camera with the given position, target vector and up vector.
	 *
	 * @param p0 the camera position
	 * @param vTo the vector that points towards the target
	 * @param vUp the up vector
	 *
	 * @throws IllegalArgumentException if vTo and vUp aren't orthogonal
	 */
	public Camera(Point p0, Vector vTo, Vector vUp)
	{
		if (!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException("vTo and vUp aren't orthogonal (camera constructor)");
		}
		
		this.p0 = p0;
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		
		this.vRight = this.vTo.crossProduct(this.vUp).normalize();
	}
	
	/**
	 * Sets the size of the viewport.
	 *
	 * @param width the width of the viewport
	 * @param height the height of the viewport
	 *
	 * @return this camera instance for chaining
	 */
	public Camera setVPSize(double width, double height)
	{
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	/**
	 * Sets the distance between the camera and the viewport.
	 *
	 * @param distance the distance between the camera and the viewport
	 *
	 * @return this camera instance for chaining
	 */
	public Camera setVPDistance(double distance)
	{
		this.distance = distance;
		
		return this;
	}
	
	/**
	 * Constructs a ray that passes through the pixel at (j,i) in the viewport.
	 *
	 * @param nX the number of pixels in the viewport's width
	 * @param nY the number of pixels in the viewport's height
	 * @param j the pixel's column index in the viewport
	 * @param i the pixel's row index in the viewport
	 *
	 * @return the ray that passes through the specified pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		Point Pc = p0.add(vTo.scale(distance)); // view plane center

		// Ratio
		double Ry = height / nY;
		double Rx = width / nX;

		// Pij calculation
		double Xj = (j - (nX - 1) / 2d) * Rx;
		double Yi = ((nY - 1) / 2d - i) * Ry;

		Point Pij = Pc;
		if (!Util.isZero(Xj))
		{
			Pij = Pij.add(vRight.scale(Xj));
		}
		if (!Util.isZero(Yi))
		{
			Pij = Pij.add(vUp.scale(Yi));
		}

		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);
	}

	/**
	 * Getters for all fields
	 * */
	public Point getP0()
	{
		return p0;
	}
	
	public Vector getvTo()
	{
		return vTo;
	}
	
	public Vector getvUp()
	{
		return vUp;
	}
	
	public Vector getvRight()
	{
		return vRight;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public double getDistance()
	{
		return distance;
	}
}
