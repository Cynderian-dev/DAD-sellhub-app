# DAD-project
- [Introducción al funcionamiento de la aplicación](#introducción-al-funcionamiento-de-la-aplicación)
  * [Casos de uso](#casos-de-uso)
  * [Parte pública y privada de la aplicación](#parte-pública-y-privada-de-la-aplicación)
  * [Diagrama de navegación](#diagrama-de-navegación)
  * [Descripción general de las entidades](#descripción-general-de-las-entidades)
    + [Oferta](#oferta)
    + [Usuario](#usuario)
    + [Valoracion](#valoración)
    + [Lista](#lista)
  * [Descripción del servicio interno](#descripción-del-servicio-interno)
- [Navegación](#navegación)
- [Modelo de datos](#modelo-de-datos)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


# Introducción al funcionamiento de la aplicación
Los usuarios de SellHub pueden _crear_ ofertas y _comprar_ ofertas ya creadas. Cuando compran una oferta, deben valorar al vendedor dejando una puntuación y un comentario. Los usuarios también pueden cerrar o modificar sus ofertas, crear listas donde guardar organizadamente ofertas que les interesen, o utilizar los buscadores de ofertas/usuarios para encontrar lo que buscan.

## Casos de uso
Casos de uso por actor:

Todos los _Usuarios_ (en general, independientemente de si son usuarios registrados o anónimos) podrán:
- **Consultar ofertas** (utilizando el buscador de ofertas)
- **Consultar usuarios** (utilizando el buscador de usuarios)

Los _Usuarios registrados_ (los usuarios que han iniciado sesión) además podrán:
- **Crear ofertas**
- **Eliminar ofertas** (consultándolas previamente)
- **Comprar ofertas** (consultándolas previamente)

Los _Usuarios anónimos_ (los usuarios que no han iniciado sesión) además podrán:
- **Iniciar sesión**
- **Crear una cuenta de usuario**

![Diagrama de casos de uso](https://user-images.githubusercontent.com/22685426/152697023-b3dca184-e24d-452c-8481-c2ec02035294.png)

## Diagrama de navegación


| Captura de pantalla  | Descripción breve |
| ------------- | ------------- |
| ![Pantalla buscador ofertas](docs/images/Pantalla_buscador_ofertas.png)  | **Pantalla de buscador de ofertas**: Aquí se pueden consultar y filtrar las ofertas activas en SellHub en un momento dado.  |
| ![Pantalla buscador usuarios](docs/images/Pantalla_buscador_usuarios.png) | **Pantalla de buscador de usuarios**: Aquí se pueden consultar los usuarios de SellHub.  |
| ![Pantalla confirmacion compra](docs/images/Pantalla_confirmacion_compra.png) | **Pantalla de confirmación de compra**: Desde esta pantalla puede comprarse una oferta.  |
| ![Pantalla crear oferta](docs/images/Pantalla_crear_oferta.png) | **Pantalla de creación de oferta**: Desde aquí los usuarios pueden crear ofertas.  |
| ![Pantalla detalles lista](docs/images/Pantalla_detalles_lista.png) | **Pantalla de detalles de lista**: Aquí se pueden consultar los detalles (principalmente, los elementos) de las listas de un usuario.  |
| ![Pantalla informacion usuario](docs/images/Pantalla_informacion_usuario.png) | **Panel de usuario - Información**: Aquí se puede consultar información de un usuario.  |
| ![Pantalla listas usuario](docs/images/Pantalla_listas_usuario.png) | **Panel de usuario - listas**: Aquí un usuario puede consultar sus listas.  |
| ![Pantalla ofertas usuario](docs/images/Pantalla_ofertas_usuario.png) | **Panel de usuario - ofertas**: Aquí un usuario puede consultar sus ofertas.   |
| ![Pantalla valoraciones usuario](docs/images/Pantalla_valoraciones_usuario.png) | **Panel de usuario - valoraciones**: Aquí se pueden consultar las valoraciones que ha recibido un usuario.   |

## Parte pública y privada de la aplicación
Los usuarios no registrados de la aplicación podrán:
- Consultar las ofertas (y filtrarlas).
- Ver perfiles de cuentas, donde podrán consultar la puntuación de vendedor y los comentarios de una cuenta.
- Crear una cuenta de usuario (registrarse).

Los usuarios registrados tendrán a su disposición toda la funcionalidad de la aplicación, pudiendo:
- Consultar ofertas y perfiles (igual que un usuario no registrado).
- Crear ofertas.
- _Comprar_ ofertas.
- Administrar su cuenta, por ejemplo cambiando la contraseña.

## Descripción general de las entidades
### Oferta
Representa una oferta de venta que ha creado un usuario. Las ofertas pueden consultarse y filtrarse en un buscador que ofrece la aplicación tanto por usuarios registrados como anónimos, pero sólo pueden crearlas los usuarios registrados.
#### Propiedades
- **Vendedor**: El usuario que ha creado la oferta
- **Precio**: El precio de venta establecido por el vendedor en el momento de la creación de la oferta.
- **Título**: Breve descripción de lo que se está vendiendo.
- **Categoría**: Un identificador de texto que clasifica lo que se está vendiendo. Pertenece a un conjunto de categorías predefinido.
- **Fecha (de creación)**: La fecha en la que el vendedor creó la oferta.
- **Fecha (de cierre)**: La fecha en la que al oferta fue comprada por un usuario o cerrada por su creador.

### Usuario
Representa la cuenta de un usuario registrado.
#### Propiedades
- **Nombre**: Un texto corto que identifica el usuario. Es único para cada usuario.
- **Contraseña**: se utiliza para autenticar al usuario en el login.
- **Puntuacion**: La puntuación de vendedor, derivada a partir de las valoraciones de los compradores de sus ofertas.
- **Valoraciones**: Las valoraciones recibidas por compradores de las ofertas que ha creado el usuario.

### Valoración
Representa una valoración sobre un vendedor que ha dejado un usuario tras comprar una oferta que el vendedor ha creado.
#### Propiedades
- **Autor**: El usuario que ha escrito el comentario.
- **Puntuación**: Puntuación numérica que se le da al vendedor de la oferta.
- **Comentario**: Un pequeño texto que acompaña a la puntuación.
- **Fecha**: La fecha de publicación de la valoración. Se establece automáticamente.

### Lista
Representa una lista que ha creado un usuario para organizar las ofertas que le interesan.
#### Propiedades
- **Nombre**: El nombre de la lista. Un usuario no puede tener dos listas del mismo nombre.
- **Propietario**: Usuario que ha creado y administra la lista.
- **Contenido**: Las ofertas almacenadas en la lista.


## Descripción del servicio interno
La aplicación utilizará un servicio interno (no accesible de forma directa por los usuarios) de correo/mensajería para facilitar la comunicación entre compradores o entre comprador y vendedor. Utilizando este servicio, los usuarios podrán enviar y recibir mensajes de otros usuarios (además de consultar los mensajes que se hayan enviado o recibido en el pasado).

# Navegación

# Modelo de datos



