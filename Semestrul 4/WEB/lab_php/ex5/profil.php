<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("Location: index.html");
    exit;
}

$pdo = new PDO("mysql:host=localhost;dbname=photoapp;charset=utf8", "root", "");

$uploadDir = __DIR__ . DIRECTORY_SEPARATOR . 'uploads';

if (!is_dir($uploadDir)) {
    if (!mkdir($uploadDir, 0755, true)) {
        die("Eroare: nu am putut crea directorul uploads la $uploadDir");
    }
}

if (!is_writable($uploadDir)) {
    die("Eroare: directorul uploads nu este scriibil (permisiuni insuficiente). Calea: $uploadDir");
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['imagine'])) {
    $file = $_FILES['imagine'];
    if ($file['error'] !== UPLOAD_ERR_OK) {
        die("Eroare la încărcare (cod PHP: {$file['error']}).");
    }
    if ($file['size'] > 2 * 1024 * 1024) {
        die("Eroare: fișier prea mare ({$file['size']} bytes). Maxim 2MB.");
    }

    $ext = strtolower(pathinfo($file['name'], PATHINFO_EXTENSION));
    if (!in_array($ext, ['jpg', 'jpeg', 'png'], true)) {
        die("Eroare: tip de fișier neacceptat: .$ext");
    }

    $nume_fisier = uniqid('img_', true) . '.' . $ext;
    $dest = $uploadDir . DIRECTORY_SEPARATOR . $nume_fisier;

    if (!move_uploaded_file($file['tmp_name'], $dest)) {
        die("Eroare: nu am putut salva fișierul pe server la calea: $dest");
    }

    $stmt = $pdo->prepare("INSERT INTO poze (id_utilizator, fisier) VALUES (?, ?)");
    $stmt->execute([$_SESSION['user_id'], $nume_fisier]);

    header("Location: profil.php");
    exit;
}

$pozele_mele    = $pdo->prepare("SELECT * FROM poze WHERE id_utilizator = ?");
$pozele_mele->execute([$_SESSION['user_id']]);
$toti_utilizatorii = $pdo->prepare("SELECT * FROM utilizatori WHERE id != ?");
$toti_utilizatorii->execute([$_SESSION['user_id']]);
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Profil <?= htmlspecialchars($_SESSION['username'], ENT_QUOTES) ?></title>
    <link rel="stylesheet" href="ex5.css">
</head>
<body>
<header><h1>Profilul lui <?= htmlspecialchars($_SESSION['username'], ENT_QUOTES) ?></h1></header>

<form method="post" enctype="multipart/form-data">
    <label>Selectează imagine:
        <input type="file" name="imagine" accept=".jpg,.jpeg,.png" required>
    </label>
    <button type="submit">Uploadează</button>
</form>

<h2>Pozele tale</h2>
<div class="galerie">
    <?php foreach ($pozele_mele as $p): ?>
        <div>
            <img src="uploads/<?= htmlspecialchars($p['fisier'], ENT_QUOTES) ?>" alt="">
            <form method="post" action="sterge.php">
                <input type="hidden" name="id" value="<?= $p['id'] ?>">
                <button style="background:#dc3545;">Șterge</button>
            </form>
        </div>
    <?php endforeach; ?>
</div>

<h2>Profilurile celorlalți utilizatori</h2>
<?php foreach ($toti_utilizatorii as $u): ?>
    <h3><?= htmlspecialchars($u['username'], ENT_QUOTES) ?></h3>
    <div class="galerie">
        <?php
        $poze = $pdo->prepare("SELECT * FROM poze WHERE id_utilizator = ?");
        $poze->execute([$u['id']]);
        foreach ($poze as $img):
        ?>
            <img src="uploads/<?= htmlspecialchars($img['fisier'], ENT_QUOTES) ?>" alt="">
        <?php endforeach; ?>
    </div>
<?php endforeach; ?>

<p><a href="logout.php">Logout</a></p>
</body>
</html>
