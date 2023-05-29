<?php
//connexion 
require ('dbconnexion.php'); 
//variables
 
$id = $_POST["id"];
 
$sql = "delete from formations where id = '$id';";
if (mysqli_query($con,$sql))
{
	echo "Suppression effectuée avec succès";
	
}
else
{
	echo "Erreur de suppression! Merci d'essayer une autre fois";
}
mysqli_close($con);
?>