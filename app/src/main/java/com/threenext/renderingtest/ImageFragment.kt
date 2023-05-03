package com.threenext.renderingtest

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.IOException
import java.io.InputStream


private const val ARG_PARAM1 = "param1"

class ImageFragment : Fragment() {
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image, container, false)
        root.findViewById<TextView>(R.id.position).text = param1.toString()
        root.findViewById<GLSurfaceView>(R.id.surface).setRenderer(getBitmapFromAsset("$param1.jpg")?.let { ImageRenderer(it) })
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) = ImageFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
        }
    }

    private fun getBitmapFromAsset(filePath: String): Bitmap? {
        return context?.let {
            val assetManager: AssetManager = it.assets
            val istr: InputStream
            var bitmap: Bitmap? = null
            try {
                istr = assetManager.open(filePath)
                bitmap = BitmapFactory.decodeStream(istr)
            } catch (e: IOException) {
                // handle exception
            }
            bitmap
        }
    }
}