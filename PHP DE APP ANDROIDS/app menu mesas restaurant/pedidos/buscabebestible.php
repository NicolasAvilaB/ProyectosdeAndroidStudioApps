<?php
include('functions.php'); 
$producto=$_GET['producto'];
if ($resultset = getSQLResultSet("Call ConsultaBebestible('$producto')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);    	
    	}
   }
?>
