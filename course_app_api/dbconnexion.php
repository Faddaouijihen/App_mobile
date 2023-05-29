<?php 
$db_host = 'localhost';
$db_user = 'root';
$db_password = '';
$db_name= 'coursemanager';
$con = mysqli_connect($db_host,$db_user,$db_password,$db_name);

if(!$con)
{
    echo mysqli_connect_error();
    exit;
}