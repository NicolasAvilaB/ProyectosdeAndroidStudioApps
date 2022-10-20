<?php
include('functions.php'); 
$mesa=$_GET['mesa'];
if ($resultset = getSQLResultSet("Call MostrarReservas('$mesa')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);    	
    	}
   }
?>
