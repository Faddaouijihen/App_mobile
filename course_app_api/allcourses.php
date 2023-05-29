<?php 
require ('dbconnexion.php'); 
$sql = "select * from formations;";
$result = mysqli_query($con,$sql);
if($result === false){
    echo mysqli_error($con);
    exit;
}else{
$operations = mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($operations);    
}