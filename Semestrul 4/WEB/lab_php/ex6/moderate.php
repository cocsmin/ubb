<?php
session_start();
if (!isset($_SESSION["admin"])) {
    header("Location: index.html");
    exit;
}
$conn = new mysqli("localhost", "root", "", "retete");
$rez = $conn->query("SELECT * FROM comentarii WHERE status = 'neaprobat'");
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Moderare Comentarii</title>
    <link rel="stylesheet" href="ex6.css">
</head>
<body>
<header><h1>Moderare Comentarii</h1></header>

<main>
<?php if ($rez->num_rows === 0): ?>
    <p style="text-align:center; font-size: 18px;">Nu exista comentarii in asteptare.</p>
<?php else: ?>
    <?php while ($c = $rez->fetch_assoc()): ?>
        <div class="comment">
            <strong style="font-size: 1.1em;"><?= htmlspecialchars($c["nume"]) ?></strong><br>
            <p><?= nl2br(htmlspecialchars($c["comentariu"])) ?></p>
           <div style="margin-top: 1rem; display: flex; gap: 10px;">
               <form method="post" action="aproba.php" style="flex: 1;">
                   <input type="hidden" name="id" value="<?= $c["id"] ?>">
                   <button type="submit" style="width: 100%;">Aproba</button>
               </form>
               <form method="post" action="sterge.php" style="flex: 1;">
                   <input type="hidden" name="id" value="<?= $c["id"] ?>">
                   <button type="submit" style="background: #dc3545; width: 100%;">Sterge</button>
               </form>
           </div>
        </div>
    <?php endwhile; ?>
<?php endif; ?>
</main>

<footer style="margin-top: 2rem;">
    <a href="logout.php" style="display: inline-block; text-align: center;">Logout</a>
</footer>
</body>
</html>