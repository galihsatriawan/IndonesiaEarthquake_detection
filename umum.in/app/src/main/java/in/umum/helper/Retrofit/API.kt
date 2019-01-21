package `in`.umum.helper.Retrofit

import `in`.umum.model.Gempa.GempaResponse
import `in`.umum.model.Layanan.KategoriResponse
import `in`.umum.model.OrderModel.OrderItem
import `in`.umum.model.OrderModel.OrderResponse
import `in`.umum.model.Vendor.VendorResponse
import `in`.umum.model.Video.VideoResponse
import io.reactivex.Observable
import retrofit2.http.*

interface API{





    @GET("order/od/id_order/{id}/android")
    fun getOrderById(@Path("id") telp:String) :Observable<OrderResponse>


    @GET("umumin/video")
    fun getListVideo() : Observable<VideoResponse>

    @GET("umumin/all_gempa")
    fun getListGempa() : Observable<GempaResponse>

    @GET("umumin/new_gempa")
    fun getNewGempa() : Observable<GempaResponse>
}