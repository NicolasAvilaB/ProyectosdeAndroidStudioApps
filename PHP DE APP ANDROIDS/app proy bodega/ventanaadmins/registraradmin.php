<?php include ('functions.php');
$id=$_GET['id'];
$alias=$_GET['alias'];
$clave=$_GET['clave'];
ejecutarSQLCommand("Call Ingreso_Alias('$id','$alias','$clave')");
if ($resultset = getSQLResultSet("Call Grilla_Acceso")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
