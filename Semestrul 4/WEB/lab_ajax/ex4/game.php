<?php
header('Content-Type: application/json');

$board = json_decode($_POST['board']);
$player = $_POST['player'];
$computer = $player === 'X' ? '0' : 'X';

function checkWinner($b, $symbol) {
    for ($i = 0; $i < 3; $i++) {
        if ($b[$i][0] === $symbol && $b[$i][1] === $symbol && $b[$i][2] === $symbol) return true;
        if ($b[0][$i] === $symbol && $b[1][$i] === $symbol && $b[2][$i] === $symbol) return true;
    }
    if ($b[0][0] === $symbol && $b[1][1] === $symbol && $b[2][2] === $symbol) return true;
    if ($b[0][2] === $symbol && $b[1][1] === $symbol && $b[2][0] === $symbol) return true;
    return false;
}

function isDraw($b) {
    foreach ($b as $row) {
        foreach ($row as $cell) {
            if ($cell === '') return false;
        }
    }
    return true;
}

if (checkWinner($board, $player)) {
    echo json_encode(['board' => $board, 'gameOver' => 'player']);
    exit;
}

for ($i = 0; $i < 3; $i++) {
    for ($j = 0; $j < 3; $j++) {
        if ($board[$i][$j] === '') {
            $board[$i][$j] = $computer;
            break 2;
        }
    }
}

if (checkWinner($board, $computer)) {
    echo json_encode(['board' => $board, 'gameOver' => 'computer']);
    exit;
}

if (isDraw($board)) {
    echo json_encode(['board' => $board, 'gameOver' => 'draw']);
    exit;
}
echo json_encode(['board' => $board]);
?>