# Sistema de Gestión de Anuncios - Primer Parcial C6

**Institución:** UTNMDP Regional Mar del Plata
**Carrera:** TUP-2do Año
**Materia:** Programación 3
**Profesor:** Tec. Mango Eduardo
**Ayudante:** Tec. Raipane Kevin

---

## Descripción del Proyecto
La agencia de marketing digital necesita digitalizar y automatizar su motor de anuncios (Ad Server) para optimizar el consumo de los presupuestos diarios de las campañas activas mediante una estrategia de sobreventa controlada (Overbudget). El sistema debe gestionar además las contingencias de reembolsos/devolución de crédito a anunciantes y la reasignación automática de anuncios cuando una campaña sature su capacidad financiera base.

## Diagrama Entidad Relación (DER)

### `campanas`
* `id`: integer
* `nombre_campana`: varchar
* `target_audiencia`: varchar
* `tipo_dispositivo`: varchar
* `fecha_fin`: timestamp
* `presupuesto_base`: decimal

> **Nota:** Las campañas serán provistas como entidad dentro de JPA, así como un archivo `.sql` que inicialice su estado en la base de datos, por lo que no deberán gestionar su CRUD.

### `anunciantes`
* `id`: integer
* `nombre_empresa`: varchar
* `cuit`: varchar
* `es_cuenta_vip`: boolean
* `activo`: boolean

### `anuncios`
* `id`: integer
* `codigo_anuncio`: uvid
* `fecha_creacion`: date
* `inversion_estimada`: decimal
* `inversion_total`: decimal
* `estado`: varchar (Estados posibles: Pendiente, En curso, Finalizado y Cancelado)
* `id_campana`: integer NN
* `id_anunciante`: Integer NN

---

## Requisitos Funcionales

### Gestión de Anunciantes
* El sistema deberá permitir que se realicen operaciones de ALTA y BAJA sobre anunciantes.
* La baja deberá ser lógica.
* No deberá poder darse de baja un anunciante si tienen algún anuncio pendiente o en curso.
* El sistema deberá permitir que se visualicen todos los anunciantes.
* Deberá permitir filtrados opcionales por nombre de empresa y/o cuit.
* El sistema deberá permitir visualizar todos los anunciantes pertenecientes a una campaña en particular.

### Gestión de Anuncios
* El sistema deberá permitir cargar un anuncio nuevo en el sistema.
* El anuncio deberá pertenecer a un anunciante que este activo y a una campaña que no haya alcanzado su fecha de finalización.
* Deberá validar que no se supere el límite de presupuesto estipulado en el apartado de Reglas de Negocio.
* El sistema deberá permitir cargar gastos a un anuncio en particular.
* Al cargar gastos, se deben sumar al valor de inversion_total del anuncio.
* Si el anuncio se encuentra cancelado, esta operación debe fallar.
* Cuando a un anuncio se le carguen gastos por primera vez, su estado deberá cambiar de "Pendiente" a "En Curso".
* La inversión total no podrá superar la inversión estimada por más de un 30%. En caso de hacerlo, la operación deberá fallar.
* Una vez que una carga de gastos hace que la inversion_total supere a la inversión_estimada, el anuncio deberá cerrarse automáticamente, pasando su estado a finalizado.
* El sistema deberá permitir cancelar un anuncio.
* Al cancelar un anuncio, su estado debe cambiar a "Cancelado".
* El sistema deberá permitir visualizar todos los anuncios de una campaña especifica.

---

## Reglas de Negocio y Validaciones

* **Control de Presupuestos:** Una campaña permite que se le registren anuncios que superen su presupuesto_base, pero cuenta con un Límite de Over Budget Estricto. El máximo de inversión permitida en cola para una campaña se calcula como: Presupuesto Base + (Presupuesto Base * 0.35) (es decir, un 35% de tolerancia por encima de su presupuesto original). Si un nuevo anuncio en estado Pendiente supera este techo financiero, la operación debe fallar inmediatamente.
* **Política de Reembolso Monetario:** Al "cancelar" un anuncio, este no se borra físicamente de la base de datos (se mantiene para auditoría histórica con estado 'CANCELADO'). El cálculo del dinero a reintegrar al saldo del anunciante se rige bajo la siguiente lógica condicional:
    * Si el anunciante es Cuenta VIP, se le reembolsa el 100% de la inversion_estimada.
    * Si el anunciante no es VIP (Regular), se le aplica una penalización por reserva de espacio en el AdServer: se le devuelve únicamente el 80% de la inversion_estimada (el 20% restante lo retiene la agencia).
    * Se deberá retornar un `CancelacionDTO`, que contenga la información del anuncio cancelado, asi como el monto que fue reembolsado.
