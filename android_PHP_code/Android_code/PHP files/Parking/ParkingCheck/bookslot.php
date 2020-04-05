<?php
 
require("../config.php");

$User = $_POST['User'];

$SlotNo = $_POST['SlotNo'];

$strCarNumber = $_POST['strCarNumber'];

$strLicenceNo = $_POST['strLicenceNo'];

$strInTime = $_POST['strInTime'];

$strAmount = $_POST['strAmount'];

$status="no";



$query = $db->prepare("UPDATE parkingSlot  SET  status='$status',user_id='$User',time=NOW(),in_time='$strInTime' WHERE slot_no='$SlotNo'");

$data=$query->execute();

           if($data){


                  $sqll = "INSERT INTO parkingschedule(user_id,car_no,license_no,in_time,amount)VALUES 
                                                       
                                              ('$User','$strCarNumber','$strLicenceNo','$strInTime','$strAmount')";                
                                   
                                    $Issaved=$db->exec($sqll);
				 
				 if($Issaved){

                              try{
                       $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$User' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data) {
                                                                      foreach($data as $row){
										
                                                                                 $oldamount= $row["wallet_amount"];
                                 
                                                                        $newAmount=$oldamount-$strAmount;
                                   

                              $sql = "UPDATE Users SET wallet_amount='$newAmount' WHERE email= '$User'";
                             $data=$db->exec($sql);

                                       
								 if ($data) {
										 $json['message'] = 'success';
                                                                                 echo json_encode($json);
                                                                      
									    }
								        else{
								                 $json['message'] = 'error';		
                                                 				 echo json_encode($json);
								            }
										
                                                                      }
											}
								        else{
								                 $json['message'] = 'error';		
                                                 echo json_encode($json);
								            }
                                   
                                
                    }  
                  catch(PDOException $e)
                              {
                                  echo  "<br>" . $e->getMessage();
                          }			

				       
                                             }


            
                 }

	

?>