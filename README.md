# OOP_Monopoly
devs: Rodrigo Rios Louro & Rocio Perez-Muñuzuri Fernandez & Jorge Piñeiro Ouviña  

proyect for OOP class of programing the game monopoly in Java


*to-do*:

    -seguir implementando funcionalidades

*Mejoras:*
    
    -correccion en setter de tipoID, solo hay 4 tipos disponibles
    -implementado metodo esTipoValido para comprobar si el tipo de avatar es valido antes de llamar a los constructores
    -constructor Jugador(args) puesto a private, olo se puede usar newJugador
    -implementado toString grupo
    -implementado que describa las casillas que se le piden
    -nuevo atributo boolean hipotecada en casilla para saber si una casilla esta hipotecada o no
    -nuevo metodo encontrarJugador que devuelve un jugador dando el nombre
    -implementado toString jugador para describir jugador segun funcionalidad 12


*----SIGUIENTE CAMBIO GRANDE A HACER-----*

Primero acabar funcionalidades, con esta arquiectura funciona pero hay codigo repetido

    -Gestionar dentro de Analizar Comando toda a parte de acciones para no repetir 2 veces la 
    estructura switch