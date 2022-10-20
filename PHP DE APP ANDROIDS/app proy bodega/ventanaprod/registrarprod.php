<?php include ('functions.php');
$id=$_GET['id'];
$codigo=$_GET['codigo'];
$nombre=$_GET['nombre'];
$marca=$_GET['marca'];
ejecutarSQLCommand("Call Ingreso_Articulo('$id','$codigo','$nombre','$marca')");
if ($resultset = getSQLResultSet("Call Grilla_Productos")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
