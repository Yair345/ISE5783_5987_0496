package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight
{
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction)
    {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p)
    {
        Color temp = super.getIntensity(p);
        Vector vec = super.getL(p);
        double t = vec.dotProduct(direction.normalize());

        return temp.scale(0 < t ? t : 0);
    }

    public SpotLight setNarrowBeam(int i)
    {
        return this;
    }
}
