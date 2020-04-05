<?php




 
require("../config.php");


$SlotNo = $_POST['SlotNo'];

$exit_time =$_POST[exit_time];




$status="available";



                      $query = $db->prepare("UPDATE parkingSlot  SET  status='$status',exit_time='$exit_time' WHERE slot_no='$SlotNo'");

                             $data=$query->execute();

                        $queryy = $db->prepare("SELECT * FROM parkingslot WHERE slot_no='$SlotNo'" );
	                 $queryy->execute();

 

                             if($queryy->rowCount() > 0){
		             $dataa = $queryy->fetchAll(PDO::FETCH_ASSOC);

								 if ($data) {
										 $json['message'] = 'success';
                                                                                   echo json_encode($json);

                                                         foreach($dataa as $row)
                                                                      {
                                                                        $user=$row['user_id'];
                                                                        // echo $user;
                                                                                $timee=$row['time'];
                                                                                 $exit_timee=$row['exit_time'];
                                                                
                                                             
                   
 
                                                                  $ts1 = strtotime($timee);
                                                                  $ts2 = strtotime($exit_timee);     
                                                                  $seconds_diff = $ts2 - $ts1;                            
                                                                  $difference = ($seconds_diff/3600);
                                                                      
                                                                        $whole = (int) $difference;
                                                                         $newwhole=$whole-1;
                                                                    $finalePrice= $newwhole*20;

                                    $query = $db->prepare("UPDATE users SET  pendingAmount='$finalePrice' WHERE email='$user'");

                                        $data=$query->execute();

                              

                                                             } 

                                                                      
									    }
								        else{
								                 $json['message'] = 'error';		
                                                                                  echo json_encode($json);
								            }
										
                                                
                                           }
                                
                 

				       

?>