# DAD-project
- [Introducción](#introducción)
  * [Resumen del funcionamiento de la aplicación](#resumen-del-funcionamiento-de-la-aplicación)
    + [Casos de uso](#casos-de-uso)
  * [Parte pública y privada de la aplicación](#parte-pública-y-privada-de-la-aplicación)
  * [Descripción de las entidades](#descripción-de-las-entidades)
    + [Oferta](#oferta)
    + [Item](#item)
    + [Usuario](#usuario)
    + [Comentario](#comentario)
  * [Descripción del servicio interno](#descripción-del-servicio-interno)
- [Navegación](#navegación)
- [Modelo de datos](#modelo-de-datos)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


# Introducción
Sellhub es una aplicación genérica de compra y venta de cosas. Cualquiera puede consultar las ofertas (los ítems a la venta en un momento dado), y los usuarios registrados pueden también (entre otras cosas) poner ítems en venta o comprarlos.

## Resumen del funcionamiento de la aplicación
Los usuarios registrados pueden crear **ofertas**. Las ofertas tienen una serie de propiedades:
- Un **vendedor** (el usuario que las creó).
- Un **precio**, que establece el vendedor en su creación.
- Un **ítem**, que representa aquello que se está vendiendo.
- Una **fecha de creación** que se determina de forma automática.

Para que la búsqueda/filtrado de ofertas sea útil, los ítems también tienen propiedades:
- Un **nombre** (que no es único, es decir varios ítems de ofertas diferentes pueden tener el mismo nombre)
- Una **categoría**, que el creador de la oferta selecciona de una lista de categorías que le ofrece el sistema en el momento de su creación (la lista podría ser, por ejemplo, "comida", "muebles", y "otros"). 

Se pueden consultar las ofertas y filtrarlas utilizando cualquier combinación de estas propiedades (por ejemplo, las ofertas de ítems de categoría “comida” por debajo de 20 euros creadas por cierto vendedor).

Los usuarios registrados tienen cuentas. Las cuentas también tienen una serie de propiedades:
- Un **nombre** de usuario.
- Una **contraseña**.
- Una **puntuación de vendedor**, que se calcula en base a las puntuaciones que recibe de los compradores de sus ofertas.
- Puede haber uno o varios **comentarios** (de compradores) asociados a una cuenta.

Cuando un usuario _compra_ una oferta, debe puntuar al vendedor. La puntuación de vendedor de una cuenta es la media aritmética de todas las puntuaciones que recibe un usuario de este modo. Opcionalmente el comprador puede también dejar un comentario. Los comentarios tienen **autor** y **contenido**.

### Casos de uso
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

### Valoracion
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



