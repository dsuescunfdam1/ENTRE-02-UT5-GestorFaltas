import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Un objeto de esta clase permite registrar estudiantes de un
 * curso (leyendo la información de un fichero de texto) y 
 * emitir listados con las faltas de los estudiantes, justificar faltas, 
 * anular matrícula dependiendo del nº de faltas, .....
 *
 */
public class GestorFaltas {
    Estudiante [] estudiantes;
    private int total;
    public GestorFaltas(int n) {
        estudiantes = new Estudiante[n];
        this.total = total;
    }

    /**
     * Devuelve true si el array de estudiantes está completo,
     * false en otro caso
     */
    public boolean cursoCompleto() {
        return total == estudiantes.length;
    }

    /**
     *    Añade un nuevo estudiante solo si el curso no está completo y no existe ya otro
     *    estudiante igual (con los mismos apellidos). 
     *    Si no se puede añadir se muestra los mensajes adecuados 
     *    (diferentes en cada caso)
     *    
     *    El estudiante se añade de tal forma que queda insertado en orden alfabético de apellidos
     *    (de menor a mayor)
     *    !!OJO!! No hay que ordenar ni utilizar ningún algoritmo de ordenación
     *    Hay que insertar en orden 
     *    
     */
    public void addEstudiante(Estudiante nuevo) {
        int z = 0;
        for(int i = 1; i <= total; i++){
            if(nuevo.getApellidos() == estudiantes[i].getApellidos()){
                z++;
            }
        }
        if(buscarEstudiante(nuevo.getApellidos()) == -1|| z == 1){
            System.out.println("No se puede");
        } 
        else{
            int i = total - 1;
            while (i >= 0 && nuevo.getApellidos().compareTo(estudiantes[i].getApellidos()) < 0) {
                estudiantes[i + 1] = estudiantes[i];
                i-- ;
            }
            estudiantes[i + 1] = nuevo; 
            total ++; 
          
        }
    }

    /**
     * buscar un estudiante por sus apellidos
     * Si está se devuelve la posición, si no está se devuelve -1
     * Es indiferente mayúsculas / minúsculas
     * Puesto que el curso está ordenado por apellido haremos la búsqueda más
     * eficiente
     *  
     */
    public int buscarEstudiante(String apellidos) {
        int izquierda = 0;
        int derecha = estudiantes.length - 1;
        while (izquierda <= derecha) {
            int mitad = (izquierda + derecha) / 2;

            if (apellidos.compareTo(estudiantes[mitad].getApellidos()) == 0) {
                return mitad;
            }
            else if (apellidos.compareTo(estudiantes[mitad].getApellidos()) < 0) {
                derecha = mitad - 1;
            }
            else {
                izquierda = mitad + 1;

            }
        }

        return -1;
    }

    /**
     * Representación textual del curso
     * Utiliza StringBuilder como clase de apoyo.
     *  
     */
    public String toString() {

        return null;

    }

    /**
     *  Se justifican las faltas del estudiante cuyos apellidos se proporcionan
     *  El método muestra un mensaje indicando a quién se ha justificado las faltas
     *  y cuántas
     *  
     *  Se asume todo correcto (el estudiante existe y el nº de faltas a
     *  justificar también)
     */
    public void justificarFaltas(String apellidos, int faltas) {
        Estudiante e = estudiantes[buscarEstudiante(apellidos)];
        int FalN = e.getFaltasNoJustificadas();
        int Fal = e.getFaltasJustificadas();
        e.setFaltasNoJustificadas(0);
        e.setFaltasJustificadas(FalN + Fal);
    }

    /**
     * ordenar los estudiantes de mayor a menor nº de faltas injustificadas
     * si coinciden se tiene en cuenta las justificadas
     * Método de selección directa
     */
    public void ordenar() {
        for (int i = 0; i < estudiantes.length - 1; i++) {
            int posmin = i;
            for (int j = i + 1; j < estudiantes.length; j++) {
                if (estudiantes[j].getFaltasNoJustificadas() < estudiantes[posmin].getFaltasNoJustificadas()) {
                    posmin = j;
                }
                else if(estudiantes[j].getFaltasNoJustificadas() == estudiantes[posmin].getFaltasNoJustificadas()){
                    if(estudiantes[j].getFaltasNoJustificadas() < estudiantes[posmin].getFaltasNoJustificadas()){
                        posmin = j;
                    }
                }
            }
            Estudiante aux = estudiantes[posmin];
            estudiantes[posmin] = estudiantes[i];
            estudiantes[i] = aux;
        }
    }

    /**
     * anular la matrícula (dar de baja) a 
     * aquellos estudiantes con 30 o más faltas injustificadas
     */
    public void anularMatricula() {
        for (int i = 0; i < estudiantes.length - 1; i++) {
            if(estudiantes[i].getFaltasNoJustificadas() >= 30){
                int j = 0;
                while(j < estudiantes.length - 2){
                    estudiantes[j] = estudiantes[j + 1];
                    j++;
                }
            }
        }
    }

    /**
     * Lee de un fichero de texto los datos de los estudiantes
     *   con ayuda de un objeto de la  clase Scanner
     *   y los guarda en el array. 
     */
    public void leerDeFichero() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("estudiantes.txt"));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                Estudiante estudiante = new Estudiante(linea);
                this.addEstudiante(estudiante);

            }

        }
        catch (IOException e) {
            System.out.println("Error al leer del fichero");
        }
        finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

}
