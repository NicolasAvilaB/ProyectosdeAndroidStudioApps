<?php include ('functions.php');
$id=$_GET['id'];
$alias=$_GET['alias'];
$clave=$_GET['clave'];
ejecutarSQLCommand("Call Ingreso_Alias('".$id."','".$alias."','".$clave."')");
 ?>
