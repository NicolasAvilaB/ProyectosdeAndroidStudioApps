<?php
include('functions.php'); 
$usuario=$_GET['usuario'];
$producto=$_GET['producto'];
$precio=$_GET['precio'];
$cantidad=$_GET['cantidad'];
$tipo=$_GET['tipo'];
$id=$_GET['id'];
ejecutarSQLCommand("Call Modificar_Pedido('$usuario','$producto','$precio','$cantidad','$tipo','$id')");
?>
