package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * The Scene class represents a scene with a name, background color, geometries, and ambient light.
 *
 * @author Yair and Noam
 */
public class Scene
{
	// Fields
	private final String name;
	private final Color background;
	private final Geometries geometries;
	private AmbientLight ambientLight;
	
	/**
	 * Constructor using builder pattern.
	 *
	 * @param builder - a SceneBuilder object used to construct the Scene instance.
	 */
	private Scene(SceneBuilder builder)
	{
		name = builder.name;
		background = builder.background;
		ambientLight = builder.ambientLight;
		geometries = builder.geometries;
	}
	
	/**
	* Getters
	*/
	public String getName()
	{
		return name;
	}
	
	public Color getBackground()
	{
		return background;
	}
	
	public AmbientLight getAmbientLight()
	{
		return ambientLight;
	}
	
	public Geometries getGeometries()
	{
		return geometries;
	}
	
	/**
	 * The SceneBuilder class is a builder for constructing Scene objects.
	 */
	public static class SceneBuilder
	{
		// Fields
		private final String name;
		private Color background = Color.BLACK;
		private AmbientLight ambientLight = AmbientLight.NONE;
		private Geometries geometries = new Geometries();
		
		/**
		 * Constructor.
		 *
		 * @param name - a string representing the name of the scene.
		 */
		public SceneBuilder(String name)
		{
			this.name = name;
		}

		/**
		 * Build scene using builder pattern
		 * @return Scene object
		 */
		public Scene build()
		{
			return new Scene(this);
		}
		
		/**
		 * Sets the background color of the scene.
		 *
		 * @param background - a Color object representing the background color of the scene.
		 * @return the SceneBuilder object.
		 */
		public SceneBuilder setBackground(Color background)
		{
			this.background = background;
			return this;
		}
		
		/**
		 * Sets the ambient light of the scene.
		 *
		 * @param ambientLight - an AmbientLight object representing the ambient light of the scene.
		 * @return the SceneBuilder object.
		 */
		public SceneBuilder setAmbientLight(AmbientLight ambientLight)
		{
			this.ambientLight = ambientLight;
			return this;
		}
		
		/**
		 * Sets the geometries of the scene.
		 *
		 * @param geometries - a Geometries object representing the geometries of the scene.
		 * @return the SceneBuilder object.
		 */
		public SceneBuilder setGeometries(Geometries geometries)
		{
			this.geometries = geometries;
			return this;
		}
	}
}
