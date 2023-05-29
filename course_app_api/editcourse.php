<?php
//connexion 
require ('dbconnexion.php'); 

//variables
$titre = $_POST["titre"];
$description = $_POST["description"];
$nbr_heure = $_POST["nbr_heure"];
$date = $_POST["date"];
$formation = $_POST["formation"];
$id = $_POST["id"];
 

$con->set_charset('UTF-8');
$sql = "UPDATE formations SET titre='".$titre."',
								description='".$description."',
								nbr_heure='".$nbr_heure."',
								categorie='".$formation."',
								date='".$date."' 
								WHERE id='$id';";
	
 
if(mysqli_query($con,$sql)){
echo $titre . " modifié avec succes";
}else{
  echo mysqli_error($con);
}
mysqli_close($con);


?>