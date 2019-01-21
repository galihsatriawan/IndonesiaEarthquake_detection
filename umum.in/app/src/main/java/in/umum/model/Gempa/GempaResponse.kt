package `in`.umum.model.Gempa


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GempaResponse{
    @SerializedName("status")
    @Expose
    var status : String = ""

    @SerializedName("data")
    @Expose
    var data : List<GempaModel> = ArrayList()

    @SerializedName("message")
    @Expose
    var message : String =""

}