document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("filterForm");
    const output = document.getElementById("rezultate");

    function incarca() {
        const formData = new FormData(form);
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "filter.php", true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                output.innerHTML = xhr.responseText;
            }
        };
        xhr.send(formData);
    }

    form.addEventListener("change", incarca);

    incarca();
});