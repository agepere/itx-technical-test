# Despliegue

Para el despliegue se recomienda utilizar Docker, ya que el proyecto contiene el Dockerfile y el compose para lanzar la aplicación rápidamente con el comando:

```
docker compose up -d
```

Con versiones antiguas de docker es posible que haya que modificarlo a:

```
docker-compose up -d
```

# Endpoints

Tras lanzar la aplicación podremos acceder a http://localhost:8080/swagger-ui (host por defecto), para ver el documentador de la API realizado en swagger. En concreto encontraremos los endpoints:

- **POST /api/products**: Crea el producto que recibe por el body. Hay que tener en cuenta que el **type** debe tener el valor *TSHIRT* y que el **size** sólo permite los valores [*L*, *M*, *S*]. Igualmente, si se lanza la aplicación y posteriormente se ejecutan los tests de aceptación, se poblará la base de datos con las entidades que venían de ejemplo en el enunciado de la prueba técnica. Como este endpoint no era necesario para la prueba técnica no se le han realizado tests, simplemente se ha utilizado para agilizar el proceso de creación de datos de pruebas.
- **GET /api/products**: Devuelve el listado ordenado por los criterios que se indican en el enunciado. Recibe los parámetros típicos de paginación (size y page) y los ratios que sirven para controlar el peso que se le da a cada criterio de puntación (salesScoreRatio y stockScoreRatio).
- **GET /api/products/query**: Esta petición sólo está disponible en la tag del repositorio **native-query-endpoint**. Este endpoint hace la misma función que el anterior, pero la query está realizada en nativo. El problema de realizar queries de mongo en nativo es que los repositorios pueden quedar muy extensos y sean difíciles de mantener. Por ello, se ha borrado y se utiliza una libreria para formar las queries de forma que pueda ser reutilizable cuando se implementen nuevos criterios de ordenación.
# Consideraciones de diseño
## Dominio

En cuanto al dominio se ha tomado la decisión de realizar una clase abstracta (Producto) y una clase normal (Camiseta), intuyendo que en un futuro habrá más tipos de productos, pero tendrán que ser instanciados en su clase específica y nunca como producto.

Para realizar la herencia en MongoDB, se ha optado por añadir un campo tipo que indique de qué tipo de producto se trata y poder crear el objeto en java consultando dicho campo.

Por otro lado, en el caso de camisetas, el único campo específico es la talla, que está almacenado en un enumerado los tipos que se permiten. Esta decisión se ha tomado porque en principio las tallas que se fabrican no deben cambiar constantemente, si no fuera el caso pero se quisiera seguir controlando que tallas son permitidas para cada producto, habría que almacenarlas en base de datos.

Además, para facilitar la consulta del stock, junto a cada talla se indicará el stock que hay para dicho tamaño.


## Algoritmo ordenación

El algoritmo de ordenación se ha diseñado para que se calculen dos puntuaciones (que serán ponderadas por los parámetros externos) y finalmente se sumen en un valor que será el utilizado para ordenar los productos. Las puntuaciones calculadas son:

1. **Puntuación por ventas**: Dado que las ventas ya son un número, se utiliza directamente como puntuación para ahorrarse procesamiento, por lo que no hay que hacer ningún cálculo extra, simplemente se sumará con el resto de puntuaciones.
2. **Puntuación por stock**: Por cada talla que tenga una prenda con stock mayor que 0, se sumará un punto. Si no se ponderán los criterios, es evidente que el criterio anterior tendrá más peso por norma general, ya que como máximo este criterio dará 3 puntos. Por lo que hay que tenerlo en cuenta para asignar los pesos.

Se ha utilizado una agregación en mongo para que se puedan calcular estas puntuaciones y, posteriormente, ordenar por el total. El problema que tiene esta agregación es que puede empeorar mucho su rendimiento en caso de que se añadan muchos criterios nuevos, ya que en algunos casos podría ser necesario tener que volver a recorrer todos los documentos para calcular una nueva puntuación.

En caso de que la consulta se hiciera muy lenta cuando hubiera muchos documentos y criterios, estaría la opción de persistir estas puntuaciones, aunque es preferible no hacerlo para evitar posibles inconsistencias en los datos.

Igualmente, para mejorar el rendimiento de la consulta, se han creado índices (*index*) encargados de optimizarla.

## DTO y Entities

A pesar de que pueden ser clases anémicas, se ha decidido utilizar DTOs y Entities, ya que es diferente como está definido el modelo de dominio que como deben guardarse los datos en mongo para agilizar las consultas y que los documentos sean más intuitivos. De igual forma, como hay varios value objects, si se enviara como respuesta en una petición a la API un objeto del modelo de dominio se ensuciaría mucho el JSON con claves que no aportan información.

Dado que el DTO y la Entity de producto si son exactamente iguales, podría haberse usado la misma clase, sin embargo, hay una vulnerabilidad en spring que no permite que la entidad sea recibida como parámetro directamente en el controlador, es por ello que se ha optado por tener estas clases sin lógica, pero a cambio se mejora la experiencia de usuario y, por supuesto, la seguridad.

Los mappers para pasar de un objeto a otro se han realizado manualmente, ya que al haber una herencia de una clase abstracta algunas librerías, como *mapstruct*, no realizaban el mapeo correctamente.

Debido a que hay polimorfismo y un producto podría ser una camiseta, pantalones, entre otras prendas, se ha optado por utilizar un switch para elegir a que objeto hay que transformar, no soy partidario de utilizar esta sentencia dado que suele ser poco mantenible, pero cuando hay polimorfismo es de los pocos casos donde creo que es justificable.

# Mejoras futuras

En cuanto a mejoras futuras para este mismo proyecto encontramos:

- Validación de los campos de paginación, actualmente el usuario podría indicar cualquier valor y traernos páginas enormes de base de datos.
- Mejorar tests de aceptación, hay muchos tests E2E pero la mayoría son para la creación de los datos y se podrían hacer tests más interesantes si tenemos el control del estado de la base de datos y jugamos con el peso de los criterios de ordenación.
- Mejorar la documentación de la API, sería interesante añadir más descripciones para que sea completamente autoexplicativa.

