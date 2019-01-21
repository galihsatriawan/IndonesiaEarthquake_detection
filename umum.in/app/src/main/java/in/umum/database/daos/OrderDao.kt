package `in`.umum.database.daos


import `in`.umum.model.OrderModel.OrderItem
import android.arch.persistence.room.*

@Dao // Data Access Object
interface OrderDao{
    // We can write sql queries directly
    @Query("SELECT * FROM tb_order where telp_customer = :phone")
    fun getOrder(phone : String) : OrderItem
    @Insert
    fun insertOrder(order:OrderItem)
    @Update
    fun updateOrder(order:OrderItem)
}