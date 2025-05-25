function getPlecari() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'plecari.php', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById('orasPlecare').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

function getSosiri(value) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'sosiri.php?name=' + encodeURIComponent(value), true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById('orasSosire').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}