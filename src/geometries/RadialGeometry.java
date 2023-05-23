package geometries;

/**
 * The RadialGeometry abstract class represents a geometrical object with a radial attribute in 3D space.
 *
 * @author Yair and Noam
 */
public abstract class RadialGeometry extends Geometry
{
    /**
     * The radial attribute of the geometrical object.
     */
    protected double radius;
    
    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param radius the radius of the RadialGeometry object.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public RadialGeometry(double radius)
    {
        if (radius < 0) throw new IllegalArgumentException("radius cannot be negative");
        this.radius = radius;
    }
    
    /**
     * Returns the radius of the RadialGeometry object.
     *
     * @return the radius of the RadialGeometry object.
     */
    public double getRadius()
    {
        return radius;
    }
}

