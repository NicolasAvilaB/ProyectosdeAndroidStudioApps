<?php include ('functions.php');
$alias=$_GET['alias'];
ejecutarSQLCommand("Call Eliminar_Alias('$alias')");
if ($resultset = getSQLResultSet("Call Grilla_Acceso")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
