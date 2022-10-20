<?php
header("Content-Type: application/json; charset=UTF-8");
header("Content-Type: text/html;charset=utf-8");
include('functions.php'); 
if ($resultset = getSQLResultSet("Call MostrarGrillaRegistrosFichas")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row,  JSON_UNESCAPED_UNICODE);
    	}	
   }
?>
