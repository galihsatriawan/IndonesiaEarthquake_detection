package `in`.umum.Model

import java.io.Serializable

/**
 * Created by root on 9/24/18.
 */
class LayananItem(

    var id_kategori : Int? = null,

    var nama_kategori : String? = null,

    var id_service : Int? = null,
    var jenis_service: String? = null,
    var icon_service: String? = null

) : Serializable
