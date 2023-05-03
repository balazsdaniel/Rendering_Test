package com.threenext.renderingtest

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class ImageRenderer(bitmap: Bitmap) : GLSurfaceView.Renderer {
    private var textures: IntArray = IntArray(1)
    private val bitmap: Bitmap

    init {
        this.bitmap = bitmap
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        textures = IntArray(1)
        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glGenTextures(1, textures, 0)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0])
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE.toFloat())
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        gl.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10) {
        gl.glActiveTexture(GL10.GL_TEXTURE0)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0])
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VERTEX_BUFFER)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, TEXCOORD_BUFFER)
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4)
    }

    companion object {
        private val VERTEX_COORDINATES = floatArrayOf(
            -1.0f, +1.0f, 0.0f,
            +1.0f, +1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            +1.0f, -1.0f, 0.0f
        )
        private val TEXTURE_COORDINATES = floatArrayOf(
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
        )
        private val TEXCOORD_BUFFER: Buffer = ByteBuffer.allocateDirect(TEXTURE_COORDINATES.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(TEXTURE_COORDINATES).rewind()
        private val VERTEX_BUFFER: Buffer = ByteBuffer.allocateDirect(VERTEX_COORDINATES.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(VERTEX_COORDINATES).rewind()
    }
}