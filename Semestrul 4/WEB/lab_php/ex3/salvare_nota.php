<?php
session_start();
if (!isset($_SESSION['profesor_id'])) {
  header('Location: index.html');
  exit;
}
$pdo = new PDO("mysql:host=localhost;dbname=catalog", "root", "");
$stmt = $pdo->prepare("
  INSERT INTO note (id_student, id_disciplina, id_profesor, nota)
  VALUES (?, ?, ?, ?)
  ON DUPLICATE KEY UPDATE nota = VALUES(nota), id_profesor = VALUES(id_profesor)
");
$stmt->execute([
  $_POST['student_id'],
  $_POST['disciplina_id'],
  $_SESSION['profesor_id'],
  $_POST['nota']
]);
header('Location: notare.php');