package renderer;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import parser.Json;
import primitives.Color;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ImageWriterTests class contains a unit test for the writeToImage() method of the ImageWriter class.
 *
 * @author Yair and Noam
 */
class ImageWriterTests
{
	/**
	 * JUnit test that creates a new image with specified dimensions
	 * and fills it with a green grid on a black background.
	 * The grid has a constant spacing of 50 pixels.
	 * The test then saves the image to a file named "firstTest".
	 */
	@Test
	void testWriteToImage()
	{
		int nx = 800;
		int ny = 500;
		int gridConstant = 50;
		Color green  = new Color(0d,255d,0d);
		Color black = new Color(0d,0d,0d);
		
		ImageWriter imageWriter = new ImageWriter("firstTest", nx, ny);
		
		// filling the background and creating grid
		for (int i = 0; i < nx; i++)
		{
			for (int j = 0; j < ny; j++)
			{
				if (i % gridConstant == 0 || j % gridConstant == 0)
				{
					imageWriter.writePixel(i, j, black);
				}
				else
				{
					imageWriter.writePixel(i, j, green);
				}
			}
		}
		
		imageWriter.writeToImage();
	}
	
	/**
	 * A unit test method to test the creation of a JSON file from an ImageWriter object.
	 */
	@Test
	void testCreateJson()
	{
		ImageWriter imageWriter = new ImageWriter("Json Test", 1500, 1000);
		
		Json.imageWriterToJson(imageWriter, System.getProperty("user.dir") + "/json/imageWriterJson.json");
	}
	
	/**
	 * A unit test method to test deserialization of an ImageWriter object from a JSON file.
	 */
	@Test
	void testFromJson()
	{
		ImageWriter imageWriterTest = Json.imageWriterFromJson(System.getProperty("user.dir") + "/json/imageWriterJson.json");
		ImageWriter imageWriterReal = new ImageWriter("Json Test", 1500, 1000);
		
		assertTrue(imageWriterReal.equals(imageWriterTest), "Json deserialize");
	}
}