<?php
include('functions.php'); 
if ($resultset = getSQLResultSet("Call ConsultaUsuario")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
