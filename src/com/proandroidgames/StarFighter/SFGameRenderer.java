package com.proandroidgames.StarFighter;

import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SFGameRenderer implements Renderer {

    private SFBackground background = new SFBackground();
    private SFBackground background2 = new SFBackground();
    private float bgScroll1;
    private float bgScroll2;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        //TODO Add texture loading for background image
        background.loadTexture(gl, SFEngine.BACKGROUND_LAYER_ONE, SFEngine.context);
        background2.loadTexture(gl, SFEngine.BACKGROUND_LAYER_TWO, SFEngine.context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //розміри роб. області. (0, 0) - нижній лівий кут
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

        //TODO Add texture loading for background image
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        scrollBackground1(gl);
        scrollBackground2(gl);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
    }

    private void scrollBackground1(GL10 gl){
        if (bgScroll1 == Float.MAX_VALUE){
            bgScroll1 = 0f;
        }
        //reset scale and translate
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);
        //reset scale and translate

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, bgScroll1, 0f); //translate matrix

        background.draw(gl);
        gl.glPopMatrix();
        bgScroll1 += SFEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

    private void scrollBackground2(GL10 gl){
        if(bgScroll2 == Float.MAX_VALUE){
            bgScroll2 = 0;
        }

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(0.5f, 1f, 1f);
        gl.glTranslatef(1.5f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, bgScroll2, 0f);

        background2.draw(gl);
        gl.glPopMatrix();
        bgScroll2 += SFEngine.SCROLL_BACKGROUND_2;
        gl.glLoadIdentity();
}
}
