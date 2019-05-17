package eu.europa.ecb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eu.europa.ecb.util.LiveDataCallAdapterFactory
import eu.europa.ecb.util.LiveDataTestUtil.getValue
import eu.europa.ecb.vo.BaseCube
import eu.europa.ecb.vo.Cube
import eu.europa.ecb.vo.Envelope
import eu.europa.ecb.vo.Sender
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@RunWith(JUnit4::class)
class EcbServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: EcbService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy() // important part!
                        )))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(EcbService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun test() {
        enqueueResponse("eurofxref_daily.xml")
        val value = getValue(service.getEcb())
        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, `is`("/eurofxref-daily.xml"))
        println("result $value")
        val successResult = (value as ApiSuccessResponse).body
        Assert.assertThat<Envelope>(successResult, notNullValue())
        Assert.assertThat<BaseCube>(successResult.cube, notNullValue())
        Assert.assertThat(successResult.subject, `is`("Reference rates"))
        Assert.assertThat<Sender>(successResult.sender, notNullValue())
        Assert.assertThat(successResult.sender?.name, `is`("European Central Bank"))
        Assert.assertThat<Cube>(successResult.cube?.cube, notNullValue())
        Assert.assertThat(successResult.cube?.cube?.time, `is`("2019-05-16"))
        assert(successResult.cube?.cube?.cube?.size?.compareTo(32) == 0)
        successResult.cube?.cube?.cube?.forEach { it -> println("Item: ${it.currency} , ${it.rate}") }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }

}
