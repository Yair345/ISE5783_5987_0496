package lighting;

import primitives.Color;

/**
 * The Light class represents a light source in a scene.
 */
abstract class Light
{
    protected Color intensity;
    
    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity)
    {
        this.intensity = intensity;
    }
    
    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity()
    {
        return intensity;
    }
}
