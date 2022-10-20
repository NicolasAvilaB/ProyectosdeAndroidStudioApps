<?php
include('functions.php'); 
$producto = $_GET['producto'];
if ($resultset = getSQLResultSet("Call Buscador_Articulos_Nombre ('$producto')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
