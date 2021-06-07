package change.village.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import java.util.*

object Util {
    fun createUuid() = UUID.randomUUID().toString().replace("-", "")
        .substring(0..10)

    fun requestStoragePermission(activity: Activity) {
        if (PermissionChecker.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            AlertDialog.Builder(activity)
                .setTitle("저장소 접근 권한")
                .setMessage("사진들을 올리기 위해 저장소에 접근 권힌이 필요합니다.")
                .setPositiveButton("확인") { _, _ ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )
                }
                .setCancelable(false)
                .show()
        }
    }
}
