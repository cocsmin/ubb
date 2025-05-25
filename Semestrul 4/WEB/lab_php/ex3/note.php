<?php
$pdo = new PDO("mysql:host=localhost;dbname=catalog", "root", "");
$note = $pdo->query("
  SELECT s.nume AS student, d.nume AS disciplina, n.nota
  FROM note n
  JOIN studenti s ON s.id = n.id_student
  JOIN discipline d ON d.id = n.id_disciplina
  ORDER BY s.nume, d.nume
")->fetchAll();
?>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Note Studenti</title>
  <link rel="stylesheet" href="ex3.css">
</head>
<body>
  <h1>Note Vizibile Public</h1>
  <table>
    <tr>
      <th>Student</th><th>Disciplina</th><th>Nota</th>
    </tr>
    <?php foreach ($note as $n): ?>
      <tr>
        <td><?= htmlspecialchars($n['student']) ?></td>
        <td><?= htmlspecialchars($n['disciplina']) ?></td>
        <td><?= $n['nota'] ?></td>
      </tr>
    <?php endforeach; ?>
  </table>
</body>
</html>