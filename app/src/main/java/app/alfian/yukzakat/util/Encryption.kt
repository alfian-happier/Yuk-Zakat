package app.alfian.yukzakat.util

import android.os.Build
import app.alfian.yukzakat.BuildConfig
import java.nio.charset.StandardCharsets
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Alfian on 6/14/2023.
 */

fun encrypt(strToEncrypt: String): String? {
    try {
        val iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val ivSpec = IvParameterSpec(iv)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(BuildConfig.ENCRYPT_KEY.toCharArray(), BuildConfig.ENCRYPT_SALT.toByteArray(), 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder()
                .encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)))
        } else {
            android.util.Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)),android.util.Base64.DEFAULT)
        }
    } catch (e: java.lang.Exception) {
        println("Error while encrypting: $e")
    }
    return null
}

fun decrypt(strToDecrypt: String?): String? {
    try {
        val iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val ivspec = IvParameterSpec(iv)
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(BuildConfig.ENCRYPT_KEY.toCharArray(), BuildConfig.ENCRYPT_SALT.toByteArray(), 65536, 256)
        val tmp: SecretKey = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
        } else {
            String(cipher.doFinal(android.util.Base64.decode(strToDecrypt,android.util.Base64.DEFAULT)))
        }
    } catch (e: Exception) {
        println("Error while decrypting: $e")
    }
    return null
}