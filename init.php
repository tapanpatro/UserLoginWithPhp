<?php

$host = "localhost";
$user = "tapanpatro";
$password = "Champ@123";
$dbname = "userdb";

$con = mysqli_connect($host,$user,$password,$dbname);

if(!$con){
    //die("Error in database connection".mysqli_connect_error());
}else{
    //echo "<h3> Database Connection Success...";
}



?>