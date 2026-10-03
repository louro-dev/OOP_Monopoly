# OOP_Monopoly
devs: Rodrigo Rios Louro & Rocio Perez-Muñuzuri Fernandez & Jorge Piñeiro Ouviña  
proyect for OOP class of programing the game monopoly in Java

Mejoras:

correccion concepto analizar comando: el switch para la accion se hará ahora en
MonopolyETSE para evitar modificar los atributos del metodo, y se utilizará el
método para saber que tipo de comando en el sentido de orden o fichero que contiene
ordenes. Lo devolerá como un entero.

CAMBIADO METODO EN MENU DE PRIVATE A PUBLIC. 

añadido getter y setter para el entero que guarda el ID del jugador con el turno en 
menu

se ha comenzado la implementacion de la aceptacion de comandos con un documento

posibilidad: hacer que AnalizarComando devuelva numero de 2 cifras si es comando
para saber que comando es y no usar un switch con Strings. Seria mas seguro en fallos 
de entrada por mayusculas, ya que dentro de este usamos toLoersCase ppara el 
analisis de las entradas.

Implementada funcion IniciarPartida: se encarga de leer el documento inicial y hacer el
tratamiento de los comandos que hay dentro de el, inicializa la banca y le da todas las
propiedades y inicializa el tablero.

Implementamos dar de alta un jugador, bien pidiendolo por comando o dando la orden desde
el documento Inicial. 

Falta implementar que el documento se pueda pasar por los args, por ahora hay que pedirlo

Se ha añadido un .txt de prueba para que se vayan pidiendo poco a poco desde ese documento
las funciones implementadas para probarlas

Funciona con documenti inicial, crea jugadores correctamente 1 a 1 pasandoselo por linea de
comandos y lanza los dados para que el primero avance

Creados toString para imprimir jugadores, imprimir avatares.

Implementada funcionalidad que lista jugadores.