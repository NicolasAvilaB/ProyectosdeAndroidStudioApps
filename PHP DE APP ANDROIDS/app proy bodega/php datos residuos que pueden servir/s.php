<?php include ('functions.php');

$nx = $_GET['nx'];
$cx = $_GET['cx'];
$conexion = new PDO("mysql:host=localhost;dbname=bodega","root","root");

$consulta = $conexion->query("Call Verifica_Usuario_Clave('".$nx."','".$cx."')");

//datos=array();
//foreach ($consulta as $row)
//{
//$datos[]=$row;
//}
//echo json_encode($datos);
?>
