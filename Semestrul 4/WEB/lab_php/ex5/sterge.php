<?php
session_start();
if (!isset($_SESSION['user_id'])) exit;

$pdo = new PDO("mysql:host=localhost;dbname=photoapp", "root", "");
$stmt = $pdo->prepare("SELECT * FROM poze WHERE id = ? AND id_utilizator = ?");
$stmt->execute([$_POST['id'], $_SESSION['user_id']]);
$poza = $stmt->fetch();

if ($poza) {
  unlink('uploads/' . $poza['fisier']);
  $pdo->prepare("DELETE FROM poze WHERE id = ?")->execute([$poza['id']]);
}

header('Location: profil.php');