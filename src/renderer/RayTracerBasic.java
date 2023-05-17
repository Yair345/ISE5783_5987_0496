package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBasic class is an implementation of the RayTracerBase abstract class
 * that provides a basic ray tracing algorithm.
 */
public class RayTracerBasic extends RayTracerBase
{
    /**
     *Constructs a RayTracerBasic object with the specified scene.
     *
     *@param s The scene to be rendered.
     */
    public RayTracerBasic(Scene s)
    {
        super(s);
    }

    /**
     * Traces a ray using the basic ray tracing algorithm.
     *
     * @param ray The ray to be traced.
     * @return The color at the intersection point.
     */
    @Override
    public Color traceRay(Ray ray)
    {
        return null;
    }
}
