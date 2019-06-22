package apps.iloyarte.melichallenge.api

import apps.iloyarte.melichallenge.BuildConfig
import apps.iloyarte.melichallenge.models.ItemDetails
import io.reactivex.Observable
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.http.Path


interface SearchServiceAPI {


    @GET("search")
    fun search(@Query("q") query: String): Observable<SearchResponse>

    @GET("items/{id}")
    fun getDetails(@Path("id") id: String): Observable<ResponseBody>


    // TODO: inject retrofit as a dependency instead
    companion object Factory {
        fun create(): SearchServiceAPI {
            val logging = HttpLoggingInterceptor()

            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = retrofit2.Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()

            return retrofit.create(SearchServiceAPI::class.java)
        }
    }
}