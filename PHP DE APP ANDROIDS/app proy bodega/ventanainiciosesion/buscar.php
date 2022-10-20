<?php
header("Content-Type: application/json; charset=UTF-8");
header("Content-Type: text/html;charset=utf-8");

include('functions.php'); 
$user = $_REQUEST['user'];
if ($resultset = getSQLResultSet("Call Buscar_Nombre_Inicio ('$user')")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
        //utf8_decode($row);
    	echo json_encode($row, JSON_UNESCAPED_UNICODE);
        //conoce errores var_dump($row);
    	}	
   }
?>
