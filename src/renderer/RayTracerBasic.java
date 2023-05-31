package renderer;

import lighting.AmbientLight;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The RayTracerBasic class is an implementation of the RayTracerBase abstract class
 * that provides a basic ray tracing algorithm.
 */
public class RayTracerBasic extends RayTracerBase
{
    private static final double DELTA = 0.1;
    
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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        
        if (intersections == null)
        {
            return scene.background;
        }
        
        GeoPoint closest = ray.findClosestGeoPoint(intersections);
        return calcColor(closest, ray);
    }

    /**
     * Calculates the color at a given geometric intersection point.
     *
     * @param gp The geometric intersection point.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray));
    }
    
    /**
     * Calculates the local effects (diffuse and specular reflections) at a given intersection point.
     *
     * @param gp the intersection point
     * @param ray the ray that intersected the geometry
     * @return the color after applying the local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray)
    {
        Color color = gp.geometry.getEmission();
        
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        
        if (nv == 0) return color;
       
        Material material = gp.geometry.getMaterial();
        
        for (LightSource lightSource: scene.lights)
        {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            
            if (nl * nv > 0)
            { // sign(nl) == sing(nv)
                if (unshaded(gp, lightSource, l, n))
                {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        
        return color;
    }
    
    /**
     * Calculates the specular reflection color contribution based on the material properties and light vectors.
     *
     * @param material the material of the intersected geometry
     * @param n the surface normal vector at the intersection point
     * @param l the vector from the intersection point to the light source
     * @param nl the dot product of n and l
     * @param v the view vector
     * @return the specular reflection color contribution
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v)
    {
        Vector r = l.add(n.scale(-2 * nl));
        
        r = r.scale(-1);
        double specular = alignZero(Math.pow(r.dotProduct(v), material.nShininess));
        
        return material.kS.scale(specular > 0 ? specular : 0);
    }
    
    /**
     * Calculates the diffusive reflection color contribution based on the material properties and the dot product of the
     * surface normal and light vector.
     *
     * @param material the material of the intersected geometry
     * @param nl the dot product of the surface normal and light vector
     * @return the diffusive reflection color contribution
     */
    private Double3 calcDiffusive(Material material, double nl)
    {
        return material.kD.scale(nl > 0 ? nl : (-1) * nl);
    }
    
    /**
     * Checks if a point on a surface is unshaded by other objects in the scene, given a light source and surface properties.
     *
     * @param gp the geometric point representing the intersection
     * @param light the light source
     * @param l the direction vector from the point to the light source
     * @param n the surface normal at the intersection point
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        
        Vector  deltaVector = n.scale(DELTA);
        Point point = gp.point.add(deltaVector);
        
        Ray lightRay = new Ray(point, lightDirection);
        double distance = light.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);
        
        if(intersections == null)
            return true;
        
       return false;
    }
}
