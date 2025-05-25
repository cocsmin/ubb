$(document).ready(function () {
    function loadDirectory(path, parentLi) {
        $.getJSON('folder.php', { path: path }, function (items) {
            const $ul = $('<ul>');

            items.forEach(item => {
                const $li = $('<li>').text(item.name).attr('data-path', item.path);

                if (item.type === 'dir') {
                    $li.addClass('folder');

                    $li.on('click', function (e) {
                        e.stopPropagation();

                        const existing = $li.children('ul');
                        if (existing.length > 0) {
                            existing.toggle();
                        } else {
                            loadDirectory(item.path, $li);
                        }
                    });
                } else {
                    $li.addClass('file');

                    $li.on('click', function (e) {
                        e.stopPropagation();
                        $.get('folder.php', { file: item.path }, function (content) {
                            $('#fileContent').text(content).show();
                        });
                    });
                }

                $ul.append($li);
            });

            parentLi.append($ul);
        });
    }

    loadDirectory('.', $('#treeContainer'));
});