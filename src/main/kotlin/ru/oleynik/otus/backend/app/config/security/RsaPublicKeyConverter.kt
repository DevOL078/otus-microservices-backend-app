package ru.oleynik.otus.backend.app.config.security

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.security.converter.RsaKeyConverters
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.security.interfaces.RSAPublicKey

@Component
@ConfigurationPropertiesBinding
class RsaPublicKeyConverter : Converter<String, RSAPublicKey> {
    override fun convert(source: String): RSAPublicKey? {
        return RsaKeyConverters.x509().convert(ByteArrayInputStream(source.toByteArray()))
    }
}