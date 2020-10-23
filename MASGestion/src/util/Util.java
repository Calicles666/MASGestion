/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Abraham
 */
public class Util {
    // Atributos estÃ¡ticos

    // Cadena con las letras posibles del DNI ordenados para el cÃ¡lculo de DNI
    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
    // Atributos de objeto
    private int numeroDni;

    // MÃ©todos
    public String getNif() {
        // Variables locales
        String cadenaNif = null;   // NIF con letra para devolver. Si no se puede calcular una letra correctamente
        //para el objeto dni en cuestiÃ³n, se devolverÃ¡ null.
        char letraNif = ' ';      // Letra del nÃºmero de NIF calculado. La inicializamos con un espacio en blanco
        boolean letraCorrecta = true;
        // CÃ¡lculo de la letra del NIF 
        try {
            letraNif = calcularLetraNif(this.numeroDni);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            letraCorrecta = false;
        }
        if (letraCorrecta) {
            // ConstrucciÃ³n de la cadena del DNI: nÃºmero + letra
            cadenaNif = Integer.toString(numeroDni) + String.valueOf(letraNif);
        }
        // DevoluciÃ³n del resultado
        return cadenaNif;
    }

    public int getDni() {
        return this.numeroDni;
    }

    public void setNumeroDni(String nif) throws Exception {
        // lo primero, comprobar que realmente se le ha pasado un argumento al mÃ©todo
        if (nif != null) {
            if (validarNif(nif)) { // Valor vÃ¡lido: lo almacenamos
                this.numeroDni = getNumeroDni(nif);
            } else { // Valor invÃ¡lido: lanzamos una excepciÃ³n
                throw new Exception("NIF invÃ¡lido: " + nif);
            }
        } else {
            throw new Exception("No se ha proporcinado ningÃºn NIF.");
        }
    }

    public void setNumeroDni(int numeroDni) throws Exception {

        // ComprobaciÃ³n de rangos: debe tener 8 dÃ­gitos, pero sÃ³lo es obligado que al menos el de las unidades sea distinto de cero.
        // Si el dni tiene menos de 8 dÃ­gitos, los demÃ¡s se completarÃ¡n con ceros.
        if (numeroDni > 0 && numeroDni <= 99999999) {
            this.numeroDni = numeroDni; // Valor vÃ¡lido: lo almacenamos
        } else { // Valor invÃ¡lido: lanzamos una excepciÃ³n
            throw new Exception("DNI invÃ¡lido. Debe tener como mÃ¡ximo 8 dÃ­gitos significativos: " + String.valueOf(numeroDni));
        }
    }

    private static char calcularLetraNif(int numeroDni) throws Exception {
        char letra = ' ';

        // Si en mÃºmero estÃ¡ en el rango adecuado, procedemos al cÃ¡lculo de la letra NIF
        if (numeroDni > 0 && numeroDni <= 99999999) {
            letra = LETRAS_DNI.charAt(numeroDni % 23);
            // En caso contrario, valor invÃ¡lido: lanzamos una excepciÃ³n
        } else {
            throw new Exception("DNI invÃ¡lido. Debe tener como mÃ¡ximo 8 dÃ­gitos significativos: " + String.valueOf(numeroDni));
        }
        // DevoluciÃ³n de la letra NIF
        return letra;
    }

    private static char getLetraNif(String nif) throws Exception {
        char letra = ' ';
        //Lo primero, comprobar que se le pasa un parÃ¡metro, distinto de la cadena vacÃ­a.
        if (nif != null && !nif.equals("")) {
            letra = nif.charAt(nif.length() - 1);
        } else {
            throw new Exception("No se ha proporcionado un NIF del que extraer la letra.");
        }
        return letra;
    }

    private static int getNumeroDni(String nif) throws Exception {
        int numero = 0;
        //Lo primero, comprobar que se le pasa un parÃ¡metro, distinto de la cadena vacÃ­a.
        if (nif != null && !nif.equals("")) {
            numero = Integer.parseInt(nif.substring(0, nif.length() - 1));
        } else {
            throw new Exception("No se ha proporcionado un NIF del que extraer el nÃºmero de DNI.");
        }
        return numero;
    }

    public static boolean validarNif(String nif) {
        boolean valido = true;   // Suponemos el NIF vÃ¡lido mientras no se encuentre algÃºn fallo
        char letraCalculada = ' ';
        char letraLeida = ' ';
        int dniLeido;

        if (nif == null) {  // El parÃ¡metro debe ser un objeto no vacÃ­o
            valido = false;
        } else {
            if (nif.length() != 9) {    // El NIF debe tener 9 caracteres (8 dÃ­gitos, aunque sea con ceros a la izquierda, y la letra)
                valido = false;
            } else {
                try {
                    letraLeida = getLetraNif(nif);    // Extraemos la letra de NIF (letra)
                    dniLeido = getNumeroDni(nif);  // Extraemos el nÃºmero de DNI (int)
                    letraCalculada = calcularLetraNif(dniLeido);  // Calculamos la letra de NIF a partir del nÃºmero extraÃ­do
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (letraLeida == letraCalculada) {   // Comparamos la letra extraÃ­da con la calculada
                    // Todas las comprobaciones han resultado vÃ¡lidas. El NIF es vÃ¡lido.
                    valido = true;
                } else {
                    valido = false;
                }
            }
        }
        return valido;
    }
    /**
     * Método que comprueba qe el telefono introducido es un numero de telefono 
     * con 9 digitos o más
     * @param cadena - la cadena de texto a comprobar.
     * @return true si es un tlf
     */
    public static boolean esNumeroTlf(String cadena) {
        boolean respuesta = false;
        try {
            //que sea un numero
            Integer.parseInt(cadena);
            //que tenga  9 digitos 
            if (cadena.length() == 9) {
                respuesta = true;
            }
        } catch (NumberFormatException nfe) {
            respuesta = false;
        }
        return respuesta;
    }
    
    
}
