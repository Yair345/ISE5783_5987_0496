package primitives;

/**
 * The Material class represents the properties of a material used for shading and
 * rendering objects in a scene.
 */
public class Material
{
    /**
     * Diffuse reflection coefficient
     */
    public Double3 kD = Double3.ZERO;
    
    /**
     * Specular reflection coefficient
     */
    public Double3 kS = Double3.ZERO;
    
    /**
     * Transmission (refraction) coefficient
     */
    public Double3 kT = Double3.ZERO;
    
    /**
     * Reflection coefficient
     */
    public Double3 kR = Double3.ZERO;
    
    /**
     * Shininess factor for specular reflection
     */
    public int nShininess = 0;
    
    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD the diffuse reflection coefficient
     * @return the updated Material object
     */
    public Material setKd(Double3 kD)
    {
        this.kD = kD;

        return this;
    }
    
    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD the diffuse reflection coefficient
     * @return the updated Material object
     */
    public Material setKd(double kD)
    {
        this.kD = new Double3(kD);

        return this;
    }
    
    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS the specular reflection coefficient
     * @return the updated Material object
     */
    public Material setKs(double kS)
    {
        this.kS = new Double3(kS);

        return this;
    }
    
    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS the specular reflection coefficient
     * @return the updated Material object
     */
    public Material setKs(Double3 kS)
    {
        this.kS = kS;

        return this;
    }
    
    /**
     * Sets the shininess value of the material.
     *
     * @param nShininess the shininess value
     * @return the updated Material object
     */
    public Material setShininess(int nShininess)
    {
        this.nShininess = nShininess;

        return this;
    }
    
    /**
     * Sets the transmission coefficient (kT) of the material.
     *
     * @param kT The transmission coefficient as a Double3 object.
     * @return The material instance with the updated transmission coefficient.
     */
    public Material setKt(Double3 kT)
    {
        this.kT = kT;
        
        return this;
    }
    
    /**
     * Sets the transmission coefficient (kT) of the material.
     *
     * @param kT The transmission coefficient as a double value.
     * @return The material instance with the updated transmission coefficient.
     */
    public Material setKt(double kT)
    {
        this.kT = new Double3(kT);
        
        return this;
    }
    
    /**
     * Sets the reflection coefficient (kR) of the material.
     *
     * @param kR The reflection coefficient as a Double3 object.
     * @return The material instance with the updated reflection coefficient.
     */
    public Material setKr(Double3 kR)
    {
        this.kR = kR;
        
        return this;
    }
    
    /**
     * Sets the reflection coefficient (kR) of the material.
     *
     * @param kR The reflection coefficient as a double value.
     * @return The material instance with the updated reflection coefficient.
     */
    public Material setKr(double kR)
    {
        this.kR = new Double3(kR);
        
        return this;
    }
}
