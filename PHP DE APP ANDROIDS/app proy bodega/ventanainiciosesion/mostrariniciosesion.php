<?php
include('functions.php'); 
if ($resultset = getSQLResultSet("Call Visual_Grilla_Inicio")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
