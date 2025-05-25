<?php
session_start();
$conn = new mysqli("localhost", "root", "", "retete");
if ($conn->connect_error) die("Eroare conectare");

$u = $conn->real_escape_string($_POST["username"]);
$p = $conn->real_escape_string($_POST["parola"]);

$sql = "SELECT * FROM utilizatori WHERE username='$u' AND parola=SHA1('$p')";
$res = $conn->query($sql);

if ($res->num_rows === 1) {
    $_SESSION["admin"] = true;
    header("Location: moderate.php");
} else {
    header("Location: index.html");
}