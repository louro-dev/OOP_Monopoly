# OOP_Monopoly
devs: Rodrigo Rios Louro & Rocio Perez-Muñuzuri Fernandez & Jorge Piñeiro Ouviña  

proyect for OOP class of programing the game monopoly in Java


*to-do*:

*Mejoras:*

    -corregido suma de gastos al comprar casilla
    -revisados algunos warnings
    -uso de la constante FACTOR_SERVICIO en el metodo analizar casilla

*----SIGUIENTE CAMBIO GRANDE A HACER-----*

Primero acabar funcionalidades, con esta arquiectura funciona pero hay codigo repetido

    -Gestionar dentro de Analizar Comando toda a parte de acciones para no repetir 2 veces la 
    estructura switch

    -En vez de ter os cases brutales, cada funcionalidad ten un metodo propio xa definido. Pasar o codigo cos cases
    ao metodo, e chamar a ese metodo desde analizar comando