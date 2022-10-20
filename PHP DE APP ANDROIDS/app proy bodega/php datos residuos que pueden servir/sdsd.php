<?php include ('functions.php');
$nx=$_GET['nx'];
$cx=$_GET['cx'];
$consulta = ejecutarSQLCommand("Call Verifica_Usuario_Clave('$nx','$cx')");
if ($consulta){
echo 'usuaeio biem';
}
else{
echo 'usuaeio mal';
}

?>
