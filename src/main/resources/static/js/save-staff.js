document.addEventListener('DOMContentLoaded', function() {
    // Datos simulados de usuarios
    const users = {
        available: [
            { id: 1, username: "user1" },
            { id: 2, username: "user2" },
            { id: 3, username: "user3" },
        ],
        otros: [
            { id: 4, username: "user4" },
            { id: 5, username: "user5" }
        ]
    };

    const availableUsers = document.getElementById('availableUsers');
    const assignedUsers = document.getElementById('otros');

    // Función para renderizar una lista de usuarios
    function renderUserList(userList, selectElement) {
        selectElement.innerHTML = '';  // Limpiar la lista antes de renderizar
        userList.forEach(user => {
            const option = document.createElement('option');
            option.value = user.id;
            option.textContent = user.nombre;
            selectElement.appendChild(option);
        });
    }

    // Inicializar las listas
    renderUserList(users.available, availableUsers);
    renderUserList(users.otros, assignedUsers);

    // Función para mover un usuario de una lista a otra
    function moveUser(sourceList, targetList, selectElementSource, selectElementTarget) {
        const selectedOption = selectElementSource.selectedOptions[0]; // Usar selectedOptions[0] para obtener el primer elemento seleccionado

        if (selectedOption) {
            // Obtener el usuario que se seleccionó
            const userId = selectedOption.value;
            const user = sourceList.find(user => user.id == userId);

            // Mover el usuario de la lista origen a la lista destino
            sourceList.splice(sourceList.indexOf(user), 1);
            targetList.push(user);

            // Volver a renderizar las listas
            renderUserList(sourceList, selectElementSource);
            renderUserList(targetList, selectElementTarget);
        }
    }

    // Mover de "usuarios disponibles" a "usuarios asignados"
    document.getElementById('moveRight').addEventListener('click', function() {
        moveUser(users.available, users.assigned, availableUsers, otros);
    });

    // Mover de "usuarios asignados" a "usuarios disponibles"
    document.getElementById('moveLeft').addEventListener('click', function() {
        moveUser(users.assigned, users.available, assignedUsers, otros);
    });
});



