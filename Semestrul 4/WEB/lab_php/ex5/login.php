<?php
session_start();
$pdo = new PDO("mysql:host=localhost;dbname=photoapp", "root", "");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  $stmt = $pdo->prepare("SELECT * FROM utilizatori WHERE username = ? AND parola = ?");
  $stmt->execute([$_POST['username'], $_POST['parola']]);
  $user = $stmt->fetch();

  if ($user) {
    $_SESSION['user_id'] = $user['id'];
    $_SESSION['username'] = $user['username'];
    header("Location: profil.php");
  } else {
    echo "Autentificare eșuata.";
  }
}