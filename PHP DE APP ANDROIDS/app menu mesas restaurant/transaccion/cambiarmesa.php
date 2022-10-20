<?php
include('functions.php'); 
$mesa2=$_GET['mesa2'];
$mesa1=$_GET['mesa1'];
ejecutarSQLCommand("Call ModificarMesaReservas('$mesa2','$mesa1')");
?>
