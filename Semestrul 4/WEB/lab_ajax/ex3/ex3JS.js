let formChanged = false;
let selectedUserId = null;

document.addEventListener("DOMContentLoaded", function () {
    function fetchUsers() {
        fetch('utilizatori.php')
            .then(response => response.json())
            .then(users => {
                const list = document.getElementById('userList');
                list.innerHTML = '';

                users.forEach(user => {
                    const li = document.createElement('li');
                    li.textContent = `${user.id} - ${user.lastName} `;
                    li.addEventListener('click', function () {
                        if (formChanged) {
                            if (confirm('You have unsaved changes. Save now?')) {
                                saveChanges();
                            }
                        }

                        selectedUserId = user.id;
                        document.getElementById('userId').value = user.id;
                        document.getElementById('lastName').value = user.lastName;
                        document.getElementById('firstName').value = user.firstName;
                        document.getElementById('phone').value = user.phone;
                        document.getElementById('email').value = user.email;
                        document.getElementById('saveButton').disabled = true;
                        formChanged = false;
                    });

                    list.appendChild(li);
                });
            });
    }

    function saveChanges() {
        const data = new URLSearchParams();
        data.append('id', document.getElementById('userId').value);
        data.append('lastName', document.getElementById('lastName').value);
        data.append('firstName', document.getElementById('firstName').value);
        data.append('phone', document.getElementById('phone').value);
        data.append('email', document.getElementById('email').value);

        fetch('utilizatori_update.php', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(response => response.text())
            .then(responseText => {
                console.log(responseText);
                fetchUsers();
                document.getElementById('saveButton').disabled = true;
                formChanged = false;
            });
    }

    document.getElementById('userForm').addEventListener('input', function () {
        document.getElementById('saveButton').disabled = false;
        formChanged = true;
    });

    document.getElementById('saveButton').addEventListener('click', saveChanges);

    fetchUsers();
});