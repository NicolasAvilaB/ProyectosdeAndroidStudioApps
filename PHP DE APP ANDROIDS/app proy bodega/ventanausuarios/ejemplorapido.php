<?php 
$conexion = new PDO("mysql:host=localhost;dbname=bodega","root","root");
$consulta = $conexion->query("");
$datos=array();
foreach ($consulta as $row)
{
$datos[]=$row;
}
echo json_encode($datos);
?>
