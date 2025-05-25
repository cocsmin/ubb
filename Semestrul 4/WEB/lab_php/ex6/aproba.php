<?php
session_start();
if (!isset($_SESSION["admin"])) exit;
$conn = new mysqli("localhost", "root", "", "retete");
$id = (int)$_POST["id"];
$conn->query("UPDATE comentarii SET status='aprobat' WHERE id=$id");
header("Location: moderate.php");