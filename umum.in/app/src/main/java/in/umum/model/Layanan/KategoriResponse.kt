package `in`.umum.model.Layanan


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class KategoriResponse{
    @SerializedName("status")
    @Expose
    var status : String = ""

    @SerializedName("data")
    @Expose
    var data : List<Kategori> = ArrayList()

    @SerializedName("message")
    @Expose
    var message : String =""

}