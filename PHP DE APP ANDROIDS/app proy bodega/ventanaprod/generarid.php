<?php
include('functions1.php'); 
if ($resultset = getSQLResultSet("Call GeneradorIDautomatico4")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
	
    	}
    	
   }
   
?>
