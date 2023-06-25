package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.stream.*;

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
	 * The imageWriter field represents the image writer used by the camera.
	 */
	private ImageWriter imageWriter;

	/**
	 * The rayTracerBase field represents the ray tracer base used by the camera.
	 */
	private RayTracerBase rayTracerBase;

	/**
	 * Number of threads
	 */
	private int threadsCount = 1;
	/**
	 *
	 */
	private double printInterval = 1;

	/**
	 * The pixel manager responsible for managing pixels or pixel-related operations.
	 * This private variable is used within the class scope.
	 */
	private PixelManager pixelManager;
	
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
		if (width <= 0 || height <= 0)
		{
			throw new IllegalArgumentException("Fields cannot be less then 0 (setVPSize)");
		}

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
		if (distance <= 0)
		{
			throw new IllegalArgumentException("Fields cannot be 0 (setVPDistance)");
		}

		this.distance = distance;
		
		return this;
	}

	/**
	 * Sets the image writer for the camera.
	 *
	 * @param imageWriter The image writer to be set.
	 * @return The camera instance with the updated image writer.
	 */
	public Camera setImageWriter(ImageWriter imageWriter)
	{
		this.imageWriter = imageWriter;

		return this;
	}

	/**
	 * Sets the ray tracer base for the camera.
	 *
	 * @param rayTracerBase The ray tracer base to be set.
	 * @return The camera instance with the updated ray tracer base.
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase)
	{
		this.rayTracerBase = rayTracerBase;

		return this;
	}

	/**
	 * Sets the print interval for debug information or log messages.
	 *
	 * @param k The new value for the print interval.
	 * @return The modified Camera object.
	 */
	public Camera setDebugPrint(double k)
	{
		this.printInterval = k;
		return this;
	}

	/**
	 * Sets the number of threads to be used by the camera.
	 *
	 * @param n The new value for the number of threads.
	 * @return The modified Camera object.
	 */
	public Camera setMultithreading(int n)
	{
		this.threadsCount = n;
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
		// view plane center
		Point Pc = p0.add(vTo.scale(distance));

		// Ratios of the pixel
		double Ry = height / nY;
		double Rx = width / nX;

		// offsets of Pij in vUp axis and vRight axis respectively
		double Xj = (j - (nX - 1) / 2d) * Rx;
		double Yi = ((nY - 1) / 2d - i) * Ry;
		
		// Pij calculation
		Point Pij = Pc;
		if (!isZero(Xj))
		{
			Pij = Pij.add(vRight.scale(Xj));
		}
		if (!isZero(Yi))
		{
			Pij = Pij.add(vUp.scale(Yi));
		}

		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);
	}

	/**
	 * Renders the image using the configured image writer and ray tracer base.
	 * Throws an exception if the image writer or ray tracer base is not initialized.
	 *
	 * @throws UnsupportedOperationException if the image writer or ray tracer base is not initialized.
	 */
	public Camera renderImage()
	{
		// check if valid parameters
		try
		{
			testValidity();
		}
		catch (UnsupportedOperationException e)
		{
			throw new MissingResourceException(e.getMessage(), Camera.class.getName(), "");
		}
		
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();

		// Checking if we try to use threads.
		if (threadsCount == 1)
		{
			//rendering image without using of threads (by-default)
			for (int row = 0; row < Ny; row++)
			{
				for (int col = 0; col < Nx; col++)
				{
					Color color = castRay(Nx, Ny, row, col);
					imageWriter.writePixel(row, col, color);
				}
			}
		}
		else
		{
			//rendering image with using of threads
			pixelManager = new PixelManager(Ny, Nx, printInterval);

			IntStream.range(0, Ny).parallel()
					.forEach(i -> IntStream.range(0, Nx).parallel()
					.forEach(j -> castRayParallel(Nx, Ny, j, i)));
		}

		return this;
	}
	
	/**
	 * Casts a ray into the scene at the specified row and column in the image and returns the resulting
	 * color of the intersection point.
	 * @param nX the number of pixels in the x-axis of the image
	 * @param nY the number of pixels in the y-axis of the image
	 * @param row the row of the pixel in the image
	 * @param col the column of the pixel in the image
	 * @return the color of the intersection point of the casted ray in the scene
	 */
	private Color castRay(int nX, int nY, int row, int col)
	{
		Ray ray = constructRay(nX, nY, row, col);
		Color color = rayTracerBase.traceRay(ray);
		return color;
	}

	/**
	 * Casts a ray into the scene at the specified row and column in the image and returns the resulting
	 * color of the intersection point.
	 * Do the same as the other function but here this used in the threads.
	 *
	 * @param nX the number of pixels in the x-axis of the image
	 * @param nY the number of pixels in the y-axis of the image
	 * @param row the row of the pixel in the image
	 * @param col the column of the pixel in the image
	 * @return the color of the intersection point of the casted ray in the scene
	 */
	private void castRayParallel(int nX, int nY, int col, int row)
	{
		imageWriter.writePixel(col, row, rayTracerBase.traceRay(constructRay(nX, nY, col, row)));
		pixelManager.pixelDone();
	}


	/**
	 * Prints a grid on the image with a specified interval and color.
	 * Throws an exception if rendering the image is not supported or if the required resources are missing.
	 *
	 * @param interval The interval between grid lines.
	 * @param color The color of the grid lines.
	 *
	 * @throws MissingResourceException if rendering the image is not supported or if the required resources are missing.
	 * @throws UnsupportedOperationException if the image writer or ray tracer base is not initialized.
	 */
	public void printGrid(int interval, Color color)
	{
		// check if valid parameters
		try
		{
			testValidity();
		}
		catch (UnsupportedOperationException e)
		{
			throw new MissingResourceException(e.getMessage(), Camera.class.getName(), "");
		}
		
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		// paint the grid
		for (int col = 0; col < nY; col += interval)
		{
			for (int row = 0; row < nX; row++)
			{
				imageWriter.writePixel(col, row, color);
			}
		}

		for (int col = 0; col < nY; col++)
		{
			for (int row = 0; row < nX; row += interval)
			{
				imageWriter.writePixel(col, row, color);
			}
		}
		
		imageWriter.writeToImage();
	}

	/**
	 * Writes the rendered image to the output.
	 * Throws an exception if rendering the image is not supported or if the required resources are missing.
	 *
	 * @throws MissingResourceException if rendering the image is not supported or if the required resources are missing.
	 * @throws UnsupportedOperationException if the image writer or ray tracer base is not initialized.
	 */
	public void writeToImage()
	{
		try
		{
			testValidity();
		}
		catch (UnsupportedOperationException e)
		{
			throw new MissingResourceException(e.getMessage(), Camera.class.getName(), "");
		}

		imageWriter.writeToImage();
	}
	
	/**
	 * Checks the validity of the imageWriter and rayTracerBase objects. If either object is null,
	 * a MissingResourceException is thrown with a message indicating which resource was not initialized.
	 * If the exception is caught, it is rethrown as an UnsupportedOperationException.
	 *
	 * @throws UnsupportedOperationException if either the imageWriter or rayTracerBase object is null
	 */
	private void testValidity()
	{
		try
		{
			if (imageWriter == null)
			{
				throw new MissingResourceException("image writer not initialize", ImageWriter.class.getName(), "");
			}
			
			if (rayTracerBase == null)
			{
				throw new MissingResourceException("ray tracer base initialize", RayTracerBase.class.getName(), "");
			}
		}
		catch (MissingResourceException e)
		{
			throw new UnsupportedOperationException(e.getClassName() + "not initialize yet");
		}
	}
}
