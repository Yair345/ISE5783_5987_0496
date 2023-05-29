package primitives;

/**
 * The Material class represents the properties of a material used for shading and
 * rendering objects in a scene.
 */
public class Material
{
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
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

}
