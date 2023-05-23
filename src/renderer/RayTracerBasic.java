package renderer;

import lighting.AmbientLight;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * The RayTracerBasic class is an implementation of the RayTracerBase abstract class
 * that provides a basic ray tracing algorithm.
 */
public class RayTracerBasic extends RayTracerBase
{
    /**
     * Constructs a RayTracerBasic object with the specified scene.
     *
     * @param s The scene to be rendered.
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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);
        
        if (intersections == null)
        {
            return scene.background;
        }
        
        GeoPoint closest = ray.findClosestGeoPoint(intersections);
        return calcColor(closest);
    }

    /**
     * Calculates the color at a given geometric intersection point.
     *
     * @param p The geometric intersection point.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint p)
    {
        return scene.ambientLight.getIntensity().add(p.geometry.getEmission());
    }
}
