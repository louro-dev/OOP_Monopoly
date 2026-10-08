# OOP_Monopoly
devs: Rodrigo Rios Louro & Rocio Perez-Muñuzuri Fernandez & Jorge Piñeiro Ouviña  

proyect for OOP class of programing the game monopoly in Java


*to-do*:

    -implementar o valor das propieades segun as vas subindo de nivel para imprimilo co toString de Casilla
    -seguir implementando funcionalidades

*Mejoras:*

    -funciona salir de carcel al sacar dobles
    -funciona salir de carcel por numero de tiradas e interaccionar con el menu
    -implementada opcion para salir de la carcel pagando. Solo se imprime en el menu si el jugador esta encarcelado
    -implementado metodo esSolvente para saber si un jugador tiene fondos para pagar algo al llamar a sumarFortuna con valor megativo
    -sumarFortuna devuelve ahora Boolean par que cuando se usa se devuelva si es solvente o no
    -Cambiados todos los sumarFortuna para que si sale false pare lo que este haciendo
    -implementado toString de los dados
    -Imprimir valor de los dados cuando se invoca a lanzar dados
    -Refactorización función encarcelar
    -Usamor metodo encarcelar en el metodo que analiza las casillas en las que se cae para encarcelar



*----SIGUIENTE CAMBIO GRANDE A HACER-----*

Primero acabar funcionalidades, con esta arquiectura funciona pero hay codigo repetido

    -Gestionar dentro de Analizar Comando toda a parte de acciones para no repetir 2 veces la 
    estructura switch