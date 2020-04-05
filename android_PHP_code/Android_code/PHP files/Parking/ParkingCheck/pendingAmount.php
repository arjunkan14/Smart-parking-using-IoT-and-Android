<?php
 
require("../config.php");

$User = $_POST['User'];

$amount ="0";


$query = $db->prepare("UPDATE users SET  pendingAmount='$amount' WHERE email='$User'");

$data=$query->execute();

          			 if ($data) {
										 $json['message'] = 'success';
                                                                                 echo json_encode($json);
                                                                      
									    }
								        else{
								                 $json['message'] = 'error';		
                                                 				 echo json_encode($json);
								            }
										
           
?>