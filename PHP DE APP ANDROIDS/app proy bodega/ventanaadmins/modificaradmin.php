<?php include ('functions.php');
$alias=$_GET['alias'];
$clave=$_GET['clave'];
$id=$_GET['id'];
ejecutarSQLCommand("Call Modifica_Seccion_Acceso('$alias','$clave','$id')");
if ($resultset = getSQLResultSet("Call Grilla_Acceso")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
