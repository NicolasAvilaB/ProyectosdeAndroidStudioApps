<?php
include('functions.php'); 
$mesa=$_GET['mesa'];
ejecutarSQLCommand("Call BorrarReservas('$mesa')");
?>
