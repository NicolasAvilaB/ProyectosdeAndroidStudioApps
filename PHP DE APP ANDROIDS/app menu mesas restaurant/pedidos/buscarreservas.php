<?php
include('functions.php'); 
$a=$_GET['a'];
if ($resultset = getSQLResultSet("Call Buscar_Reservas('$a')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);    	
    	}
   }
?>
