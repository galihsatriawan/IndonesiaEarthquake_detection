package `in`.umum.model.OrderModel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tb_order")
data class OrderItem(

		@NonNull
		@PrimaryKey
	    var id_order: String,
	    var token_customer: String? = null,
	    var alamat_customer: String? = null,
	    var harga_total: Int = -1,
	@SerializedName("is_taken")
	    var taken: String? = null,
	    var harga_transfer: String? = null,
	    var rating: String? = null,
	    var tgl_bayar: String? = null,
	    var latitude_alamat: Double? = null,
	    var saran: String? = null,
	var longtitude_alamat: Double? = null,
	    var telp_vendor: String? = null,
	    var harga_fixed: Long? = null,
	    var title_order: String? = null,
	    var batas_waktu_pembayaran: String? = null,
	    var telp_customer: String? = null,
	    var image_order: String? = null,
	    var token_vendor: String? = null,
	    var token_staff: String? = null,
	    var id_service: Int? = null,
	    var id_vendor: Long? = null,
	    var status: String? = null,
	    var detail_deskripsi_order: String? = null,
	    var urutan_harga: String? = null,
	    var sudah_upload: String? = null,
	    var tgl_selesai: String? = null,
	    var id_staff: String? = null,
	    var point: Int? = null,
	    var harga_jasa: Long? = null,
	    var waktu_selesai_garansi: String? = null,

	    var catatan_tambahan: String? = null,
	    var foto_pembayaran: String? = null,
	    var sudah_bayar: String? = null,
	    var alamat_detail_order: String? = null,
	    var telp_staff: String? = null,
	
	    var tgl_selesai_close: String? = null,
	    var tgl_order_dibuat: String? = null,
	    var id_customer: String? = null,
	    var kategori: String? = null,
	@SerializedName("is_deal")
	    var deal: String? = null,

	    var nama_customer: String? = null,
	@SerializedName("is_aktif")
	    var aktif: String? = null,
	    var jenis_status: String? = null,
	    var nama_vendor: String? = null,
	    var nama_staff: String? = null,
	
	    var id_status: String? = null,
	    var email_vendor: String? = null,
	    var deskripsi_vendor: String? = null,
	    var foto_staff: String? = null
): Serializable
