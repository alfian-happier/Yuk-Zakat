package app.alfian.yukzakat.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by Zharfan on 6/13/2023.
 */

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams
}