package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource
{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight(Color intensity, Point position)
    {
        super(intensity);
        this.position = position;
    }

    public PointLight setKc(double kC)
    {
        this.kC = kC;

        return this;
    }

    public PointLight setKl(double kL)
    {
        this.kL = kL;

        return this;
    }

    public PointLight setKq(double kQ)
    {
        this.kQ = kQ;

        return this;
    }

    @Override
    public Color getIntensity(Point p)
    {
        double d = p.distance(position);
        double temp = kC + (kL * d) + (kQ * d * d);

        return intensity.scale(1 / temp);
    }

    @Override
    public Vector getL(Point p)
    {
        try
        {
            return p.subtract(position);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
}
