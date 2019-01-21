package `in`.umum.model.Gempa

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "tb_gempa")
data class GempaModel(

        var id_gempa: String,
        var tgl_kejadian : String,
        var jam_kejadian : String,
        var latitude : Double,
        var longitude : Double,
        var magnitude : Double,
        var kedalaman : Double,
        var wilayah : String,
        var potensi_tsunami: String

): Serializable