<?php


require("../config.php");

   $user_id=$_POST['User'];


    


          $query = $db->prepare("SELECT * FROM parkingschedule WHERE user_id='$user_id' ORDER BY id DESC" );
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