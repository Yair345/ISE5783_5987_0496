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
    
    private Color calcLocalEffects(GeoPoint gp, Ray ray)
    {
        Color color= gp.geometry.getEmission();
        
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv= alignZero(n.dotProduct(v));
        
        if (nv== 0) return color;
       
        Material material = gp.geometry.getMaterial();
        
        for (LightSource lightSource: scene.lights)
        {
            Vector l = lightSource.getL(gp.point);
            double nl= alignZero(n.dotProduct(l));
            
            if (nl* nv> 0) 
            { // sign(nl) == sing(nv)
                Color iL= lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        
        return color;
    }
    
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v)
    {
        Vector r = l.add(n.scale(-2 * nl));
        
        r = r.scale(-1);
        double specular = alignZero(Math.pow(r.dotProduct(v), material.nShininess));
        
        return material.kS.scale(specular > 0 ? specular : 0);
    }
    
    private Double3 calcDiffusive(Material material, double nl)
    {
        return material.kD.scale(nl > 0 ? nl : (-1) * nl);
    }
}
