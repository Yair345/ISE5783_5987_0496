package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometrical object in 3D space.
 *
 * @author Yair and Noam
 */
public abstract class Geometry extends Intersectable
{
    /**
     * Base color of a geometry
     */
    protected Color emission = Color.BLACK;
    
    /**
     * Material of the geometry
     */
    private Material material = new Material();

    /**
     * Returns the emission color of the geometry.
     *
     * @return The emission color.
     */
    public Color getEmission()
    {
        return emission;
    }

    public Material getMaterial()
    {
        return material;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to be set.
     * @return The geometry instance with the updated emission color.
     */
    public Geometry setEmission(Color emission)
    {
        this.emission = emission;

        return this;
    }
    
    /**
     * Sets the material for the geometry object.
     *
     * @param material the material to be set
     * @return the updated Geometry object
     */
    public Geometry setMaterial(Material material)
    {
        this.material = material;

        return this;
    }

    /**
     * Returns the normal vector of the geometry at the specified point.
     *
     * @param p the point at which to compute the normal vector.
     * @return the normal vector of the geometry at the specified point.
     */
    public abstract Vector getNormal(Point p);
}
