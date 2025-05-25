let page = 1;

function fetchData() {
    $.get('utilizatori.php', { page: page }, function(response) {
        const tableBody = $('#data-table tbody');
        tableBody.empty();

        response.data.forEach(user => {
            const row = `
              <tr>
                <td>${user.nume}</td>
                <td>${user.prenume}</td>
                <td>${user.telefon}</td>
                <td>${user.email}</td>
              </tr>`;
            tableBody.append(row);
        });

        $('#prev-btn').prop('disabled', page === 1);
        $('#next-btn').prop('disabled', (page * 3 >= response.total));
    }, 'json');
}

$(document).ready(function () {
    $('#prev-btn').on('click', function () {
        if (page > 1) {
            page--;
            fetchData();
        }
    });

    $('#next-btn').on('click', function () {
        page++;
        fetchData();
    });

    fetchData();
});