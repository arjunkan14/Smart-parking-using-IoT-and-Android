<?php

   require("../config.php"); 
		
   $email= $_POST['Email'];
    $email= "saeedshaikh9890@gmail.com";

      $subjec="Smart Parking";
$headers = implode("\r\n", $headers);
   


                     
                        $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$email' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);

                               if($data){
						        
								 foreach($data as $row) {
                                                                        $takepass=$row['password'];

        			                                 echo $takepass;
                                                           $success = mail("saeedshaikh9890@gmail.com", $subject, $body,$headers);

                                                         //$success = mail($email, $subject, $takepass, $headers);
                                                              echo $success;

                                                if($success){
                            
                                           $json['message'] = 'success';
                                     	   echo json_encode($json);}
										   else{

                                                                            $json['message'] = 'not';
                                     						 echo json_encode($json);




				 }

                                                                            }
                                     }
                            else{
                                $json['message'] = 'not';
                                     						 echo json_encode($json);
                                 }
                                                                  
                                  
                      
?>