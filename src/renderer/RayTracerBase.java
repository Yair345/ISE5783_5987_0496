package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class is an abstract base class for ray tracing algorithms.
 * It provides the basic structure and functionality for tracing rays in a scene.
 */
public abstract class RayTracerBase
{
    /**
     * Scene to render
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param s The scene to be rendered.
     */
    public RayTracerBase(Scene s)
    {
        this.scene = s;
    }

    /**
     * Traces a ray and returns the color at the intersection point in the scene.
     *
     * @param ray The ray to be traced.
     * @return The color at the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}
