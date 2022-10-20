<?php
header("Content-Type: application/json; charset=UTF-8");
header("Content-Type: text/html;charset=utf-8");

include('functions.php'); 
$producto = $_REQUEST['producto'];
if ($resultset = getSQLResultSet("Call BusquedaAplicacion ('$producto')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	//utf8_decode($row);
    	echo json_encode($row, JSON_UNESCAPED_UNICODE);
        //para ver problemas igual utilizar var_dump($row);
    	}	

   }
   
 
?>
