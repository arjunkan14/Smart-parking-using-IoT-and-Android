<?php

require("../config.php");

   //$user_id=$_POST['user_id'];
 
        $query = $db->prepare("SELECT * FROM parkingSlot" );
	$query->execute();

             if($query->rowCount() > 0){
		$data = $query->fetchAll(PDO::FETCH_ASSOC);

                  echo json_encode($data);  
             
      
	        }else{
		$json['success'] = 0;
		$json['message'] = 'No Data found';		
		$json['myintro'] = '';

		echo json_encode($json);
	    }
    
?>