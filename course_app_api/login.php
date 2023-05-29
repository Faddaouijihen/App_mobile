<?php
require ('dbconnexion.php'); 
 
 $email = $_POST['email']; 
$password = $_POST['password'];  
 
 /*
$email = 'hamza@yahoo.fr';
$password = 'secretpass'; 
 */
$sql = "select id,mot_de_passe,profile,prenom from utilisateurs where email like '".$email."';";
$result = mysqli_query($con,$sql);
$response = array();
if (mysqli_num_rows($result)>0)
{
	$row = mysqli_fetch_row($result);	 
	  
    if (password_verify($password, $row['1'])) {

        $code = "login_success";
        $message="bienvenue";
        array_push($response,array("code"=>$code,"message"=>$message,"user_id"=>$row['0'],"profile"=>$row['2'],"prenom"=>$row['3']));
	    echo json_encode($response);

          
    
    } else {
        $code = "login_failed";
        $message = "utilisateur introuvable";
        array_push($response,array("code"=>$code,"message"=>$message));
        echo json_encode($response);
    
}
} else {

        $code = "login_failed";
        $message = "utilisateur introuvable.";
        array_push($response,array("code"=>$code,"message"=>$message));
        echo json_encode($response);  


}
