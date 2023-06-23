package app.alfian.yukzakat.ui.base

import com.fondesa.kpermissions.PermissionStatus

interface PermissionListener {
    fun onPermissionResult(result : List<PermissionStatus>)
}