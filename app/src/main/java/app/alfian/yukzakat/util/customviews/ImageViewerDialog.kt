package app.alfian.yukzakat.util.customviews

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import app.alfian.yukzakat.R
import app.alfian.yukzakat.databinding.DialogImageViewerBinding
import app.alfian.yukzakat.util.loadFromBase64

/**
 * Created by Alfian on 6/23/2023.
 */

class ImageViewerDialog(val ctx : Context) : Dialog(ctx) {

    companion object {
        private var base64String : String? = null
        fun instance(context : Context,
                     base64String: String) : ImageViewerDialog {
            Companion.base64String = base64String
            return ImageViewerDialog(context)
        }
    }

    lateinit var binding : DialogImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),R.layout.dialog_image_viewer,null,false)
        setContentView(binding.root)

        base64String?.also {
            binding.image.loadFromBase64(it)
        }
        setCancelable(true)
        window?.setLayout((context.resources.displayMetrics.widthPixels * 0.80).toInt(),ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}