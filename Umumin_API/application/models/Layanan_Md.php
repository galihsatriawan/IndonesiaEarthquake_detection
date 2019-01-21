<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Layanan_Md extends CI_Model {
    public $tb_service = "tb_mst_services";
    public $tb_kategori = "tb_mst_kategori_service";
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    public function getAll($tb,$field="*"){
        $this->db->select($field);
        $this->db->from($tb);
        $query = $this->db->get();
        return $query;
    }
    /*
     * Untuk mengambil satu data dari suatu table
     */
    public function getOne($tb,$id,$field="*"){
        $this->db->select($field);
        $this->db->from($tb);
        $query = $this->db->get();
        return $query;
    }


    public function getKategoriWithService(){
        $q = "SELECT
                        kt.id_kategori,
                        kt.nama_kategori,
                        sv.id_service,
                        sv.jenis_service,
                        sv.icon_service
                    FROM
                        tb_mst_kategori_service kt
                    LEFT JOIN tb_mst_services sv ON kt.id_kategori = sv.id_kategori";
            $query = $this->db->query($q);
            return $query;
    }
}

?>