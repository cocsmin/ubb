document.addEventListener('DOMContentLoaded', () => {
    function loadDirectory(path, parentElement) {
        fetch(`folder.php?path=${encodeURIComponent(path)}`)
            .then(response => response.json())
            .then(items => {
                const ul = document.createElement('ul');

                items.forEach(item => {
                    const li = document.createElement('li');
                    li.textContent = item.name;
                    li.dataset.path = item.path;

                    if (item.type === 'dir') {
                        li.classList.add('folder');
                        li.addEventListener('click', function (e) {
                            e.stopPropagation();

                            const existing = li.querySelector('ul');
                            if (existing) {
                                existing.style.display = existing.style.display === 'none' ? 'block' : 'none';
                            } else {
                                loadDirectory(item.path, li);
                            }
                        });
                    } else {
                        li.classList.add('file');
                        li.addEventListener('click', function (e) {
                            e.stopPropagation();
                            fetch(`folder.php?file=${encodeURIComponent(item.path)}`)
                                .then(res => res.text())
                                .then(content => {
                                    const display = document.getElementById('fileContent');
                                    display.textContent = content;
                                    display.hidden = false;
                                });
                        });
                    }

                    ul.appendChild(li);
                });

                parentElement.appendChild(ul);
            })
            .catch(err => alert('Error loading directory.'));
    }

    loadDirectory('.', document.getElementById('treeContainer'));
});