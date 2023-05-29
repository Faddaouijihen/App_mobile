<?php
//connexion 
require ('dbconnexion.php'); 
$client_id = $_POST["client_id"];
 
mysqli_set_charset($con,'utf8');
$products = "select formation_id from participations where client_id = '$client_id';";
$response = array();
if ($result1 = mysqli_query($con, $products)) {     
    while ($obj = mysqli_fetch_object($result1)) {        
		$formation_id = $obj->formation_id;		
		$sql = "select * from formations where id = $formation_id;";
		$result = mysqli_query($con,$sql);
		
	while ($row = mysqli_fetch_array($result))
{
array_push($response,array("id"=>$row["id"],"titre"=>$row["titre"],"description"=>$row["description"],
							"nbr_heure"=>$row["nbr_heure"],"date"=>$row["date"],"categorie"=>$row["categorie"]));	
}

    
	
	}

 echo json_encode($response);   
mysqli_free_result($result1);
}

?>