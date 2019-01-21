package `in`.umum.model.Video

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable




@Entity(tableName = "tb_video")
data class VideoModel(
        @NonNull
        @PrimaryKey
        var id_video: String,
        var gambar_video: String,
        var judul_video : String,
        var creator : String

): Serializable