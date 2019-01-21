package `in`.umum.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Messages{
    @SerializedName ("status")
    @Expose
    var status:String? = null
    @SerializedName ("additional")
    @Expose
    var additional:String? = null
}