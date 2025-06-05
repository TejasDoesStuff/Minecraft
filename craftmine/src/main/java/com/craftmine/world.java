package com.craftmine;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.raylabz.opensimplex.OpenSimplexNoise;

public class world extends Node {

    private AssetManager assetManager;
    OpenSimplexNoise noise;

    public final int numChunks = 3; // number of chunks per dimension
    // private int chunks[][][] = new int[numChunks][numChunks][numChunks];

    public world(AssetManager assetManager, OpenSimplexNoise noise) {
        this.noise = noise;
        this.assetManager = assetManager;
        generateWorld(numChunks);
    }

    private void generateWorld(int numChunks) {
        for (int x = 0; x < numChunks; x++) {
            for (int z = 0; z < numChunks; z++) {
                // Pass the chunk's world position to the constructor
                chunk c = new chunk(assetManager, noise, x, z);
                c.setLocalTranslation(x * c.size, 0, z * c.size);
                this.attachChild(c);
            }
        }
    }

}
