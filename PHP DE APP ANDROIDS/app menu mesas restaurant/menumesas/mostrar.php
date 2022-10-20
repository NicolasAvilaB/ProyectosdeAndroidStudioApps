<?php
include('functions.php'); 
if ($resultset = getSQLResultSet("Call MostrarMesas")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
