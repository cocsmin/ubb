<?php
$pdo = new PDO("mysql:host=localhost;dbname=users", "root", "");
$page = intval($_GET['page'] ?? 1);
$limit = 3;
$offset = ($page - 1) * $limit;

$total = $pdo->query("SELECT COUNT(*) FROM users")->fetchColumn();
$stmt = $pdo->prepare("SELECT * FROM users LIMIT ? OFFSET ?");
$stmt->bindValue(1, $limit, PDO::PARAM_INT);
$stmt->bindValue(2, $offset, PDO::PARAM_INT);
$stmt->execute();

$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
echo json_encode([
  'data' => $data,
  'total' => $total
]);
?>