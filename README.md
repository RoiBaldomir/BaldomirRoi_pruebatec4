# Prueba técnica 4 - API REST de gestión de reservas de hoteles y vuelos
## Descripción
API realizada en Spring Boot que permite realizar varias operaciones de gestión de hoteles y vuelos, así como de sus correspondientes reservas.
## Funcionamiento
Para ejecutar la API simplemente hay que clonar este proyecto, ejecutarlo desde tu IDE o editor correspondiente y probar los endpoints a continuación descritos.

La API cuenta con los siguientes endpoints documentados con Swagger `/doc/swagger-ui.html`:

1. Hoteles

    - Consultar lista de hoteles `/agency/hotels`
    - Consultar hotel por id `/agency/hotels/{id}`
    - Consultar hotel por rango de fechas y destino `/agency/hotels/find`
    - Crear hotel `/agency/hotels/new`
    - Editar hotel `/agency/hotels/edit/{id}`
    - Eliminar hotel `/agency/hotels/delete/{id}`
2. Vuelos

    - Consultar lista de vuelos `/agency/flights`
    - Consultar vuelo por id `/agency/flights/{id}`
    - Consultar vuelo por rango de fechas y origen y destino `/agency/flights/find`
    - Crear vuelo `/agency/flights/new`
    - Editar vuelo `/agency/flights/edit/{id}`
    - Eliminar vuelo `/agency/flights/delete/{id}`
3. Reservas

    - Consultar lista de reservas de hoteles `/agency/hotel-booking`
    - Crear reserva de hotel `/agency/hotel-booking/new`
    - Editar reserva de hotel `/agency/hotel-booking/edit/{id}`
    - Eliminar reserva de hotel `/agency/hotel-booking/delete/{id}`
    - Consultar lista de reservas de vuelos `/agency/flight-booking`
    - Crear reserva de vuelo `/agency/flight-booking/new`
    - Editar reserva de vuelo `/agency/flight-booking/edit/{id}`
    - Eliminar reserva de vuelo `/agency/flight-booking/delete/{id}`

## Seguridad
Se ha utilizado Spring Security para proteger mediante autentificación los endpoints de gestión de hoteles, vuelos y 
reservas y las consultas de la lista de reservas para que sean unicamente accesibles para los 
usuarios autenticados.
## Tests
Se han realizado un par de tests con JUnit para comprobar el correcto funcionamiento de 
los endpoints de consulta de hoteles y vuelos respectivamente.
## Supuestos
- Se han añadido funciones adicionales para la gestión y consulta de las reservas de hoteles y vuelos.
- Se han añadido validaciones extra para la creación de vuelos y hoteles para impedir registros duplicados.
