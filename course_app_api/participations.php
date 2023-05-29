<?php
//connexion 
require ('dbconnexion.php'); 
//variables
 
 
$client_id = $_POST["client_id"];
$formation_id = $_POST["formation_id"];  
 
$sql = "select * from participations where client_id = '$client_id' and formation_id = '$formation_id';";

//execute the query 
$result = mysqli_query($con,$sql);
 
$response = array();
if ((mysqli_num_rows($result)>0))
{
	echo "Vous été déjà inscri dans cette événement";
	
}

else 
{
$sql = "insert into participations values ('','$client_id','$formation_id');";		
	if(mysqli_query($con,$sql)){
	echo "Merci pour votre participation";
}else{
  echo mysqli_error($con);
}
}


 



?>