<?php
include('functions.php'); 
$user = $_GET['user'];
if ($resultset = getSQLResultSet("Call BuscarNombreIngreso ('$user')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
