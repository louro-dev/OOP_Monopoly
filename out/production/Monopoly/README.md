# OOP_Monopoly
devs: Rodrigo Rios Louro & Rocio Perez-Muñuzuri Fernandez & Jorge Piñeiro Ouviña  

proyect for OOP class of programing the game monopoly in Java


*----SIGUIENTE CAMBIO GRANDE A HACER-----*
Primero acabar funcionalidades, con esta arquiectura funciona pero hay codigo repetido

    -Gestionar dentro de Analizar Comando toda a parte de acciones para no repetir 2 veces la 
    estructura switch

*to-do*:

    -ver porque se imprime Jugador tal ha salido de la carcel en la tirada 2 y no en la 3

*Mejoras:*

    -Ahora funciona con comandos de 2 palabras

    -Anhadida funcionalidad para acabar turno

    -cambiada logica de los dados: ahora se declaran dentro de menu y solo se llaman a modificar los 
     valores dentro de lanzar dados, sin crear instancias auxiliares cada vez

    -Anhadida condicion para setter de dados

    -Anhadida funcionalidad dados deterministas

    -implementada logica de tiradas si el jugador esta en la carcel

  *CAMBIO DE LOGICA GORDO:*

    -gestionarase toda a partida desde a clase menu, en MonopolyETSE que solo este o menu=new Menu();


