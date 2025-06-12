package com.craftmine;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.raylabz.opensimplex.OpenSimplexNoise;

public class app extends SimpleApplication {

    OpenSimplexNoise noise;

    public static void main(String[] args) {
        app ap = new app();
        ap.start();
    }

    @Override
    public void simpleInitApp() {
        block.printRegistry();

        noise = new OpenSimplexNoise(System.currentTimeMillis()); // create a random noisemap for the terrain

        world w = new world(assetManager, noise); // create a new world with the asset manager and noise map

        rootNode.attachChild(w); // add the world to the main frame

        // creates sun lighting
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(
                new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);

        // creates ambient lighting
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(ambientLight);

        // creates shadow rendering
        final int SHADOWMAP_SIZE = 1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(
                true);

        // adds the shadow filter to the viewport
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);

        flyCam.setMoveSpeed(50f); // increases movement speed

    }
}
