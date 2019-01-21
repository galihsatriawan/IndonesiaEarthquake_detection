package `in`.umum.model.Vendor

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VendorResponse{
    @SerializedName("status")
    @Expose
    var status : String = ""

    @SerializedName("data")
    @Expose
    var data : List<VendorItem> = ArrayList()

    @SerializedName("message")
    @Expose
    var message : String =""

    @SerializedName("id")
    @Expose
    var id : String =""
}