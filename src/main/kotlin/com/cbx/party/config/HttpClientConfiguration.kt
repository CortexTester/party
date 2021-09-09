package com.cbx.party.config

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import kotlin.jvm.Throws

@Configuration
class HttpClientConfiguration(val cbxProperties: CbxProperties) {
    @Bean()
    fun httpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("CBX_API_KEY", cbxProperties.partyApiKey) // <-- this is the important line

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        client.connectTimeout(30, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }
}
