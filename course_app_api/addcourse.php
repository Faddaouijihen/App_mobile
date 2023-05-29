<?php
//connexion 
require ('dbconnexion.php'); 
//variables
 
$titre = $_POST["titre"];
$description = $_POST["description"];
$nbr_heure = $_POST["nbr_heure"];
$date = $_POST["date"];
$formation = $_POST["formation"]; 
 

 
$sql = "insert into formations values('','".$titre."','".$description."','".$nbr_heure."','".$date."','".$formation."');";
if (mysqli_query($con,$sql))
{
	echo $titre . " ajouté avec succes";
	
}
else
{
	echo mysqli_error($con);
}
mysqli_close($con);
?>