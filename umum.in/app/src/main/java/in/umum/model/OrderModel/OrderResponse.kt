package `in`.umum.model.OrderModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderResponse{
    @SerializedName("status")
    @Expose
    var status : String = ""

    @SerializedName("data")
    @Expose
    var data : List<OrderItem> = ArrayList()

    @SerializedName("message")
    @Expose
    var message : String =""

    @SerializedName("id")
    @Expose
    var id : String =""
}