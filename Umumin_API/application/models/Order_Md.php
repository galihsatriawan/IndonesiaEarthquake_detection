<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Order_Md extends CI_Model {
    public $tb = "tb_tr_order";
    public $tb_cust = "tb_mst_customer";
    public $tb_staff = "tb_mst_staff_vendor";
    public $tb_vendor = "tb_mst_vendor";
    public $tb_status = "tb_hlp_status";
    
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    public function getOrder($table,$field_id,$id,$field="*"){
//        $this->db->select("*");
//        $this->db->from($this->tb);
//        $this->db->join($this->tb_status, "{$this->tb_status}.id_status={$this->tb}.status","left");
//        $this->db->join($this->tb_cust, "{$this->tb}.telp_customer={$this->tb_cust}.telp_customer","left");
//        $this->db->join($this->tb_staff, "{$this->tb}.telp_staff={$this->tb_staff}.telp_staff","left");
//        $this->db->join($this->tb_vendor, "{$this->tb}.id_vendor={$this->tb_vendor}.id_vendor","left");
//        $this->db->where($field_id,$id);
//        $query = $this->db->get();
        //echo $this->db->last_query();
//        return $query;
        
        $sql = "SELECT
                    *
            FROM
                    `tb_tr_order` od
            LEFT JOIN `tb_hlp_status` st ON st.`id_status` = od.`status`
            LEFT JOIN `tb_mst_customer` cs ON od.`telp_customer` = cs.`telp_customer`
            LEFT JOIN `tb_mst_staff_vendor`  sv ON od.`telp_staff` = sv.`telp_staff`
            LEFT JOIN `tb_mst_vendor` vn  ON od.`id_vendor` = vn.`id_vendor`
            WHERE
                   {$table}.{$field_id} = '{$id}'";
        $query = $this->db->query($sql);
        return $query;
    }
    public function updateOrder($field,$id,$data){
        $this->db->where($field,$id);
        return $this->db->update($this->tb,$data);
        
    }
}

?>