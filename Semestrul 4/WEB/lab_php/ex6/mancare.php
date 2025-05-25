<?php
$conn = new mysqli("localhost", "root", "", "retete");
if ($conn->connect_error) die("Eroare conectare");

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $nume = trim($_POST["nume"]);
    $comentariu = trim($_POST["comentariu"]);

    if ($nume !== "" && $comentariu !== "") {
        $nume = $conn->real_escape_string($nume);
        $comentariu = $conn->real_escape_string($comentariu);
        $conn->query("INSERT INTO comentarii (nume, comentariu, status) VALUES ('$nume', '$comentariu', 'neaprobat')");
    }

    header("Location: " . $_SERVER["REQUEST_URI"]);
    exit;
}

$comentarii = $conn->query("SELECT nume, comentariu FROM comentarii WHERE status = 'aprobat'");
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Articol Reteta</title>
    <link rel="stylesheet" href="ex6.css">
</head>
<body>
<header><h1>Rulada de pui cu sos de rosii si vin</h1></header>

<article>
    <h2>Ingrediente</h2>
    <ul>
        <li> 4 piepți de pui LaProvincia </li>
        <li> 200 ml de sos de roșii </li>
        <li> 100 ml de vin roșu </li>
        <li> 2 căței de usturoi (tocați fin) </li>
        <li> sare și piper după gust </li>
        <li> 2 linguri de ulei de măsline </li>
        <li> verdețuri proaspete pentru decor </li>
    </ul>
    <h2>Mod de preparare</h2>
    <p>Preîncălzește cuptorul la 180°C.
       Feliază 4 piepți de pui LaProvincia subțire.
       Într-un castron, amestecă 200 ml de sos de roșii cu 100 ml de vin roșu, 2 căței de usturoi (tocați fin), sare și piper după gust.
       Așază feliile de pui pe o suprafață de lucru și unge-le cu amestecul de sos de roșii și vin.
       Rulează feliile de pui și fixează-le cu scobitori.
       Transferă rulourile de pui într-o tavă de copt unsă cu 2 linguri de ulei de măsline.
       Toarnă peste rulouri restul de sos de roșii și vin.
       Coace în cuptor timp de aproximativ 20-25 de minute sau până când puiul este gătit complet.
       Servește rulourile de pui cu sos de roșii și vin, presărate cu verdețuri proaspete pentru un aspect și gust delicios.
       Poftă bună!
    </p>
</article>

<section>
    <h2>Adauga comentariu</h2>
    <form method="post">
        <label>Nume:
            <input type="text" name="nume" required>
        </label>
        <label>Comentariu:
            <textarea name="comentariu" required></textarea>
        </label>
        <button type="submit">Trimite comentariu</button>
    </form>
</section>

<section>
    <h2>Comentarii aprobate</h2>
    <?php while ($row = $comentarii->fetch_assoc()): ?>
        <div class="comment">
            <strong><?= htmlspecialchars($row["nume"]) ?></strong><br>
            <?= nl2br(htmlspecialchars($row["comentariu"])) ?>
        </div>
    <?php endwhile; ?>
</section>
</body>
</html>