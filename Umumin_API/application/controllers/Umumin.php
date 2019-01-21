 <?php
    defined('BASEPATH') OR exit('No direct script access allowed');
       
    require APPPATH . 'controllers/Rest.php';
    class Umumin extends Rest {

        function __construct($config = 'rest') {
            parent::__construct($config);
            $this->load->database();
            $this->load->helper('url');
//            $this->cektoken();
        }
        static function download_page($path){
            $ch = curl_init();
        	
            curl_setopt($ch, CURLOPT_URL,$path);
            curl_setopt($ch, CURLOPT_FAILONERROR,1);
            curl_setopt($ch, CURLOPT_FOLLOWLOCATION,1);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);
            curl_setopt($ch, CURLOPT_TIMEOUT, 15);
            $retValue = curl_exec($ch);  
            curl_close($ch);
            return $retValue;
        }
        
        static function getAllData($xml) {
            $xml = (Array) new SimpleXMLElement($xml);
            return $xml['gempa'];
        }
        static function getNewGempa(){
             $terbaru = "http://data.bmkg.go.id/autogempa.xml";
                $sXML = Umumin::download_page($terbaru);
                $xml = Umumin::getAllData($sXML);
            
                $datas = Array();
                    # code...
                    $data['tgl_kejadian'] = (string)$xml->Tanggal[0];
                    $data['jam_kejadian'] = (string) $xml->Jam;
                    $loc = explode(",", $xml->point->coordinates);
            
                    $data['latitude'] = (string)$loc[0];
                    $data['longitude'] = (string)$loc[1];
                    $data['magnitude'] = (double)$xml->Magnitude;
                    $data['kedalaman'] = (double)$xml->Kedalaman;
                    $data['wilayah'] = (string)$xml->Wilayah1;
                    $data['potensi_tsunami'] = (string)$xml->Potensi;
                    $datas[] = $data;
                    return $datas;
        }
        function new_gempa_get(){
                $datas = Umumin::getNewGempa();
                $this->response(array("status"=>"success","data"=>$datas),200); 
        }
        function all_gempa_get(){
            $seluruh = "http://data.bmkg.go.id/gempaterkini.xml";
           
            $sXML = Umumin::download_page($seluruh);
            $xml = Umumin::getAllData($sXML);
            
            $datas = Array();
            foreach ($xml as $row) {
                # code...
                $data['tgl_kejadian'] = (string)$row->Tanggal[0];
                $data['jam_kejadian'] = (string) $row->Jam;
                $loc = explode(",", $row->point->coordinates);
            
                $data['latitude'] = (string)$loc[0];
                $data['longitude'] = (string)$loc[1];
                $data['magnitude'] = (double)$row->Magnitude;
                $data['kedalaman'] = (double)$row->Kedalaman;
                $data['wilayah'] = (string)$row->Wilayah;
            
                $datas[] = $data;
                 
            }
            $this->response(array("status"=>"success","data"=>$datas),200);
        }

        /* index page */
        function index_get($table = '', $id = '',$id_field='') {
            if ($table == '') {
                //redirect(base_url());
                $this->response(array('status'=>'failed','version'=>'beta'),500);
                
            } else {
                //Agar nama field id dapat fleksibel
                $get_id = $id_field==''?'id_'.$table:$id_field;
                
                if ($id == '') {
                // baseurl/?table=nama_table (semua data)
                    $data = $this->db->get($table)->result();
                    $this->response(array("data" => $data,'status'=>'success',), 200);
                } else {
                // baseurl/?table=nama_table&id=id (satu data)
                    $id = urldecode($id);
                    $this->db->where($get_id, $id);
                    $data = $this->db->get($table)->row();
                    if($data === null){
                        $this->response(array("{$get_id}"=>"-1","email"=>$id),200);
                    }else{
                        $this->response($data, 200);
                    }
                    
                }
                
            }
        }
        function index_post($table = '') { // baseurl/?table=nama_table
            $obj = $this->post();
            if (is_object($obj)) {
                $obj = (Array) $obj;
            }
        $insert = $this->db->insert($table, $obj);
           // $id = $this->db->insert_id();
            if ($insert) {
                /*$response = array(  
                    'data' => $this->post(),
                    'table' => $table,
                    'id' => $id,
                    'status' => 'success'
                    );
                 * */
                
                 $response = array(
                     'additional' => $obj['telp_customer'],
                     'status' => 'success'
                 );
                $this->response($response, 200);
            } else {
                $this->response(array('status' => 'fail', 502));
            }
        }

        function index_put($table = '', $id = '') { // baseurl/nama_table/id
            $get_id = 'id_'.$table;
            $this->db->where($get_id, $id);
            $update = $this->db->update($table, $this->put());
            if ($update) {
                $response = array(
                    'data' => $this->put(),
                    'table' => $table,
                    'id' => $id,
                    'status' => 'success'
                    );
                $this->response($response, 200);
            } else {
                $this->response(array('status' => 'fail', 502));
            }
        }

        function index_delete($table = '', $id = '') {
            $get_id = 'id_'.$table;
            $this->db->where($get_id, $id);
            $delete = $this->db->delete($table);
            if ($delete) {
                $response = array(
                    'table' => $table,
                    'id' => $id,
                    'status' => 'success'
                    );
                $this->response($response, 201);
            } else {
                $this->response(array('status' => 'fail', 502));
            }
        }
        
              /* index page */
        function tes_get($table = '', $id = '') {
            if ($table == '') {
                redirect(base_url());
            } else {
                $get_id = 'id_'.$table;
                if ($id == '') {
                // baseurl/?table=nama_table (semua data)
                    $data = $this->db->get($table)->result();
                } else {
                // baseurl/?table=nama_table&id=id (satu data)
                    $this->db->where($get_id, $id);
                    $data = $this->db->get($table)->result();
                }
                $this->response(array("data" => $data,'status'=>'success',), 200);
            }
        }
        
               /* index page */
        function video_get($id = '') {
            
                
                if ($id == '') {
                // baseurl/?table=nama_table (semua data)
                    $data = $this->db->get("tb_mst_video_tips")->result();
                } else {
                // baseurl/?table=nama_table&id=id (satu data)
                    $this->db->where("id_video", $id);
                    $data = $this->db->get("tb_mst_video_tips")->result();
                }
                $this->response(array('status'=>'success',"data" => $data,), 200);
            
        }
        function gempa_get($id = '') {
            
                
                if ($id == '') {
                // baseurl/?table=nama_table (semua data)
                    $sql = "Select * from tb_mst_gempa order by id_gempa DESC LIMIT 1";
                    $data = $this->db->query($sql)->row();
                } else {
                // baseurl/?table=nama_table&id=id (satu data)
                    $this->db->where("id_gempa", $id);
                    $data = $this->db->get("tb_mst_gempa")->result();
                }
                $this->response(array('status'=>'success',"data" => $data,), 200);
            
        }
        function cek_gempa_get(){
            $sql = "Select * from tb_mst_gempa order by id_gempa DESC LIMIT 1";
            $db = $this->db->query($sql)->row();
            $api = Umumin::getNewGempa()[0];
            $tgl1 = $db->tgl_kejadian;
            $tgl2 = $api['tgl_kejadian'];
            $jm1 =$db->jam_kejadian;
            $jm2 =$api['jam_kejadian'];
            if(!(($tgl1==$tgl2)&&($jm1==$jm2))){
                //echo "hello";              
                $api['date_created'] = date("Y-m-d H:i:s");
                $this->db->insert("tb_mst_gempa",$api);
                $magn = $api['magnitude'];
                if($magn>6){
                    $radius = 200;
                }else if ($magn>5){
                    $radius = 100;
                }else if ($magn>4) {
                    $radius = 50;
                }else{
                    $radius = 30;
                }
                $data = array(
                        "judul" => "Terjadi Gempa Bumi",        
                        "body" => $api['magnitude']." SR di ".$api['wilayah'],
                        "latitude" => $api['latitude'],
                        "longitude" => $api['longitude'],
                        "radius" => $radius,
                    );
                print_r($data);    
                Umumin::FCM($data);
            }else{
                //echo "sama";
                $respon['status'] = "tidak ada gempa baru";
		
		        die(json_encode($respon));
            }
            //echo $db->tgl_kejadian;
            //echo $api['tgl_kejadian'];
        }
        static function FCM($result){
            $title  = $result['judul'];
        $body = $result['body'];
        $lat = $result['latitude'];
        $long = $result['longitude'];
        $radius = $result['radius'];

   
        define('API_ACCESS_KEY','AAAAKUUlnqw:APA91bH-onJ3CPWCSaB_5YUe6P6oC1CXrGww5IicFrLm6HoFCkCrmQgwm6kADS_PMj1ce8i0wP8AYY9s144jfs5Y6vYeD-gN_BJcHtp9sGdZz8tIqlHHFVwNj3UWpFTn20nVs_-hcGcz');
		$url = 'https://fcm.googleapis.com/fcm/send';
		// prepare the message
		$message = array( 
		  'title'     => $title,
		  'body'      => $body
		);
		$res = array(
			'latitude'	=> $lat,
			'longitude'	=> $long,
			'radius'	=> $radius,
			'sound' => 'default'
		);
		$fields = array( 
		  'to' => '/topics/alltester', 
		  // 'data'             => $message  --> Wrong written
		  'notification'             => $message,
		  'data'	=> $res
		);
		$headers = array( 
		  'Authorization: key='.API_ACCESS_KEY, 
		  'Content-Type: application/json'
		);
//		echo json_encode($fields);
		//print_r($fields);
		$ch = curl_init();


//		echo json_encode($fields);
		curl_setopt( $ch,CURLOPT_URL,$url);
		curl_setopt( $ch,CURLOPT_POST,true);
		curl_setopt( $ch,CURLOPT_HTTPHEADER,$headers);
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER,true);
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER,false);
		curl_setopt( $ch,CURLOPT_POSTFIELDS,json_encode($fields));
		
		$result = curl_exec($ch);

		curl_close($ch);
		
        }
        
    }
    ?>