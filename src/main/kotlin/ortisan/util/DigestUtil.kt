package ortisan.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DigestUtil {
    companion object {
        fun getMD5FromString(input: String): String {
            val md: MessageDigest
            md = try {
                MessageDigest.getInstance("MD5")
            } catch (e: NoSuchAlgorithmException) {
                throw IllegalArgumentException(e)
            }
            return String(md.digest(input.encodeToByteArray()))
        }
    }
}