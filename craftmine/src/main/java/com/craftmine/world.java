package com.craftmine;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class world extends Node {

    private AssetManager assetManager;

    public final int numChunks = 1; // number of chunks per dimension
    // private int chunks[][][] = new int[numChunks][numChunks][numChunks];

    public world(AssetManager assetManager) {
        this.assetManager = assetManager;
        generateWorld(numChunks);
    }

    private void generateWorld(int numChunks) {
        for (int x = 0; x < numChunks; x++) {
            for (int y = 0; y < numChunks; y++) {
                for (int z = 0; z < numChunks; z++) {
                    chunk c = new chunk(assetManager);
                    c.setLocalTranslation(x * c.size, y, z * c.size);
                    this.attachChild(c);
                }
            }
        }
    }

}
