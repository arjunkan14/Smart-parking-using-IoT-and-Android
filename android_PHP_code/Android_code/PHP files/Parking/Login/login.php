<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);

require("../config.php");


   $UserName = $_POST['UserName'];
   $UserPassword = $_POST['UserPassword'];

     
 

                 try{
                       $queryCheck=$db->prepare("SELECT * FROM Users WHERE email='$UserName' AND password='$UserPassword' LIMIT 1");
                                $resultt=$queryCheck->execute();
								
                           $data=$queryCheck->fetchAll(PDO::FETCH_ASSOC);
						        
								 if ($data ) {
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
                                  echo $sql . "<br>" . $e->getMessage();
                                }
                             					
        
         $db=null;


   ?>     
      



