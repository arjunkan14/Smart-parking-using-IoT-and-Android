<?php

	

   require("../config.php"); 
		
        
   $name = $_POST['strName'];
   $email = $_POST['strEmail'];
   $contact= $_POST['strContact'];
   $license= $_POST['strLicense'];
   $adharcard= $_POST['strAdharcard'];
   $password = $_POST['strPassword'];


   $wallet_amount="1";
  $pendingAmount="0";


                     
                        $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$email' AND password='$password' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data) {
        									 $json['message'] = 'Already';
                                     						 echo json_encode($json);
                                                                            }
                                                                  else{



			 
				 $sqll = "INSERT INTO Users(name,email,contact,license,adharcard,password,wallet_amount,pendingAmount)VALUES 
                                                        ('$name','$email','$contact','$license','$adharcard','$password','$wallet_amount','$pendingAmount')";                
                                    $Issaved=$db->exec($sqll);
				 
				 if($Issaved){
				        $json['message'] = 'success';
                                      echo json_encode($json);
				 }
                                  }
                      
?>