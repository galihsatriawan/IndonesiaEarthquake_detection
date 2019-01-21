package `in`.umum.model.Video


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class VideoResponse{
    @SerializedName("status")
    @Expose
    var status : String = ""

    @SerializedName("data")
    @Expose
    var data : List<VideoModel> = ArrayList()

    @SerializedName("message")
    @Expose
    var message : String =""

}