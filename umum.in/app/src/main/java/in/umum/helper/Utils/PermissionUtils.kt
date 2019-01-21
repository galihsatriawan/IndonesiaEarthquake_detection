package `in`.umum.helper.Utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

import java.util.ArrayList

object PermissionUtils {

    val STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    /* Permission required for application */
    val PERMISSION_ALL = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    fun goToPermissionSettingScreen(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun isAllPermissionGranted(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PERMISSION_ALL
            if (permission.size == 0) return false
            for (s in permission) {
                if (ActivityCompat.checkSelfPermission(activity, s) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun getDeniedPermission(act: Activity): Array<String> {
        val permissions = ArrayList<String>()
        for (i in PERMISSION_ALL.indices) {
            var status = 0
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                status = act.checkSelfPermission(PERMISSION_ALL[i])
            }
            if (status != PackageManager.PERMISSION_GRANTED) {
                permissions.add(PERMISSION_ALL[i])
            }
        }

        return permissions.toTypedArray()
    }


    fun isGranted(act: Activity, permission: String): Boolean {
        if (!Tools.needRequestPermission()) return true
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            act.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        } else false
    }

    fun isStorageGranted(act: Activity): Boolean {
        return isGranted(act, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun showSystemDialogPermission(fragment: Fragment, perm: String) {
        fragment.requestPermissions(arrayOf(perm), 200)
    }

    fun showSystemDialogPermission(act: Activity, perm: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            act.requestPermissions(arrayOf(perm), 200)
        }
    }

    fun showSystemDialogPermission(act: Activity, perm: String, code: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            act.requestPermissions(arrayOf(perm), code)
        }
    }
}