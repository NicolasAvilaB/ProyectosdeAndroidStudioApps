<?php
include('functions.php'); 
$codigo = $_GET['codigo'];
$producto = $_GET['producto'];
if ($resultset = getSQLResultSet("Call Validar_Codigo_Nombre_Producto ('$codigo','$producto')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
