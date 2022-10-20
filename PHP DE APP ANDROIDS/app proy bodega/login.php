<?php
include('functions.php'); 
$nx=$_GET['nx'];
$cx=$_GET['cx'];
if ($resultset = getSQLResultSet("Call Verifica_Usuario_Clave('$nx','$cx')")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
		
    	
    	}
    	
   }
   
?>
