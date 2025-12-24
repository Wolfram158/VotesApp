package ru.wolfram.common.data.security

import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.wolfram.common.domain.security.Crypto
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

internal object SerializerProvider {
    inline fun <reified T> provideSerializer(
        crypto: Crypto,
        json: Json,
        ioDispatcher: CoroutineDispatcher,
        default: T
    ): Serializer<T> {
        return object : Serializer<T> {
            override val defaultValue: T
                get() = default

            override suspend fun readFrom(input: InputStream): T {
                val encryptedBytes = withContext(ioDispatcher) {
                    input.use { it.readBytes() }
                }
                val encryptedBytesDecoded = Base64.getDecoder().decode(encryptedBytes)
                val decryptedBytes = crypto.decrypt(encryptedBytesDecoded)
                val decodedJsonString = decryptedBytes.decodeToString()
                return json.decodeFromString(decodedJsonString)
            }

            override suspend fun writeTo(t: T, output: OutputStream) {
                val jsonString = json.encodeToString(t)
                val bytes = jsonString.toByteArray()
                val encryptedBytes = crypto.encrypt(bytes)
                val encryptedBytesBase64 = Base64.getEncoder().encode(encryptedBytes)
                withContext(ioDispatcher) {
                    output.use {
                        it.write(encryptedBytesBase64)
                    }
                }
            }

        }
    }
}