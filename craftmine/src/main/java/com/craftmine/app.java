package com.craftmine;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class app extends SimpleApplication {

    public static void main(String[] args) {
        app ap = new app();
        ap.start();
    }

    @Override
    public void simpleInitApp() {
        world w = new world(assetManager);
        rootNode.attachChild(w);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(ambient);

        flyCam.setMoveSpeed(10f);
    }
}
