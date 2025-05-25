<?php
$pdo = new PDO('mysql:host=localhost;dbname=trains', 'root', '');
$departure = $_GET['name'] ?? '';
$stmt = $pdo->prepare("SELECT arrival FROM routes WHERE departure = ?");
$stmt->execute([$departure]);
while ($row = $stmt->fetch()) {
    echo "<option value='{$row['arrival']}'>{$row['arrival']}</option>";
}
?>