<?php

   require("../config.php"); 
		
        
   $OldPass = $_POST['OldPass'];
   $NewPass = $_POST['NewPass'];
   $email= $_POST['User'];
   


                     
                        $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$email'  AND password='$OldPass' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data) {

        			 $sql = "UPDATE Users SET password='$NewPass' WHERE email= '$email'";
                                $dataa=$db->exec($sql);

                                                if($dataa){
                            
                                           $json['message'] = 'success';
                                     	   echo json_encode($json);}

                                                                            }
                                                                  else{

                                                                            $json['message'] = 'NOT AVAILABLE';
                                     						 echo json_encode($json);




				 }
                                  
                      
?>