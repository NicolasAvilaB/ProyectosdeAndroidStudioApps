<?php
include('functions.php'); 
if ($resultset = getSQLResultSet("Call Grilla_Historial_Egreso")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
