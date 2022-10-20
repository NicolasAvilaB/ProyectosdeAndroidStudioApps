<?php
include('functions.php'); 
if ($resultset = getSQLResultSet("select * from validar_articulos")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
