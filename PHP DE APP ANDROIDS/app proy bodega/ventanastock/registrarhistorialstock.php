<?php include ('functions.php');
$id=$_GET['id'];
$codigo=$_GET['codigo'];
$nombre=$_GET['nombre'];
$marca=$_GET['marca'];
$td=$_GET['td'];
$nd=$_GET['nd'];
$cant=$_GET['cant'];
$fecha=strftime("%Y-%m-%d", time() ); 
$hora=strftime("%H:%M:%S", time() );
if ($resultset = getSQLResultSet("insert into historial_entrada values('$id','$codigo','$nombre','$marca','$td','$nd','$cant','$fecha','$hora','------')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
 ?>
