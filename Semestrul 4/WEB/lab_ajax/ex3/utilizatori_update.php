<?php
$pdo = new PDO("mysql:host=localhost;dbname=users", "root", "");
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$stmt = $pdo->prepare("UPDATE users SET nume = ?, prenume = ?, telefon = ?, email = ? WHERE id = ?");
$stmt->execute([
    $_POST['lastName'],
    $_POST['firstName'],
    $_POST['phone'],
    $_POST['email'],
    $_POST['id']
]);

echo "User updated successfully";
?>