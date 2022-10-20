<?php
include('functions.php'); 
$user=$_GET['user'];
$producto=$_GET['producto'];
$precio=$_GET['precio'];
$tipo=$_GET['tipo'];
$mesa=$_GET['mesa'];
$caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; //posibles caracteres a usar
$numerodeletras=10; //numero de letras para generar el texto
$cadena = ""; //variable para almacenar la cadena generada
for($i=0;$i<$numerodeletras;$i++)
{
$cadena .= substr($caracteres,rand(0,strlen($caracteres)),1); /*Extraemos 1 caracter de los caracteres 
entre el rango 0 a Numero de letras que tiene la cadena */
}
ejecutarSQLCommand("Call RegistrarReserva('$cadena','$user','$producto','$precio','1','$tipo','$mesa')");
?>
