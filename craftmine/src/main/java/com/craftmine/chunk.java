package com.craftmine;

import java.util.ArrayList;
import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.raylabz.opensimplex.OpenSimplexNoise;
import com.raylabz.opensimplex.Range;
import com.raylabz.opensimplex.RangedValue;

public class chunk extends Node {

    private AssetManager assetManager;
    OpenSimplexNoise noise;

    public final int size = 16;

    int chunkX;
    int chunkZ;

    private int[][][] blocks = new int[size][size][size];

    Mesh mesh = new Mesh();

    public chunk(AssetManager assetManager, OpenSimplexNoise noise, int x, int z) {
        this.assetManager = assetManager;
        this.noise = noise;
        chunkX = x;
        chunkZ = z;
        generateWorld();
        checkBlocks();
        // addBorder();
    }

    private void generateWorld() {
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                int worldX = x + chunkX * size;
                int worldZ = z + chunkZ * size;

                RangedValue value = noise.getNoise2D(worldX, worldZ);
                double shiftedValue = value.getValue(new Range(0, 17));
                for (int y = 0; y < size; y++) {
                    if (y < shiftedValue) {
                        blocks[x][y][z] = 1;
                    } else {
                        blocks[x][y][z] = 0;
                    }
                }
            }
        }
    }

    // using a mesh to make rendering better --------------------------------------
    private void checkBlocks() {
        List<Float> vertexBuffer = new ArrayList<>();
        List<Float> normalBuffer = new ArrayList<>();
        List<Integer> indexBuffer = new ArrayList<>();
        int vertexCount = 0;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (blocks[x][y][z] != 0 && isVisible(x, y, z)) {
                        vertexCount = addExposedFaces(x, y, z, vertexBuffer, normalBuffer, indexBuffer, vertexCount);
                    }
                }
            }
        }

        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(toFloatArray(vertexBuffer)));
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(toFloatArray(normalBuffer)));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(toIntArray(indexBuffer)));
        mesh.updateBound();

        Geometry geom = new Geometry("ChunkMesh", mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Ambient", ColorRGBA.White.mult(0.3f));
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 64f);
        mat.setBoolean("UseMaterialColors", true);
        geom.setMaterial(mat);
        this.attachChild(geom);

        // AI GENERATED DEBUG STATEMENT
        System.out.println("Created mesh with " + (vertexBuffer.size() / 3) + " vertices and " + (indexBuffer.size() / 3) + " triangles");
    }

    private int addExposedFaces(int x, int y, int z, List<Float> vertexBuffer, List<Float> normalBuffer, List<Integer> indexBuffer, int vertexCount) {
        int currentVertexCount = vertexCount;

        // AI GENERATED VERTICIES ( i got lazy )
        float[][] faceVertices = {
            // Top
            {x - 0.5f, y + 0.5f, z - 0.5f, x - 0.5f, y + 0.5f, z + 0.5f, x + 0.5f, y + 0.5f, z + 0.5f, x + 0.5f, y + 0.5f, z - 0.5f},
            // Bottom
            {x - 0.5f, y - 0.5f, z - 0.5f, x + 0.5f, y - 0.5f, z - 0.5f, x + 0.5f, y - 0.5f, z + 0.5f, x - 0.5f, y - 0.5f, z + 0.5f},
            // Left
            {x - 0.5f, y - 0.5f, z - 0.5f, x - 0.5f, y - 0.5f, z + 0.5f, x - 0.5f, y + 0.5f, z + 0.5f, x - 0.5f, y + 0.5f, z - 0.5f},
            // Right
            {x + 0.5f, y - 0.5f, z - 0.5f, x + 0.5f, y + 0.5f, z - 0.5f, x + 0.5f, y + 0.5f, z + 0.5f, x + 0.5f, y - 0.5f, z + 0.5f},
            // Front
            {x - 0.5f, y - 0.5f, z + 0.5f, x + 0.5f, y - 0.5f, z + 0.5f, x + 0.5f, y + 0.5f, z + 0.5f, x - 0.5f, y + 0.5f, z + 0.5f},
            // Back
            {x - 0.5f, y - 0.5f, z - 0.5f, x - 0.5f, y + 0.5f, z - 0.5f, x + 0.5f, y + 0.5f, z - 0.5f, x + 0.5f, y - 0.5f, z - 0.5f}
        };

        float[][] faceNormals = {
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0}, // Top
            {0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0}, // Bottom 
            {-1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0}, // Left
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, // Right
            {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1}, // Front
            {0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1} // Back
        };

        int[] faceIndices = {0, 1, 2, 2, 3, 0};

        if (y + 1 >= size || blocks[x][y + 1][z] == 0) { // Top
            currentVertexCount = addFace(faceVertices[0], faceNormals[0], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }
        if (y - 1 < 0 || blocks[x][y - 1][z] == 0) { // Bottom
            currentVertexCount = addFace(faceVertices[1], faceNormals[1], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }
        if (x - 1 < 0 || blocks[x - 1][y][z] == 0) { // Left
            currentVertexCount = addFace(faceVertices[2], faceNormals[2], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }
        if (x + 1 >= size || blocks[x + 1][y][z] == 0) { // Right
            currentVertexCount = addFace(faceVertices[3], faceNormals[3], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }
        if (z + 1 >= size || blocks[x][y][z + 1] == 0) { // Front
            currentVertexCount = addFace(faceVertices[4], faceNormals[4], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }
        if (z - 1 < 0 || blocks[x][y][z - 1] == 0) { // Back
            currentVertexCount = addFace(faceVertices[5], faceNormals[5], faceIndices, vertexBuffer, normalBuffer, indexBuffer, currentVertexCount);
        }

        return currentVertexCount;
    }

    private boolean isVisible(int x, int y, int z) {
        return (x - 1 < 0 || blocks[x - 1][y][z] == 0)
                || (x + 1 >= size || blocks[x + 1][y][z] == 0)
                || (y - 1 < 0 || blocks[x][y - 1][z] == 0)
                || (y + 1 >= size || blocks[x][y + 1][z] == 0)
                || (z - 1 < 0 || blocks[x][y][z - 1] == 0)
                || (z + 1 >= size || blocks[x][y][z + 1] == 0);
    }

    private int addFace(float[] vertices, float[] normals, int[] indices, List<Float> vertexBuffer, List<Float> normalBuffer, List<Integer> indexBuffer, int vertexCount) {
        for (int i = 0; i < vertices.length; i += 3) {
            vertexBuffer.add(vertices[i]);     // x
            vertexBuffer.add(vertices[i + 1]); // y
            vertexBuffer.add(vertices[i + 2]); // z
        }

        for (int i = 0; i < normals.length; i += 3) {
            normalBuffer.add(normals[i]);     // nx
            normalBuffer.add(normals[i + 1]); // ny
            normalBuffer.add(normals[i + 2]); // nz
        }

        for (int index : indices) {
            indexBuffer.add(vertexCount + index);
        }

        return vertexCount + 4;
    }

    private float[] toFloatArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    // building the mesh for the chunk (INEFFICIENT, UNUSED) --------------------------------------
    // private void buildMesh() {
    //     for (int x = 0; x < size; x++) {
    //         for (int y = 0; y < size; y++) {
    //             for (int z = 0; z < size; z++) {
    //                 if (blocks[x][y][z] != 0) {
    //                     Box b = new Box(0.5f, 0.5f, 0.5f);
    //                     Geometry geom = new Geometry("Block", b);
    //                     geom.setLocalTranslation(x, y, z);
    //                     Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    //                     mat.setColor("Diffuse", ColorRGBA.Blue);
    //                     mat.setColor("Specular", ColorRGBA.White);
    //                     mat.setFloat("Shininess", 64f);
    //                     geom.setMaterial(mat);
    //                     this.attachChild(geom);
    //                 }
    //             }
    //         }
    //     }
    // }
    //
    // AI GENERATED FUNCTION - adds a red wireframe border around the chunk
    // private void addBorder() {
    //     // Create a wireframe box to represent the chunk's border
    //     Box borderBox = new Box(size / 2f, size / 2f, size / 2f);
    //     Geometry borderGeom = new Geometry("ChunkBorder", borderBox);
    //     // Set the border material to a wireframe style
    //     Material borderMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    //     borderMat.setColor("Color", ColorRGBA.Red); // Border color
    //     borderMat.getAdditionalRenderState().setWireframe(true); // Enable wireframe mode
    //     borderGeom.setMaterial(borderMat);
    //     // Position the border to align with the chunk
    //     borderGeom.setLocalTranslation(size / 2f, size / 2f, size / 2f);
    //     // Attach the border to the chunk
    //     this.attachChild(borderGeom);
    // }
    //
    // getters and setters for blocks
    public int getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, int id) {
        blocks[x][y][z] = id;
    }
}
