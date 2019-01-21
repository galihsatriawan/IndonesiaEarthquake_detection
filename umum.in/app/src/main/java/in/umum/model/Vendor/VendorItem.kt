package `in`.umum.model.Vendor

import com.google.gson.annotations.Expose

/**
 * Created by root on 10/6/18.
 */
data class VendorItem(

    @Expose
    var id_vendor : Long? = null,

    @Expose
    var token_vendor : String? = null,

    @Expose
    var telp_vendor : String? = null,

    @Expose
    var nama_vendor : String? = null,

    @Expose
    var deskripsi_vendor : String? = null,

    var jarak : Double = 0.0,

    @Expose
    var rating : Double? = null,

    @Expose
    var longtitude_vendor : Double? = null,

    @Expose
    var latitude_vendor : Double? = null,

    @Expose
    var logo_vendor : String? = null


//
//    constructor(id : Int,nama:String,deskrip: String,jarak: Double,rating:Double,){
//        this.id = id
//        this.nama = nama
//        this.deskripsi = deskrip
//        this.jarak = jarak
//        this.rating = rating
//    }
)