package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.Scene;

public class MiniProject1
{
    private Scene scene = new Scene.SceneBuilder("Test scene").build();
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(200, 200).setVPDistance(1000) //
            .setRayTracer(new RayTracerBasic(scene).setSoftShadow(true));//.setSample(100));

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void miniProject1() {
        scene.ambientLight = new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15, 0.15, 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(new Double3(0.8)).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(new Double3(0.8)).setShininess(60)), //
                new Sphere(30d, new Point(0, 0, -11)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)),
                new Triangle(new Point(22, 27, 70), new Point(-7, 22, 70), new Point(12, 12, 70))
                        .setMaterial(new Material().setKs(new Double3(0.8)).setShininess(60))
                        .setEmission(new Color(GREEN)) //
                ,
                new Sphere(10, new Point(55, 55, -11)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)) //
                , new Sphere(10, new Point(-55, -55, -11)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)),
                new Sphere(10, new Point(55, -55, -11)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)) //
                , new Sphere(10, new Point(-55, 55, -11)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)),
                new Sphere(10, new Point(40, 40, -100)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)) //
                , new Sphere(10, new Point(-40, -40, -100)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)),
                new Sphere(10, new Point(40, -40, -100)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30)) //
                , new Sphere(10, new Point(-40, 40, -100)) //
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(new Double3(0.5))
                                .setKs(new Double3(0.5)).setShininess(30))

        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(new SpotLight(new Color(300, 280, 500), new Point(80, 80, 175), new Vector(-3, -2, -1)) //
                .setKl(4E-4).setKq(2E-5));
        scene.lights.add(new SpotLight(new Color(650, 180, 412), new Point(12, 38, 105), new Vector(-1, 0, -4)) //
                .setKl(4E-5).setKq(2E-4));

        camera.setImageWriter(new ImageWriter("MiniProject1", 600, 600)) //
                .renderImage() //
                .writeToImage();
    }

}