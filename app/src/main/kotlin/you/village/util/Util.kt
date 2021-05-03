package you.village.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

object Util {
    fun checkGpsService(activity: Activity) {
        val manager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(activity)
                .setTitle("위치 서비스 설정")
                .setMessage("위치 서비스 기능(GPS)을 설정해주세요.")
                .setPositiveButton("확인") { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    activity.startActivity(intent)
                }
                .setCancelable(false)
                .show()
        }
    }

    fun requestGpsPermission(activity: Activity) {
        if (PermissionChecker.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_DENIED &&
            PermissionChecker.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PermissionChecker.PERMISSION_DENIED
        )
            AlertDialog.Builder(activity)
                .setTitle("위치 접근 권한")
                .setMessage("현재 위치를 파악하기 위해 위치 접근 권힌이 필요합니다.")
                .setPositiveButton("확인") { _, _ ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        1000
                    )
                }
                .setCancelable(false)
                .show()
    }
}
