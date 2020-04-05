<?php


require("../config.php");


   $name = $_POST['User'];

  
   

                 try{
                       $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$name' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data) {
                                                                      foreach($data as $row){
										$json['message'] = 'success';
                                                                                 $json['wallet_amount'] = $row["pendingAmount"];									
								
										 echo json_encode($json);
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
      
