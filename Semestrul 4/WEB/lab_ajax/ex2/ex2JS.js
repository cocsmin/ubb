document.addEventListener('DOMContentLoaded', function () {
    let page = 1;

    function fetchData() {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `utilizatori.php?page=${page}`, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                const tableBody = document.querySelector('#data-table tbody');
                tableBody.innerHTML = '';

                response.data.forEach(user => {
                    const row = document.createElement('tr');
                    row.innerHTML = `<td>${user.nume}</td><td>${user.prenume}</td><td>${user.telefon}</td><td>${user.email}</td>`;
                    tableBody.appendChild(row);
                });

                document.getElementById('prev-btn').disabled = page === 1;
                document.getElementById('next-btn').disabled = (page * 3 >= response.total);
            }
        };
        xhr.send();
    }

    document.getElementById('prev-btn').addEventListener('click', function () {
        if (page > 1) {
            page--;
            fetchData();
        }
    });

    document.getElementById('next-btn').addEventListener('click', function () {
        page++;
        fetchData();
    });

    fetchData();
});