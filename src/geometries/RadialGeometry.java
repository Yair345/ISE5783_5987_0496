package geometries;

public abstract class RadialGeometry implements Geometry
{
    protected double radius;

    public RadialGeometry(double radius)
    {
        if (radius < 0) throw new IllegalArgumentException("radius cannot be negative");
        this.radius = radius;
    }
}
