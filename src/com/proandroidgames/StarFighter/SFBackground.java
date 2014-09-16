package com.proandroidgames.StarFighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SFBackground {

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;

    private int[] textures = new int[1];

    private float[] vertices = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f
    };

    private float[] texture = {
            0.0f, 0.0f,
            1.0f, 0f,
            1f, 1.0f,
            0f, 1f
    };

    private byte[] indices = {
            0, 1, 2,
            0, 2, 3
    };

    public SFBackground(){
        ByteBuffer byteBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuff.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuff.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuff = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuff.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuff.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl){
        //Створити(викликати згенерований текстурний об`єкт)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    public void loadTexture(GL10 gl, int texture, Context context){
        InputStream inputStream = context.getResources().openRawResource(texture);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        //infinitive scrolling
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

}
