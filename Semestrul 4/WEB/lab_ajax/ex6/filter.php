<?php
$pdo = new PDO("mysql:host=localhost;dbname=magazin", "root", "");
$where = [];
$valori = [];

foreach (['producator', 'procesor', 'memorie', 'hdd', 'gpu'] as $camp) {
  if (!empty($_POST[$camp])) {
    $where[] = "$camp = ?";
    $valori[] = $_POST[$camp];
  }
}

$sql = "SELECT * FROM produse";
if ($where) {
  $sql .= " WHERE " . implode(" AND ", $where);
}

$stmt = $pdo->prepare($sql);
$stmt->execute($valori);
$produse = $stmt->fetchAll();

if ($produse) {
  echo "<ul>";
  foreach ($produse as $p) {
    echo "<li><strong>{$p['model']}</strong> - {$p['producator']} - {$p['procesor']} - {$p['memorie']} - {$p['hdd']} - {$p['gpu']}</li>";
  }
  echo "</ul>";
} else {
  echo "<p><em>Nu există produse care să corespundă filtrelor.</em></p>";
}