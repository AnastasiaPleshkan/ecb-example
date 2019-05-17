package eu.europa.ecb.api

import androidx.lifecycle.LiveData
import eu.europa.ecb.vo.Envelope
import retrofit2.Call
import retrofit2.http.GET
/**
 * REST API access points
 */
interface EcbService {
    @GET("eurofxref-daily.xml")
    fun getEcb(): LiveData<ApiResponse<Envelope>>

    @GET("eurofxref-daily.xml")
    fun callEcb(): Call<Envelope>
}
