<?php include ('functions.php');
$codigo=$_GET['codigo'];
$nombre=$_GET['nombre'];
$marca=$_GET['marca'];
$id=$_GET['id'];
ejecutarSQLCommand("Call ModificaArticulos('$codigo','$nombre','$marca','$id')");
if ($resultset = getSQLResultSet("Call Grilla_Productos")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
