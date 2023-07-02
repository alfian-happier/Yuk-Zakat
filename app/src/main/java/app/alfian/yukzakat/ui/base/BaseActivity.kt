package app.alfian.yukzakat.ui.base

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import app.alfian.yukzakat.YukZakatApplication
import app.alfian.yukzakat.di.module.ScreenModule
import app.alfian.yukzakat.util.addTo
import app.alfian.yukzakat.util.encodeToBase64
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.fondesa.kpermissions.rx2.observe
import com.google.firebase.firestore.FirebaseFirestore
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Alfian on 6/13/2023.
 */

abstract class BaseActivity : AppCompatActivity(),PermissionListener,ImagePickerListener{

    val screenComponent by lazy {
        (application as YukZakatApplication).component.plus(ScreenModule(this))
    }

    val db by lazy {
        FirebaseFirestore.getInstance()
    }

    private val launcher = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful){
            val filePath = result.getUriFilePath(this)
            filePath?.also { filePath ->
                val uiScope = CoroutineScope(Dispatchers.IO + Job())
                uiScope.launch {
                    val currentFile = File(filePath)
                    val compressedImage = Compressor.compress(this@BaseActivity, currentFile){
                        resolution(300,300)
                        quality(80)
                        format(Bitmap.CompressFormat.WEBP)
                        size(102_400) //100KB

                    }
                    val imageUri = Uri.fromFile(compressedImage)
                    runOnUiThread {
                        imageUri?.let { resultUri ->
                            val imageStream = contentResolver.openInputStream(resultUri)
                            val selectedImage = BitmapFactory.decodeStream(imageStream)
                            onImagePicked(selectedImage.encodeToBase64())
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission(permissionsBuilder(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE).build())
        setup()
        observe()
    }

    private fun requestPermission(request : PermissionRequest){
        if (request.checkStatus().allGranted()){
            onPermissionResult(request.checkStatus())
        } else {
            request.observe().subscribe {
                onPermissionResult(it)
            }.addTo(CompositeDisposable())
            request.send()
        }
    }

    open fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
    }

    override fun onPermissionResult(result: List<PermissionStatus>) {

    }

    protected fun pickImage(){
        launcher.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = true,
                    imageSourceIncludeGallery = true,
                    guidelines = CropImageView.Guidelines.ON,
                    outputCompressFormat = Bitmap.CompressFormat.PNG,
                    outputCompressQuality = 100,
                    outputRequestSizeOptions = CropImageView.RequestSizeOptions.RESIZE_INSIDE
                ),
            ),
        )
    }

    override fun onImagePicked(base64String: String) {}

    abstract fun setup()
    abstract fun observe()
}