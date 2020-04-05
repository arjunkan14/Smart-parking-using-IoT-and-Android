<?php


require("../config.php");

  // $user_id=$_POST['User'];

   $status ="open";
    


          $query = $db->prepare("SELECT status,user_id FROM parkingschedule WHERE status='$status'  ORDER BY id DESC" );
	  $query->execute();

 

             if($query->rowCount() > 0){
		$data = $query->fetchAll(PDO::FETCH_ASSOC);

                  $json['status']='open';

                echo json_encode($json); 

                  
            	        }
               else{
                $json['status']='close';

                echo json_encode($json);  
       
		//$json['success'] = 0;
	//	$json['message'] = 'close';		
	//	$json['myintro'] = '';

		//echo json_encode($json);
	    }
?>