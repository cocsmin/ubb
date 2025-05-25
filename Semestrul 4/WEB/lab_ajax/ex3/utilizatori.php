<?php
$pdo = new PDO("mysql:host=localhost;dbname=users", "root", "");
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$query = $pdo->query("SELECT id, nume AS lastName, prenume AS firstName, telefon AS phone, email FROM users");
$data = $query->fetchAll(PDO::FETCH_ASSOC);

header('Content-Type: application/json');
echo json_encode($data);
?>