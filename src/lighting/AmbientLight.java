package lighting;

import primitives.Color;
import primitives.Double3;

import java.util.Objects;

public class AmbientLight
{
	private Color intensity;
	
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	public AmbientLight(Color Ia, Double3 Ka)
	{
		intensity = Ia.scale(Ka);
	}
	
	public AmbientLight(Color Ia, double Ka)
	{
		intensity = Ia.scale(Ka);
	}
	
	public Color getIntensity()
	{
		return intensity;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) {return true;}
		if (!(o instanceof AmbientLight that)) {return false;}
		return Objects.equals(intensity, that.intensity);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(intensity);
	}
}
