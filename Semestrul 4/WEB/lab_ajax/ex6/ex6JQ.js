$(document).ready(function () {
    function incarcaRezultate() {
        $.ajax({
            url: 'filter.php',
            method: 'POST',
            data: $('#filterForm').serialize(),
            success: function (raspuns) {
                $('#rezultate').html(raspuns);
            }
        });
    }

    $('#filterForm select').on('change', incarcaRezultate);

    incarcaRezultate();
});