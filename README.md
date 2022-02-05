# DAD-project
- [Introducción](#introducción)
  * [Resumen del funcionamiento de la aplicación](#resumen-del-funcionamiento-de-la-aplicación)
  * [Parte pública y privada de la aplicación](#parte-pública-y-privada-de-la-aplicación)
  * [Descripción de las entidades](#descripción-de-las-entidades)
    + [Oferta](#oferta)
      - [Propiedades](#propiedades)
    + [Item](#item)
      - [Propiedades](#propiedades-1)
    + [Usuario](#usuario)
      - [Propiedades](#propiedades-2)
    + [Comentario](#comentario)
      - [Propiedades](#propiedades-3)
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

## Descripción de las entidades
### Oferta
Representa una oferta de venta que ha creado un usuario, y que se destruye en el momento en que la oferta es comprada por otro usuario. Las ofertas pueden consultarse y filtrarse en un buscador que ofrece la aplicación tanto por usuarios registrados como anónimos, pero sólo pueden crearlas los usuarios registrados.
#### Propiedades
- **Vendedor**: El usuario que ha creado la oferta
- **Precio**: El precio de venta establecido por el vendedor en el momento de la creación de la oferta.
- **Item**: El ítem que se está vendiendo. Tendrá un nombre y una categoría. En el momento de creación de una oferta, el vendedor indica nombre y categoría del ítem que se oferta.
- **Fecha** (de creación): La fecha en la que el vendedor creó la oferta. Se establece automáticamente.

### Item
Representa el objeto o ítem que se vende en una oferta. Almacena información relevante para la búsqueda/filtrado.
#### Propiedades
- **Nombre**: Un texto corto que identifica el ítem que se está vendiendo. Varios ítems diferentes pueden tener el mismo nombre.
- **Categoria**: Un identificador de texto que indica la “categoría” del ítem para propósitos de filtrado en el buscador. Pertenece a un conjunto de categorías predefinido.

### Usuario
Representa la cuenta de un usuario registrado.
#### Propiedades
- **Nombre**: Un texto corto que identifica el usuario. Es único para cada usuario.
- **Contraseña**: se utiliza para autenticar al usuario en el login.
- **Puntuacion**: La puntuación de vendedor, derivada a partir de las puntuaciones que le dan los compradores de las ofertas.
- **Comentarios**: Una lista de comentarios escritos por compradores de las ofertas que ha creado el usuario.

### Comentario
Representa un comentario sobre un vendedor que ha dejado un usuario tras comprar una oferta que el vendedor ha creado.
#### Propiedades
- **Autor**: El usuario que ha escrito el comentario.
- **Contenido**: El texto del comentario.
- **Fecha**: La fecha de publicación del comentario. Se establece automáticamente.


## Descripción del servicio interno
La aplicación utilizará un servicio interno (no accesible de forma directa por los usuarios) de correo/mensajería para facilitar la comunicación entre compradores o entre comprador y vendedor. Utilizando este servicio, los usuarios podrán enviar y recibir mensajes de otros usuarios (además de consultar los mensajes que se hayan enviado o recibido en el pasado).

# Navegación

# Modelo de datos



