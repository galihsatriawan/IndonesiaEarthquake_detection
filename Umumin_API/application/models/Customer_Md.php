<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Customer_Md extends CI_Model {
    public $tb = "tb_mst_customer";
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }
    public function getAll(){
        $this->db->select('*');
        $this->db->from($this->tb);
        $query = $this->db->get();
        return $query->result();
    }
    public function getCustomer($field,$id){
        $this->db->select("*");
        $this->db->from($this->tb);
        $this->db->where($field,$id);
        $query = $this->db->get();
        return $query->row();
    }
    public function addCustomer($data){
        return $this->db->insert($this->tb,$data);
    }
    public function updateCustomer($field,$id,$data){
        $this->db->where($field,$id);
        return $this->db->update($this->tb, $data);
    }
    public function deleteCustomer($field,$id){
        $this->db->where($field, $id);
        $delete = $this->db->delete($this->tb);
        return $delete;
    }
}

?>