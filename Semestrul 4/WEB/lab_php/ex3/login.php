<?php
session_start();
$pdo = new PDO("mysql:host=localhost;dbname=catalog", "root", "");
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$stmt = $pdo->prepare("SELECT * FROM profesori WHERE username = ? AND parola = ?");
$stmt->execute([$_POST['username'], $_POST['parola']]);
$prof = $stmt->fetch();

if ($prof) {
    $_SESSION['profesor_id'] = $prof['id'];
    $_SESSION['username'] = $prof['username'];
    header('Location: notare.php');
} else {
    echo "Date incorecte.";
}