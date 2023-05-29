<?php
//connexion 
require ('dbconnexion.php'); 
//variables
 

$name = $_POST["prenom"];
$lastname = $_POST["nom"];
$email = $_POST["email"];
$password = password_hash($_POST["password"],PASSWORD_DEFAULT);
$phone = $_POST["telephone"]; 
$profile = "etudiant";
$sql = "select * from utilisateurs where email like '".$email."';";

//execute the query 
$result = mysqli_query($con,$sql);
 
$response = array();
if ((mysqli_num_rows($result)>0))
{
	$code = "reg_failed";
	$message = "E-mail existant !";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
	
}

else 
{
$sql = "insert into utilisateurs values ('','".$name."','".$lastname."','".$email."','".$phone."','".$password."','".$profile."');";	
	 
 

	
	if(mysqli_query($con,$sql)){
	$code = "reg_success";
	$message = "merci pour votre inscription";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode($response);
}else{
  echo mysqli_error($con);
}
}


 



?>