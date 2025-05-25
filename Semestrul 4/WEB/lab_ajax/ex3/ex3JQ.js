let formChanged = false;
let selectedUserId = null;

$(document).ready(function () {
    function fetchUsers() {
        $.getJSON('utilizatori.php', function (users) {
            const $list = $('#userList').empty();
            users.forEach(user => {
                const $item = $('<li>').text(`${user.id} - ${user.lastName}`);
                $item.on('click', function () {
                    if (formChanged) {
                        if (confirm('You have unsaved changes. Save now?')) {
                            $('#saveButton').click();
                        }
                    }
                    selectedUserId = user.id;
                    $('#userId').val(user.id);
                    $('#lastName').val(user.lastName);
                    $('#firstName').val(user.firstName);
                    $('#phone').val(user.phone);
                    $('#email').val(user.email);
                    $('#saveButton').prop('disabled', true);
                    formChanged = false;
                });
                $list.append($item);
            });
        });
    }

    $('#userForm input').on('input', function () {
        $('#saveButton').prop('disabled', false);
        formChanged = true;
    });

    $('#saveButton').on('click', function () {
        const data = {
            id: $('#userId').val(),
            lastName: $('#lastName').val(),
            firstName: $('#firstName').val(),
            phone: $('#phone').val(),
            email: $('#email').val()
        };
        $.post('utilizatori_update.php', data, function (response) {
            console.log(response);
            fetchUsers();
            $('#saveButton').prop('disabled', true);
            formChanged = false;
        });
    });

    fetchUsers();
});