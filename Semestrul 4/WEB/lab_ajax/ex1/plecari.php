<?php
$pdo = new PDO('mysql:host=localhost;dbname=trains', 'root', '');
$result = $pdo->query("SELECT DISTINCT departure FROM routes");
while ($row = $result->fetch()) {
    echo "<option value='{$row['departure']}'>{$row['departure']}</option>";
}
?>