* **Regla de Negocio Adicional:** Si una carga de gastos excede el límite máximo permitido del anuncio actual (Inversión Total > Inversión Estimada * 1.30), el sistema deberá buscar automáticamente dentro de la misma campaña un anuncio en estado Pendiente perteneciente a un anunciante Regular (No VIP). Si encuentra uno, el anuncio encontrado será cancelado automáticamente y su inversión estimada será transferida completamente al anuncio actual, aumentando así su capacidad disponible. Luego de la transferencia, la carga de gastos deberá reintentarse automáticamente. Si aun así el límite continúa excedido, o no existe un anuncio elegible, la operación deberá fallar.

---

## Guía de Endpoints

| Método HTTP | Enlace (URI) | Descripción Funcional | Códigos HTTP Esperados |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/anunciantes` | Crea un nuevo anunciante en el sistema (activo = true). Requiere validación de campos obligatorios en el DTO de entrada. | 211 Created <br> 400 Bad Request |
| **DELETE** | `/api/anunciantes/{id}` | Realiza la baja lógica (activo = false). La operación debe fallar si el anunciante posee anuncios asociados en estado PENDIENTE o EN_CURSO. | 200 OK <br> 400 Bad Request <br> 404 Not Found |
| **GET** | `/api/anunciantes` | Retorna la lista completa de anunciantes. Permite aplicar filtros opcionales por los Query Params nombre y/o cuit. | 200 OK |
| **GET** | `/api/anunciantes/campana/{idCampana}` | Retorna la lista de todos los anunciantes únicos que tienen al menos un anuncio asociado al ID de campaña especificado. | 200 OK <br> 404 Not Found |
| **POST** | `/api/anuncios` | Registra un nuevo anuncio con estado PENDIENTE e inversion_total = 0. Valida que el anunciante esté activo, la campaña no haya vencido y no se quiebre el límite del 35% de overbudget. | 201 Created <br> 400 Bad Request <br> 409 Conflict |
| **GET** | `/api/anuncios/idCampaña` | Retorna la lista completa de anuncios de una campaña. | 200 OK <br> 404 Not Found |
| **POST** | `/api/anuncios/{id}/gastos` | Registra una carga de gastos al anuncio. Acumula en inversion_total, maneja la transición a EN_CURSO o FINALIZADO, y ejecuta la Lógica de Redistribución de Saldo si excede el 30%. | 200 OK <br> 400 Bad Request <br> 404 Not Found |
| **POST** | `/api/anuncios/{id}/cancelar` | Cancela el anuncio pasando su estado a CANCELADO. Aplica las políticas de reembolso y retorna obligatoriamente un CancelacionDTO. | 200 OK <br> 400 Bad Request <br> 404 Not Found |

---

## Restricciones Técnicas Obligatorias

* **Mapeo y Base de Datos:** Las entidades deberán estar correctamente mapeadas con JPA/Hibernate respetando las relaciones.
* **Uso Estricto de DTOs:** No se pueden exponer las entidades JPA en los controladores. Se deben usar DTOs específicos para la entrada y salida de datos.
* **Validaciones del Framework:** Es obligatorio el uso de `@Valid` y anotaciones de (`@NotNull`, `@Min`, `@Size`, etc.) en los DTOs de entrada.
* **Manejo Centralizado de Excepciones:** Al menos una regla de negocio compleja debe lanzar una Custom Exception propia que sea capturada y procesada por un `@ControllerAdvice`, retornando un código de estado HTTP adecuado (ej: 400 Bad Request o 409 Conflict) con un mensaje claro.
* **Arquitectura:** Se debe implementar la arquitectura en capas (Controller - Service - Repository) respetando los principios SOLID y las buenas prácticas promovidas en la cursada.
