<?php



require("../config.php");


   $User = $_POST['User'];
   $amount = $_POST['Amount'];





                 try{
                       $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$User' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data) {
                                                                      foreach($data as $row){
										
                                                                                 $oldamount= $row["wallet_amount"];
                                 
                                                                        $newAmount=$oldamount+$amount;
                                   

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
        
         $db=null;


   ?>     