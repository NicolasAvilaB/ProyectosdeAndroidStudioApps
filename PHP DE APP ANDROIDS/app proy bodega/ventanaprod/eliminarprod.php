<?php include ('functions.php');
$codigo=$_GET['codigo'];
ejecutarSQLCommand("Call EliminarProducto('$codigo')");
if ($resultset = getSQLResultSet("Call Grilla_Productos")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
