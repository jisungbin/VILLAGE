package you.village.util

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import kotlin.experimental.and

object EncryptUtil {
    sealed class EncryptType {
        object MD5 : EncryptType()
        object SHA256 : EncryptType()
    }

    fun encrypt(type: EncryptType = EncryptType.MD5, message: String): String {
        return try {
            val md = MessageDigest.getInstance(if (type == EncryptType.MD5) "MD5" else "SHA-256")
            md.update(message.toByteArray(StandardCharsets.UTF_8))
            val byteData = md.digest()
            val sb = StringBuilder()
            for (byteDatum in byteData) {
                sb.append(
                    ((byteDatum and 0xff.toByte()) + 0x100).toString(16).substring(1)
                )
            }
            with(sb.toString()) {
                if (length > 10) this.substring(0..10)
                else this
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            message
        }
    }
}
