
<?php



require("../config.php");


   $User = $_POST['User'];
   $status = "open";

   

                 try{
           
                                   
                              $sql = "UPDATE parkingschedule SET status='$status'";
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
                  catch(PDOException $e)
                              {
                                  echo  "<br>" . $e->getMessage();
                          }			
        
         $db=null;


   ?>     