/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.de.rutas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Eloy Barca
 */
public class Calculos {

    double[][] matriz;

    public boolean iniciarMatriz(File file) throws FileNotFoundException, IOException {
        boolean valor = false;
        //creamos un patron para revisar que la extension del archivo es correcta
        Pattern p = Pattern.compile("[A-Za-z0-9]+[.]{1}xls");
        //extraemos el nombre del archivo
        String nom = file.getName();
        //hacemos la comparacion
        Matcher m = p.matcher(nom);
        
        //si el nombre del archivo no coincide con el patron
        if (m.matches() == false) {
            //no se ejecuta nada mas en el metodo
        } else {
            //si la extension del archivo es valida
            //recuperamos la informacion del archivo
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = wb.getSheetAt(0);
            
            //comprobamos el numero de filas
            int rows = sheet.getLastRowNum();
                
            //como van a ser tabals cuadradas iniciamos la matriz con el valor
            //del numero de filas
            matriz = new double[rows + 1][rows + 1];
            
            //bucle for que va recorriendo la matriz y la tabla de excel 
            //para obtener y guardar los datos
            for (int i = 0; i < matriz.length; i++) {
                //extraemos la fila completa
                HSSFRow row = sheet.getRow(i);
                for (int j = 0; j < matriz.length; j++) {
                    //extraemos el valor de cada celda
                    HSSFCell cell = row.getCell(j);
                    double d = cell.getNumericCellValue();
                    matriz[i][j] = d;

                }
                valor = true;

            }
        }
        //regresamos el valor de la variable
        return valor;
    }

    public int[] calcularRuta(int ini) {
        //iniciamos el array donde guardaremos los puntos ya visitados
        int[] posic = new int[matriz.length];
        //iniciamos el array donde guardaremos la ruta
        int[] ruta = new int[matriz.length];

        //asignamos la posicion inicial de la matriz
        int posIni = ini - 1;

        //creamos una variable ptipo doble para guardar el tiempo
        double min = 10;
        //creamos una varibale tipo int para asignar la posicion del array
        int pos = posIni;
        //creamos una variable tipo int para guardar la siguiente posicion
        int npos = 0;

        //hacemos un bucle for para iniciar todas las posiciones del array posci
        //a -1 para evitar que esten a 0
        for (int i = 0; i < posic.length; i++) {
            posic[i] = -1;
        }

        //iniciamos la primera posicion del array posic al valor de la posicion 
        //de inicio
        posic[0] = posIni;

        //hecmos un bucle que se ejecute tantas veces como filas de array
        for (int i = 0; i < ruta.length; i++) {
            //hacemos un bucle que recorra las filas revisando los datos
            for (int j = 0; j < matriz[pos].length; j++) {

                //si i es igual a la ultima posicion ponemos el valor de min a 0
                if (i == posic.length - 1) {
                    min = 0;
                    break;
                }

                //si la posicion actual es igual a 0
                if (matriz[pos][j] == 0) {
                    //no se hace nada
                } else {
                    //si min es mayor que el valor de la posision actual
                    if (min > matriz[pos][j]) {
                        //comprobamos que el punto no este en el array de 
                        //los puntos visitados
                        if (comprobarPuntos(posic, j) == true) {
                            //continua la ejecucion del for
                        } else {
                            //cambiamos el valor de min por el nuevo valor
                            min = matriz[pos][j];
                            //añadimos al array posic el punto visitado
                            posic[i] = pos;
                            //cambiamos el valor del nuevo punto
                            npos = j;
                        }
                    }
                }
            }
            //introducimos en el array ruta el punto
            ruta[i] = pos + 1;
            //asignamos el nuevo punta a pos
            pos = npos;
            //reseteamos min
            min = 10;
        }

        return ruta;
    }

    public boolean comprobarPuntos(int[] arr, int num) {
        //variable booleana para comproba si el num esta en el array
        boolean comp = false;
        //bucle for que recorre el array
        for (int i = 0; i < arr.length; i++) {
            //si la posicion del array actual es igual a num
            if (arr[i] == num) {
                //ponemos comp a true
                comp = true;
                break;
            }
        }
        return comp;
    }

    public double calcularTiempo(int ini) {
        //iniciamos el array donde guardaremos los puntos ya visitados
        int[] posic = new int[matriz.length];
        //iniciamos el array donde guardaremos la ruta
        int[] ruta = new int[matriz.length];

        //asignamos la posicion inicial de la matriz
        int posIni = ini - 1;

        //creamos una variable ptipo doble para guardar el tiempo
        double min = 10;
        //creamos una varibale tipo int para asignar la posicion del array
        int pos = posIni;
        //creamos una variable tipo int para guardar la siguiente posicion
        int npos = 0;

        double tiempo = 0;

        //hacemos un bucle for para iniciar todas las posiciones del array posci
        //a -1 para evitar que esten a 0
        for (int i = 0; i < posic.length; i++) {
            posic[i] = -1;
        }

        //iniciamos la primera posicion del array posic al valor de la posicion 
        //de inicio
        posic[0] = posIni;

        //hecmos un bucle que se ejecute tantas veces como filas de array
        for (int i = 0; i < ruta.length; i++) {
            //hacemos un bucle que recorra las filas revisando los datos
            for (int j = 0; j < matriz[pos].length; j++) {

                //si i es igual a la ultima posicion ponemos el valor de min a 0
                if (i == posic.length - 1) {
                    min = 0;
                    break;
                }

                //si la posicion actual es igual a 0
                if (matriz[pos][j] == 0) {
                    //no se hace nada
                } else {
                    //si min es mayor que el valor de la posision actual
                    if (min > matriz[pos][j]) {
                        //comprobamos que el punto no este en el array de 
                        //los puntos visitados
                        if (comprobarPuntos(posic, j) == true) {
                            //continua la ejecucion del for
                        } else {
                            //cambiamos el valor de min por el nuevo valor
                            min = matriz[pos][j];
                            //añadimos al array posic el punto visitado
                            posic[i] = pos;
                            //cambiamos el valor del nuevo punto
                            npos = j;
                        }
                    }
                }
            }
            //introducimos en el array ruta el punto
            ruta[i] = pos + 1;
            //asignamos el nuevo punta a pos
            pos = npos;
            //sumamos el tiempo de la ruta
            tiempo = tiempo + min;
            //reseteamos min
            min = 10;
        }

        return tiempo;
    }
}
