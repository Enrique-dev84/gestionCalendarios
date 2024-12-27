// Script para enviar datos como JSON
document.querySelector('#businessForm').addEventListener('submit', function (e) {
    e.preventDefault(); // Evita el envío tradicional del formulario

    // Recopilar los datos del formulario
    const formData = new FormData(e.target);
    const formObject = {};
    formData.forEach((value, key) => {
        formObject[key] = value;
    });

    // Convertir el objeto a JSON
    const jsonData = JSON.stringify(formObject);

    // Enviar los datos usando fetch
    fetch('/admin-dashboard/business/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Cambiar a 'application/json'
        },
        body: jsonData,
    })
    .then(response => response.json())
    .then(data => {
        const alertContainer = document.getElementById('alertContainer');
        const alertMessage = document.createElement('div');

        if (data.error) {
            // Si hay un error, muestra el mensaje del backend
            alertMessage.classList.add('alert', 'alert-danger', 'alert-dismissible', 'fade', 'show');
            alertMessage.innerHTML = ` <strong>Error!</strong> ${data.error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>`;
        } else {
            // Si todo va bien, muestra el mensaje de éxito
            alertMessage.classList.add('alert', 'alert-success', 'alert-dismissible', 'fade', 'show');
            alertMessage.innerHTML = ` <strong>¡Éxito!</strong> Negocio creado exitosamente con ID: ${data.businessId}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>`;

            // Redirigir a una nueva URL después de crear el negocio
            window.location.href = `/admin-dashboard/business/${data.businessId}/new-staff`; // Redirigir solo si el negocio se crea correctamente
        }

        // Añadir el mensaje al contenedor
        alertContainer.appendChild(alertMessage);

        // Vaciar el formulario en caso de éxito
        if (!data.error) {
            document.querySelector('#businessForm').reset();  // Restablece todos los campos del formulario
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Error al crear el negocio. Por favor, inténtalo más tarde.");
    });
});

