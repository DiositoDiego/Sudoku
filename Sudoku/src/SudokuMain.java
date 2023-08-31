/*
 * PROGRAMA 100% HECHO POR: DIEGO ALBABERA FIERRO
 *
 * Este es mi primera vez intentando hacer un programa por 'módulos'(?),
 * primera vez en la que comparto un código mío públicamente y la primera
 * vez en la que documento todo el código con comentarios.
 *
 * REGLAS DEL SUDOKU (por si alguien no las conoce):
 * Consiste en una cuadricula de 9x9 cuadritos separada en
 * pequeñas cuadrículas de 3x3, cuyo objetivo es llenar
 * todos los espacios con números, los cuales no pueden ser
 * repetidos en la misma fila, columna o cuadrícula de 3x3.
 * El llenado de los cuadritos o espacios en este programa
 * es por medio de 'coordenadas' llamadas filas y columnas,
 * en las que, después de seleccionar la fila y columna, se
 * debe ingresar el número.
*/

import java.util.Scanner;

public class SudokuMain {
    static int[][] numeros = new int[9][9];
    static int dificultad;
    static boolean fila3x3completa = false;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        //en el método main, simplemente se manda a llamar al método iniciar
        //para que el juego inicie mostrando primeramente la dificultad a seleccionar
        iniciar();
    }

    //en este método se inicia el juego y se seleccióna la dificultad desde el 1 hasta el 6
    public static void iniciar(){
        in.useDelimiter("\n");
        boolean bandera = true;
        do {
            System.out.println("Selecciona la dificultad\n1. Solucionado\n2. Para bebes\n3. Super facil\n4. Facil\n5. Medio\n6. Dificil");
            dificultad = in.nextInt();
            switch (dificultad){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    bandera = false;
                    break;
                default:
                    System.out.println("Selecciona un numero valido");
            }
        }while(bandera);

        //una vez seleccionada la dificultad desde el menú, ahora se seleccionará internamente
        //en el programa, llamando al método 'seleccionarDificultad()' con el parámetro 'dificultad'
        seleccionarDificultad(dificultad);
    }

    //aquí se hace la selección de cuantos cuadritos van a ser rellenados automáticamente
    public static void seleccionarDificultad(int dificultad){
        int iterador = 0;
        switch (dificultad){
            case 1:
                iterador = 81;
                break;
            case 2:
                iterador = 75;
                break;
            case 3:
                iterador = 60;
                break;
            case 4:
                iterador = 45;
                break;
            case 5:
                iterador = 35;
                break;
            case 6:
                iterador = 20;
                break;
            default:
                System.out.println("Selecciona un numero valido");
        }
        //dependiendo del número correspondiente a la dificultad, la variable 'iterador'
        //se setea en la cantidad predeterminada de cuadritos solucionados automáticamente
        //por el programa

        //se manda a llamar al método para rellenar automáticamente el sudoku
        rellenar(iterador);

        //el método 'continuar()' inicia el rellenado del sudoku por teclado
        continuar();
    }

    //método para rellenar automáticamente el sudoku
    public static void rellenar(int iterador){
        //aquí se rellenan por filas de 3x3
        //se rellena cada conjunto de 3x3 para un relleno más eficaz
        rellenar3x3(0,0);
        rellenar3x3(0,3);
        rellenar3x3(0,6);

        //la variable sirve para controlar el método que borra y rellena una fila
        //de 3x3, marca falso para indicar que la siguiente fila está vacía
        fila3x3completa = false;

        rellenar3x3(3,0);
        rellenar3x3(3,3);
        rellenar3x3(3,6);

        fila3x3completa = false;

        rellenar3x3(6,0);
        rellenar3x3(6,3);
        rellenar3x3(6,6);

        fila3x3completa = false;

        //una vez que se hayan llenado las 9 cuadrículas de 3x3, se empezarán a quitar
        //numeros con el siguiente método
        quitarNumeros(iterador);
    }

    //método para iniciar el sudoku
    private static void continuar() {
        int num, fila, columna;
        boolean bandera = true;
        imprimirMatriz();
        //se manda a llamar al método para imprimir la matríz inicialmente

        validarGanador();
        //aunque un método sea de tipo boleano, se puede mandar a llamar como si fuera
        //de tipo void y este valida si el usuario ganó. Si la dificultad del sudoku fue 'solucionado'
        //quiere decir que desde un principio el usuario ya habrá ganado

        do {
            //el primer dowhile sirve para seguir pidiendo los 3 valores al usuario:
            //el número a ingresar, la fila (posición X) y la columna (posición Y)

            do {
                //el segundo dowhile sirve para controlar que el usuario escriba un número válido

                bandera = true;
                //la variable bandera sirve para controlar que el usuario haya ingresado un número válido

                System.out.println("Escribe la fila (1 - 9)");
                fila = in.nextInt();
                if (fila >= 1 && fila <= 9) {
                    //la condición sirve para validar que el usuario haya escrito un número entre
                    //el 1 y el 9, en caso de que así sea, la variable bandera se setea a 'false'
                    bandera = false;
                } else {
                    System.out.println("Error, numero no valido, intentalo de nuevo");
                    //en caso de que no haya ingresado un número entre el 1 y el 9, se pedirá
                    //volverlo a escribir hasta que ingrese uno válido
                }
            } while (bandera);
            do {
                bandera = true;
                System.out.println("Escribe la columna (1 - 9)");
                columna = in.nextInt();
                if (columna >= 1 && columna <= 9) {
                    bandera = false;
                } else {
                    System.out.println("Error, numero no valido, intentalo de nuevo");
                }
            } while (bandera);
            do {
                bandera = true;
                System.out.println("Escribe el numero que quieras agregar (1 - 9)");
                num = in.nextInt();
                if (num >= 0 && num <= 9) {
                    bandera = false;
                } else {
                    System.out.println("Error, numero no valido, intentalo de nuevo");
                }
            } while (bandera);
            if (!insertar(num, fila, columna)){
                //cuando el usuario ingrese los valores correspondientes, se validará si sí se insertó
                //el número en la matriz, siguiendo las validaciones correspondientes,
                //en caso de que así sea va a imprimir la matriz con el método 'imprimirMatriz()'
                imprimirMatriz();
            }

            //el dowhile se repetirá hasta que 'validarGanador()' retorne 'true'
        }while (!validarGanador());
    }

    //método para validar si el usuario ganó o no
    public static boolean validarGanador(){
        for (int i = 0; i < numeros.length; i++){
            //el primer for recorre todas las filas

            for (int j = 0; j < numeros[i].length; j++){
                //el segundo for recorre todas las columnas

                if (numeros[i][j] == 0){
                    //la condición valida si número en la posición actual de los iteradores
                    //es igual a 0, en caso de que así sea, significa que aún faltan casillas por rellenar
                    //y, por tanto, el usuario aún no ha ganado, retornando así 'false'
                    return false;
                }
            }
        }
        System.out.println("FELICIDADES, GANASTE!!");
        System.exit(0);
        return true;
        //en caso de que el usuario haya ganado, es decir, que los ciclos anteriores hayan
        //recorrido toda la matriz y no haya retornado 'false', dará aviso al usuario que ganó
        //y finalizará el proceso con el método 'System.exit(0)'
    }

    //método correspondiente para quitar numeros
    public static void quitarNumeros(int iterador){
        int fila, columna, limite = 81 - iterador;
        //se declaran 3 variables: fila, columna y límite
        //límite sera el límite que tendra el for

        for (int i = 0; i < limite; i++){
            //en este for simplemente se genera una fila y una columna
            //random por cada iteración para borrar el número en esa posición
            //con el método 'eliminarNumero(...)'
            fila = (int) (Math.random() * ((9 - 1) + 1)) + 1;
            columna = (int) (Math.random() * ((9 - 1) + 1)) + 1;
            eliminarNumero(fila-1, columna-1);
        }
    }

    //método para borrar un numero
    private static void eliminarNumero(int fila, int columna) {
        //simplemente recibe la fila y la columna para setear el número
        //en esa posición a 0
        numeros[fila][columna] = 0;
    }

    //método para insertar por la elección de la dificultad
    public static boolean insertarPorDificultad(int num, int fila, int columna){
        //primero hace las correspondientes validaciones de 'num', es decir
        //que no se repita horizontal, vertical y en la cuadrícula de 3x3
        if (validarHorizontal(num, fila, true) && validarVertical(num, columna, true) && validarDe3x3(num, fila, columna, true)){

            numeros[fila][columna] = num;
            //si el numero no se repite en ninguno de los 3 casos anteriormente mencionados
            //se insertará en la posición indicada y retornará 'true'
            return true;
        } else {

            //en caso de que el número sí se repita, retornara 'false'
            return false;
        }
    }

    //método para insertar los números desde teclado
    public static boolean insertar(int num, int fila, int columna){
        //primero hace las correspondientes validaciones de 'num', es decir
        //que no se repita horizontal, vertical y en la cuadrícula de 3x3
        if (validarHorizontal(num, fila-1, false) && validarVertical(num, columna-1, false) && validarDe3x3(num, fila-1, columna-1, false)){

            numeros[fila-1][columna-1] = num;
            //si el numero no se repite en ninguno de los 3 casos anteriormente mencionados
            //se insertará en la posición indicada, imprimirá la matríz actualizada y retornará 'true'
            //se pone 'fila-1' y 'columna-1' porque las posiciones el usuario las inserta del 1 al 9,
            //pero internamente se debe manejar del 0 al 8
            imprimirMatriz(fila-1, columna-1);
            return true;
        } else {

            //en caso de que el número sí se repita, retornara 'false'
            return false;
        }
    }

    //método correspondiente para imprimir la matríz
    public static void imprimirMatriz(){
        numerosColumnas();
        //con este método se imprimen los números que indican el número de columna

        for (int i = 0; i < numeros.length; i++){
            //este for controla las filas

            System.out.print((i+1)+ " ");
            //se imprime la iteración + 1 para indicarle el número de cada fila al usuario

            for (int j = 0; j < numeros[i].length; j++){
                //este for controla las columnas

                System.out.print("[" + numeros[i][j] + "]");
                //se imprime un corchete al inicio y al final, porque se necesita separar la posición
                //de cada número y yo lo hice con corchetes de tal forma que quedara así, ejemplo: [1]
                //IMPORTANTE: se usa '...print' sin el 'ln' porque cada que imprima un número no se debe
                //de dar un salto de línea para imprimir el segundo, haciendo que quede así, por ejemplo: [1][2]...

                if ((j+1)%3 == 0){
                    //esta condición sirve para separar las columnas en grupos de 3
                    //es decir, cada vez que la iteración + 1 sea múltiplo de 3, se
                    //imprimirá un espacio, de tal forma que quede así, ejemplo: [1][2][3]   [4]...
                    System.out.print("   ");
                }
            }
            //cada que se termine de imprimir una columna se imprime un salto de línea
            //para continuar con la segunda columna
            System.out.println("");

            if ((i+1)%3 == 0){
                //esta condición sirve para separar las filas en grupos de 3
                //es decir, cada vez que la iteración + 1 sea múltiplo de 3, se
                //imprimirá un salto de línea, de tal forma que quede así, ejemplo:
                //[1]
                //[2]
                //[3]
                //
                //[4]
                //...
                System.out.print("\n");
            }
        }
    }

    //método para imprimir la matríz, con la diferencia de que imprime
    //el último número ingresado por teclado en color ROJO
    public static void imprimirMatriz(int fila, int columna){
        numerosColumnas();
        //con este método se imprimen los numeros que indícan el número de columnas

        for (int i = 0; i < numeros.length; i++){
            //este for controla las filas

            System.out.print((i+1)+ " ");
            //se imprime la iteración + 1 para indicarle el número de cada fila al usuario

            for (int j = 0; j < numeros[i].length; j++){
                //este for controla las columnas

                System.out.print("[");
                //se imprime un corchete porque se necesita separar la posición de cada número
                //y yo lo hice usando []

                if (i == fila && j == columna) {
                    //la condición sirve para verificar si la iteración va en la
                    //posición del último número ingresado, en caso de que así sea
                    //se imprimirá de color rojo el número con ayuda de 'System.ERR.print'
                    //sin el 'ln' para que imprima los caracteres siguientes en el mismo
                    //renglón
                    System.err.print(numeros[i][j]);

                    try {
                        //el trycatch sirve para cachar la posible excepción del método 'Thread.sleep(...)'
                        //y así controlar el paro temporal del hilo de ejecución del programa.
                        //Se detiene por 50 milisegundos, ya que, el 'System.err...' imprime con retraso porque
                        //este está dedicado para imprimir errores (razón de que tenga el 'err' en lugar del 'out'),
                        //así mismo provoca que se desordene la impresión de la matríz, por tanto, se necesita esperar
                        //después de imprimir el número en color rojo
                        Thread.sleep(50);
                    }catch (InterruptedException e){
                        System.err.println(e);
                    }
                } else {
                    //en caso de que la posición de la iteración no sea la misma que
                    //la del último número ingresado por teclado, se imprimirá del color normal
                    System.out.print(numeros[i][j]);
                }

                System.out.print("]");
                //por último se imprimirá el corchete de cierre del número hacíendo que quede
                //por ejemplo: [1]

                if ((j+1)%3 == 0){
                    //esta condición sirve para separar las columnas en grupos de 3
                    //es decir, cada vez que la iteración + 1 sea múltiplo de 3, se
                    //imprimirá un espacio, de tal forma que quede así, ejemplo: [1][2][3]   [4]...
                    System.out.print("   ");
                }
            }
            //cada que se termine de imprimir una columna se imprime un salto de línea
            //para continuar con la segunda columna
            System.out.println("");

            if ((i+1)%3 == 0){
                //esta condición sirve para separar las filas en grupos de 3
                //es decir, cada vez que la iteración + 1 sea múltiplo de 3, se
                //imprimirá un salto de línea, de tal forma que quede así, ejemplo:
                //[1]
                //[2]
                //[3]
                //
                //[4]
                //...
                System.out.print("\n");
            }
        }
    }

    //método para imprimir el indicador del número de las columnas
    public static void numerosColumnas() {
        System.out.print("   ");
        //inicialmente se imprime un espacio para que el primer número esté alineado con el
        //primer valor de la matriz

        for (int i = 0; i < numeros.length; i++){
            //este for sirve para recorrer del 0 al 8

            System.out.print((i+1) + "  ");
            //sirve para imprimir el número de la iteración + 1 junto a un espacio
            //de tal forma que quede así, por ejemplo: 1  2  3...

            if ((i+1)%3 == 0){
                //esta condición sirve para separar las columnas en grupos de 3
                //es decir, cada vez que la iteración + 1 sea múltiplo de 3, se
                //imprimirá un espacio, de tal forma que quede así, ejemplo: 1  2  3    4...
                System.out.print("   ");
            }
        }

        System.out.println("");
        //cuando termine de imprimir los números, se dará un salto de línea
        //para empezar a imprimir la matriz

    }

    //método para validar que un numero no se repita horizontalmente
    public static boolean validarHorizontal(int num, int fila, boolean porDificultad){
        //recibe 3 parámetros
        //num: es el número a validar
        //fila: es la fila por la cual se va a recorrer y comparar el número
        //porDificultad: indica si se está validando cuando el número fue ingresado
        //por teclado o si fue ingresado de manera aleatoria al rellenar la matriz

        for (int i = 0; i < numeros.length; i++){
            //el for sirve para (en este caso) recorrer la fila

            if (num == numeros[fila][i]){
                //en la condición se valida si el número se repite en la
                //primera, segunda, tercera, cuarta... fila

                if (!porDificultad){
                    //esta condición valida si el número fue ingresado por teclado, en caso de que así
                    //sea, imprimirá al usuario indicándole que el número ya está en la fila
                    System.err.println("El numero " + num + " ya se encuentra en la fila " + (fila+1));
                    try{
                        Thread.sleep(50);
                    }catch(InterruptedException e){
                        System.err.println(e);
                    }
                }

                //en caso de que sí se repita, retornará 'false'
                return false;
            }
        }

        //en caso de que no se repita, retornará 'true'
        return true;
    }

    //método para validar que un numero no se repita verticalmente
    public static boolean validarVertical(int num, int columna, boolean porDificultad){
        //recibe 3 parámetros
        //num: es el número a validar
        //columna: es la columna por la cual se va a recorrer y comparar el número
        //porDificultad: indica si se está validando cuando el número fue ingresado
        //por teclado o si fue ingresado de manera aleatoria al rellenar la matriz

        for (int i = 0; i < numeros.length; i++){
            //el for sirve para (en este caso) recorrer la columna

            if (num == numeros[i][columna]){
                //en la condición se valida si el número se repite en la
                //primera, segunda, tercera, cuarta... columna

                if (!porDificultad) {
                    //esta condición valida si el número fue ingresado por teclado, en caso de que así
                    //sea, imprimirá al usuario indicándole que el número ya está en la columna
                    System.out.println("El numero " + num + " ya se encuentra en la columna " + (columna+1));
                }

                //en caso de que sí se repita, retornará 'false'
                return false;
            }
        }

        //en caso de que no se repita, retornará 'true'
        return true;
    }

    //método para obtener el valor inicial de la cuadrícula en la que se va a
    //validar si un número no se repite
    public static boolean validarDe3x3(int num, int fila, int columna, boolean porDificultad){
        //recibe 4 parámetros
        //num: es el número a validar
        //columna: es la columna por la cual se va a recorrer y comparar el número
        //fila: es la fila por la cual se va a recorrer y comparar el número
        //porDificultad: indica si se está validando cuando el número fue ingresado
        //por teclado o si fue ingresado de manera aleatoria al rellenar la matriz

        if (fila >= 0 && fila < 3) {
            //condición que valida si la fila recibida está en la primera cuadrícula 3x3
            //es decir, si se encuentra entre el 0 y el 2

            if (columna >= 0 && columna < 3) {
                //condición que valida si la columna recibida está en la primera cuadrícula 3x3
                //es decir, si se encuentra entre el 0 y el 2

                fila = 0;
                columna = 0;
                //la fila se setea a 0, porque es la primera posición horizontal de la primera cuadrícula 3x3
                //la columna se setea a 0, porque es la primera posición vertical de la primera cuadrícula 3x3

                return validar3x3(num, fila, columna, porDificultad);
                //finalmente retorna el método 'validar3x3', el cual valida si el número se repite
                //en la primera cuadrícula 3x3

            } else if (columna >= 3 && columna < 6) {
                //condición que valida si la columna recibida está en la segunda cuadrícula 3x3
                //es decir, si se encuentra entre el 3 y el 5

                fila = 0;
                columna = 3;
                //la fila se setea a 0, porque es la primera posición horizontal de la segunda cuadrícula 3x3
                //la columna se setea a 3, porque es la primera posición vertical de la segunda cuadrícula 3x3

                return validar3x3(num,fila,columna, porDificultad);
                //finalmente retorna el método 'validar3x3', el cual valida si el número se repite
                //en la segunda cuadrícula 3x3

            } else if (columna >= 6 && columna < 9) {
                //condición que valida si la columna recibida está en la tercera cuadrícula 3x3
                //es decir, si se encuentra entre el 6 y el 8

                fila = 0;
                columna = 6;
                //la fila se setea a 0, porque es la primera posición horizontal de la tercera cuadrícula 3x3
                //la columna se setea a 6, porque es la primera posición vertical de la tercera cuadrícula 3x3

                return validar3x3(num,fila,columna, porDificultad);
                //finalmente retorna el método 'validar3x3', el cual valida si el número se repite
                //en la tercera cuadrícula 3x3
            }

        } else if (fila >= 3 && fila < 6) {
            //condición que valida si la fila recibida está en la primera cuadrícula 3x3
            //es decir, si se encuentra entre el 3 y el 5

            if (columna >= 0 && columna < 3) {
                fila = 3;
                columna = 0;
                return validar3x3(num, fila, columna, porDificultad);
            } else if (columna >= 3 && columna < 6) {
                fila = 3;
                columna = 3;
                return validar3x3(num,fila,columna, porDificultad);
            } else if (columna >= 6 && columna < 9) {
                fila = 3;
                columna = 6;
                return validar3x3(num,fila,columna, porDificultad);
            }
        } else if (fila >= 6 && fila < 9) {
            //condición que valida si la fila recibida está en la primera cuadrícula 3x3
            //es decir, si se encuentra entre el 6 y el 8

            if (columna >= 0 && columna < 3) {
                fila = 6;
                columna = 0;
                return validar3x3(num, fila, columna, porDificultad);
            } else if (columna >= 3 && columna < 6) {
                fila = 6;
                columna = 3;
                return validar3x3(num,fila,columna, porDificultad);
            } else if (columna >= 6 && columna < 9) {
                fila = 6;
                columna = 6;
                return validar3x3(num,fila,columna, porDificultad);
            }
        }

        //finalmente se retorna falso, aunque es imposible que llegue a
        //ejecutarse esta línea de código, pero se tiene que dejar porque
        //el método así lo requiere
        return false;
    }

    public static boolean validar3x3(int num, int fila, int columna, boolean porDificultad){
        //recibe 4 parámetros
        //num: es el número a validar
        //columna: es la columna por la cual se va a recorrer y comparar el número
        //fila: es la fila por la cual se va a recorrer y comparar el número
        //porDificultad: indica si se está validando cuando el número fue ingresado
        //por teclado o si fue ingresado de manera aleatoria al rellenar la matriz

        int l = fila + 3, m = columna + 3;
        //estas variables sirven para establecer los límites de los for
        //se les suma 3, porque cada cuadrícula tiene 3 filas x 3 columnas
        //y las variables fila y columna se encuentran en la primera posición
        //de cada cuadrícula

        for (int i = fila; i < l; i++) {
            //este for sirve para recorrer la cuadrícula verticalmente

            for (int k = columna; k < m; k++) {
                //este for sirve para recorrer la cuadrícula horizontalmente

                if (num == numeros[i][k]) {
                    //condición que valida si el numero que fue pasado por parámetro
                    //es igual al número en la posición que se está iterando

                    if (!porDificultad) {
                        //en caso de que la validación se haya hecho por teclado
                        //indicará al usuario que el número se encuentra repetido
                        //en la cuadrícula 3x3
                        System.out.println("El numero " + num + " se repite en 3 x 3");
                    }

                    //en caso de que el número se repita, retornará 'false'
                    return false;
                }
            }
        }

        //en caso de que no haya regresado 'false' aún y ya haya recorrido toda la cuadrícula
        //quiere decir que ya está llena, por tanto, regresará un 'true'
        return true;
    }

    public static boolean rellenar3x3(int fila, int columna){
        int l = fila + 3, m = columna + 3;
        //estas variables sirven para establecer los límites de los for
        //se les suma 3, porque cada cuadrícula tiene 3 filas x 3 columnas
        //y las variables fila y columna se encuentran en la primera posición
        //de cada columna

        int num;
        int probabilidad = 0, reinicio= 0;
        boolean bandera = false;
        //el primer for sirve para iterar las filas -
        for (int i = fila; i < l; i++) {

            //la condition detiene cada ciclo si la fila de 3x3 ya esta completa
            if (fila3x3completa){
                break;
            }

            //el segundo for sirve para iterar las columnas |
            for (int k = columna; k < m; k++) {
                if (fila3x3completa){
                    break;
                }
                do {
                    if (fila3x3completa){
                        break;
                    }
                    //el do while sirve para generar e insertar números random,
                    //si se repite algún número en vertical, horizontal o 3x3
                    //se vuelve a generar un número random para intentar insertarlo
                    //en la misma posición en la que estaba
                    num = (int) (Math.random() * ((9 - 1) + 1)) + 1;

                    //la variable 'probabilidad' sirve para controlar cuantas veces se
                    //intento insertar un número random en una posición
                    probabilidad++;
                    if (probabilidad > (fila + columna + l + m) + 18){
                        //calculo que me saque de la cola, pero que se incrementa
                        //dependiendo de las filas y columnas. Si 'probabilidad'
                        //pasa del límite establecido, se borrará la cuadrícula 3x3
                        borrar3x3(fila, columna);

                        i = fila;
                        k = columna;
                        //las variables controladoras de los for se reinician para
                        //llenar la cuadrícula 3x3 desde 0

                        probabilidad = 0;
                        //'probabilidad' se setea a 0, porque sino siempre se va a cumplir
                        //la condición de arriba, haciendo que se borre infinitamente la
                        //cuadricula 3x3

                        reinicio++;
                        //'reincicio' sirve para controlar cuantas veces se
                        //borró la cuadrícula 3x3, este sirve por si llega
                        //un punto en el que, cualquier número que inserte
                        //incumpla las condiciones y se repita en cualquiera de las 3 formas

                    } else if (reinicio > l+m){
                        //otro calculo que me saque de la cola
                        //en caso de que se haya reiniciado mas veces del limite establecido
                        //se va a borrar la fila 3x3 entera
                        borrarFila3x3(fila);
                    }

                    //el dowhile siempre se repetirá mientras no se haya insertado y validad
                    //la cuadrícula 3x3 siga sin rellenar (empiezo a pensar que '&& !validar3x3relleno(...)' es innecesario)
                }while(!insertarPorDificultad(num, i, k) && !validar3x3relleno(fila, columna));

                bandera = true;
                //'bandera' sirve solo para tener un valor a retornar al final de los ciclos

                probabilidad = 0;
                //cuando haya insertado un número el contador 'probabilidad' se setea a 0
                //para empezar un conteo nuevo para el siguiente número de la siguiente posición
            }
        }
        return bandera;
        //finalmente el método requiere retornar algún valor boleano, por tanto, se
        //retorna el valor de la variable 'bandera'
    }

    private static void borrarFila3x3(int fila) {
        //método correspondiente para borrar TODA una fila de 3x3

        int columna = 0;
        //la columna se inicializa en 0, porque siempre iniciará el borrado
        //desde la primera columna

        //las condiciones sirven para validar desde qué fila de 3x3
        //se empezará a borrar
        if (fila >= 0 && fila < 3) {

            fila = 0;
            //la fila se setea a 0, porque si es la primera fila de 3x3,
            //se borrará desde el primer valor de cada cuadrícula

            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);

            columna = 3;
            //la columna ahora vale 3 porque es la posición del primer número
            //de la segunda cuadrícula 3x3

            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);

            columna = 6;
            //la columna ahora vale 6 porque es la posición del primer número
            //de la tercera cuadrícula 3x3

            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
        } else if (fila >= 3 && fila < 6) {
            fila = 3;
            columna = 0;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
            columna = 3;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
            columna = 6;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
        } else if (fila >= 6 && fila < 9) {
            fila = 6;
            columna = 0;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
            columna = 3;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
            columna = 6;
            borrar3x3(fila, columna);
            rellenar3x3(fila, columna);
        }

        //la variable 'fila3x3completa' se vuelve verdadera porque quiere decir que
        //ya se borró y se rellenó la fila 3x3
        fila3x3completa = true;
    }

    //método correspondiente para borrar una cuadrícula 3x3
    private static void borrar3x3(int fila, int columna) {
        int l = fila + 3, m = columna + 3;
        //estas variables sirven para establecer los límites de los for
        //se les suma 3, porque cada cuadrícula tiene 3 filas x 3 columnas
        //y las variables fila y columna se encuentran en la primera posición
        //de cada cuadrícula

        for (int i = fila; i < l; i++) {
            for (int k = columna; k < m; k++) {
                //estos for sirven para recorrer cada posición dentro de la cuadrícula de 3x3

                numeros[i][k] = 0;
                //cada posición que haya recorrido tendrá el valor 0
            }
        }
    }

    //método correspondiente para validar que toda una cuadrícula 3x3 esté completamente
    //rellena con números y no con 0
    public static boolean validar3x3relleno(int fila, int columna){
        int l = fila + 3, m = columna + 3;
        //estas variables sirven para establecer los límites de los for
        //se les suma 3, porque cada cuadrícula tiene 3 filas x 3 columnas
        //y las variables fila y columna se encuentran en la primera posición
        //de cada cuadrícula

        for (int i = fila; i < l; i++){
            for (int j = columna; j < m; j++){
                //estos for sirven para recorrer cada posición dentro de la cuadrícula de 3x3

                if (numeros[i][j] == 0){
                    //con esta condición se valida si posición actual tiene un número 0
                    //en caso de que cualquier posición de la cuadrícula 3x3 tenga un 0
                    //significa que aún no está llena y, por tanto, regresará un 'false'
                    return false;
                }
            }
        }

        //en caso de que no haya regresado 'false' aún y ya haya recorrido toda la cuadrícula
        //quiere decir que ya está llena, por tanto, regresará un 'true'
        return true;
    }
}