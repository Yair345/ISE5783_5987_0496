package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The Scene class represents a scene with a name, background color, geometries, and ambient light.
 *
 * @author Yair and Noam
 */
public class Scene
{
	// Fields
	private final String name;
	public final Color background;
	public final Geometries geometries;
	public AmbientLight ambientLight;
	public final List<LightSource> lights;
	
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
		lights = builder.lights;
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
		private List<LightSource> lights = new LinkedList<>();
		
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
		
		/**
		 * Sets the list of light sources in the scene.
		 *
		 * @param lights the list of light sources
		 * @return the updated SceneBuilder object
		 */
		public SceneBuilder setLights(List<LightSource> lights)
		{
			this.lights = lights;
			return this;
		}
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) {return true;}
		if (!(o instanceof Scene scene)) {return false;}
		return Objects.equals(name, scene.name) && Objects.equals(background, scene.background) && Objects.equals(geometries, scene.geometries) && Objects.equals(ambientLight, scene.ambientLight);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(name, background, geometries, ambientLight);
	}
}
