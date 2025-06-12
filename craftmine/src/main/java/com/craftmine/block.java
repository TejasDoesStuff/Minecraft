package com.craftmine;

import com.jme3.math.ColorRGBA;

public class block {

    // all block types
    public static final int AIR = 0;
    public static final int DIRT = 1;
    public static final int STONE = 2;
    public static final int GRASS = 3;

    private int id;
    private String name;
    private ColorRGBA color;

    // directory of blocks so that if you call getBlock you can get the block by its id
    private static block[] blocks = new block[256];

    public block(int id, String name, ColorRGBA color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    // adds blocks to the directory
    static {
        registerBlock(new block(AIR, "Air", new ColorRGBA(1f, 1f, 1f, 0f)));
        registerBlock(new block(DIRT, "Dirt", new ColorRGBA(0.545f, 0.271f, 0.075f, 1)));
        registerBlock(new block(STONE, "Stone", new ColorRGBA(0.5f, 0.5f, 0.5f, 1)));
        registerBlock(new block(GRASS, "Grass", new ColorRGBA(0.133f, 0.545f, 0.133f, 1)));
    }

    // prints every block that is registered
    public static void printRegistry() {
        System.out.println("Block Registry:");
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != null) {
                System.out.println("ID: " + blocks[i].getId() + ", Name: " + blocks[i].getName() + ", Color: " + blocks[i].getColor());
            }
        }
    }

    public static void registerBlock(block b) {
        blocks[b.id] = b;
    }

    public static block getBlock(int id) {
        return blocks[id];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        block blk = (block) obj;
        return id == blk.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ColorRGBA getColor() {
        return color;
    }
}
