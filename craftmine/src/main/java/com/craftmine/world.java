package com.craftmine;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.raylabz.opensimplex.OpenSimplexNoise;

public class world extends Node {

    private AssetManager assetManager;
    OpenSimplexNoise noise;

    public final int numChunks = 16; // number of chunks per dimension (ex: n = 4, 16 chunks in a 4x4 grid)

    public world(AssetManager assetManager, OpenSimplexNoise noise) {
        this.noise = noise;
        this.caveNoise = caveNoise;
        this.assetManager = assetManager;
        generateWorld(numChunks);
    }

    private void generateWorld(int numChunks) {
        for (int x = 0; x < numChunks; x++) {
            for (int z = 0; z < numChunks; z++) {
                // Pass the chunk's world position to the constructor
                chunk c = new chunk(assetManager, noise, x, z);
                c.setLocalTranslation(x * c.SIZE, 0, z * c.SIZE);
                this.attachChild(c);
            }
        }
    }

}
