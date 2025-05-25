<?php
session_start();
if (!isset($_SESSION['profesor_id'])) {
  header('Location: index.html');
  exit;
}
$pdo = new PDO("mysql:host=localhost;dbname=catalog", "root", "");
$studenti = $pdo->query("SELECT * FROM studenti")->fetchAll();
$discipline = $pdo->query("SELECT * FROM discipline")->fetchAll();
$note = $pdo->query("
  SELECT s.nume AS student, d.nume AS disciplina, n.nota, p.username AS profesor
  FROM note n
  JOIN studenti s ON s.id = n.id_student
  JOIN discipline d ON d.id = n.id_disciplina
  JOIN profesori p ON p.id = n.id_profesor
  ORDER BY s.nume, d.nume
")->fetchAll();
?>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Note</title>
  <link rel="stylesheet" href="ex3.css">
</head>
<body>
  <h1>Bine ai venit, <?= htmlspecialchars($_SESSION['username']) ?>!</h1>
  <p><a href="logout.php">Logout</a></p>

  <form method="post" action="salvare_nota.php">
    <label>Student:
      <select name="student_id" required>
        <?php foreach ($studenti as $s): ?>
          <option value="<?= $s['id'] ?>"><?= htmlspecialchars($s['nume']) ?></option>
        <?php endforeach; ?>
      </select>
    </label>

    <label>Disciplina:
      <select name="disciplina_id" required>
        <?php foreach ($discipline as $d): ?>
          <option value="<?= $d['id'] ?>"><?= htmlspecialchars($d['nume']) ?></option>
        <?php endforeach; ?>
      </select>
    </label>

    <label>Nota:
      <input type="number" name="nota" min="1" max="10" step="0.5" required>
    </label>

    <button type="submit">Salveaza</button>
  </form>

  <table>
    <tr>
      <th>Student</th><th>Disciplina</th><th>Nota</th><th>Profesor</th>
    </tr>
    <?php foreach ($note as $n): ?>
      <tr>
        <td><?= htmlspecialchars($n['student']) ?></td>
        <td><?= htmlspecialchars($n['disciplina']) ?></td>
        <td><?= $n['nota'] ?></td>
        <td><?= htmlspecialchars($n['profesor']) ?></td>
      </tr>
    <?php endforeach; ?>
  </table>
</body>
</html>