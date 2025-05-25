let playerSymbol, computerSymbol;
let isPlayerTurn = false;

window.addEventListener("DOMContentLoaded", function () {
    const startBtn = document.getElementById("startGame");
    const cells = document.querySelectorAll("#gameBoard td");

    startBtn.addEventListener("click", () => {
        if (Math.random() < 0.5) {
            playerSymbol = "X";
            computerSymbol = "0";
            isPlayerTurn = true;
            alert(`You are X and you start!`);
        } else {
            playerSymbol = "0";
            computerSymbol = "X";
            isPlayerTurn = false;
            alert(`Computer is X and starts!`);
        }

        startBtn.style.display = "none";

        if (!isPlayerTurn) {
            makeMove();
        }
    });

    cells.forEach((cell) => {
        cell.addEventListener("click", () => {
            if (!isPlayerTurn || cell.textContent !== "") return;

            cell.textContent = playerSymbol;
            isPlayerTurn = false;
            makeMove();
        });
    });

    function makeMove() {
        const board = Array.from(document.querySelectorAll("#gameBoard tr")).map(row =>
            Array.from(row.querySelectorAll("td")).map(cell => cell.textContent)
        );

        const data = new URLSearchParams();
        data.append("player", playerSymbol);
        data.append("board", JSON.stringify(board));

        fetch("game.php", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: data,
        })
            .then(res => res.json())
            .then(response => {
                response.board.forEach((row, i) => {
                    row.forEach((val, j) => {
                        document.querySelectorAll("#gameBoard tr")[i]
                            .children[j].textContent = val || "";
                    });
                });

                if (response.gameOver) {
                    let msg;
                    switch (response.gameOver) {
                        case "player":   msg = "You won!";      break;
                        case "computer": msg = "Computer won!"; break;
                        case "draw":     msg = "It's a draw!"; break;
                    }
                    alert(msg);
                    cells.forEach(c =>
                        c.replaceWith(c.cloneNode(true))
                    );
                } else {
                    isPlayerTurn = true;
                }
            })
            .catch(err => console.error("Fetch error:", err));
    }
});
