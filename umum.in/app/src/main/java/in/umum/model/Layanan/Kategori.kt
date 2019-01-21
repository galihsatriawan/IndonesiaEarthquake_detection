package `in`.umum.model.Layanan

import `in`.umum.Model.LayananItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Kategori{
    @SerializedName("id_kategori")
    @Expose
    var id_kategori : Int? = null
    @SerializedName("nama_kategori")
    @Expose
    var nama_kategori : String? = null

    @SerializedName("servis")
    @Expose
    var list_layanan:List<LayananItem>? = null

}