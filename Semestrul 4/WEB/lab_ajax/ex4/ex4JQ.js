$(document).ready(function() {
    let playerSymbol, computerSymbol;
    let isPlayerTurn = false;

    $('#startGame').click(function() {
        if (Math.random() < 0.5) {
            playerSymbol = 'X';
            computerSymbol = '0';
            isPlayerTurn = true;
            alert(`You are X and you start!`);
        } else {
            playerSymbol = '0';
            computerSymbol = 'X';
            isPlayerTurn = false;
            alert(`Computer is X and starts!`);
        }

        $('#startGame').hide();

        if (!isPlayerTurn) {
            makeMove();
        }
    });

    $('#gameBoard td').click(function() {
        if (!isPlayerTurn || $(this).text() !== '') return;

        $(this).text(playerSymbol);
        isPlayerTurn = false;
        makeMove();
    });

    function makeMove() {
        const board = [];
        $('#gameBoard tr').each(function() {
            const row = [];
            $(this).find('td').each(function() {
                row.push($(this).text());
            });
            board.push(row);
        });

        $.ajax({
            url: 'game.php',
            type: 'POST',
            data: { board: JSON.stringify(board), player: playerSymbol },
            success: function(response) {
                const newBoard = response.board;
                $('#gameBoard tr').each(function(i) {
                    $(this).find('td').each(function(j) {
                        $(this).text(newBoard[i][j] || '');
                    });
                });

                if (response.gameOver) {
                    let message;
                    switch (response.gameOver) {
                        case 'player':
                            message = 'You won!';
                            break;
                        case 'computer':
                            message = 'Computer won!';
                            break;
                        case 'draw':
                            message = 'It\'s a draw!';
                            break;
                    }
                    alert(message);
                    $('#gameBoard td').off('click');
                } else {
                    isPlayerTurn = true;
                }
            }
        });
    }
});
