<?php
include('functions.php'); 
$producto = $_GET['producto'];
if ($resultset = getSQLResultSet("select * from validar_articulos where Nombre_Producto = '$producto'")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
