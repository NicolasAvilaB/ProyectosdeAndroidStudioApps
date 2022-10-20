<?php
include('functions.php'); 
$alias = $_GET['alias'];
if ($resultset = getSQLResultSet("Call Busque_Alias ('$alias')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	}	
   }
?>
