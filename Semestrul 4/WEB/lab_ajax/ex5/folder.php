<?php
define('ROOT_PATH', realpath(__DIR__ . '/testroot'));

function sendJson($data) {
    header('Content-Type: application/json; charset=UTF-8');
    echo json_encode($data);
    exit;
}

function sendText($text) {
    header('Content-Type: text/plain; charset=UTF-8');
    echo $text;
    exit;
}

function isSafePath($realPath) {
    return $realPath !== false && str_starts_with($realPath, ROOT_PATH);
}

if (isset($_GET['file'])) {
    $requested = realpath(ROOT_PATH . '/' . ltrim($_GET['file'], '/'));

    if (!isSafePath($requested) || !is_file($requested) || !is_readable($requested)) {
        http_response_code(403);
        sendText('Access denied');
    }

    sendText(file_get_contents($requested));
}

$pathParam = $_GET['path'] ?? '.';
$target = realpath(ROOT_PATH . '/' . ltrim($pathParam, '/'));

if (!isSafePath($target) || !is_dir($target)) {
    http_response_code(403);
    sendJson(['error' => 'Invalid path']);
}

$result = [];
foreach (scandir($target) as $entry) {
    if ($entry === '.' || $entry === '..') continue;
    $fullPath = $target . '/' . $entry;
    $result[] = [
        'name' => $entry,
        'path' => trim($pathParam . '/' . $entry, '/'),
        'type' => is_dir($fullPath) ? 'dir' : 'file'
    ];
}


sendJson($result);
