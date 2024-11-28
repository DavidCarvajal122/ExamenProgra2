import java.util.LinkedList;

public class GestionYoungAvengers {
    private LinkedList<YoungAvenger> listaAvengers;

    public GestionYoungAvengers() {
        listaAvengers = new LinkedList<>();
    }

    // Aquí agregamos un nuevo Avenger en caso de que su código no esté duplicado
    public boolean agregarAvenger(YoungAvenger avenger) {
        for (YoungAvenger a : listaAvengers) {
            if (a.getCodigo() == avenger.getCodigo()) {
                return false; // Si el código ya existe devuelve false
            }
        }
        listaAvengers.addFirst(avenger);
        return true; //En caso de no estar duplicado lo agrega al principio de la fila y devuelve true
    }

    // Con esta función buscamos a los Avengers por medio de su código
    public YoungAvenger buscarAvenger(int codigo) {
        //Recorremos la lista
        for (YoungAvenger a : listaAvengers) {
            //Comparamos los códigos con el que buscamos
            if (a.getCodigo() == codigo) {
                return a; //Si lo encuentra devuelva a
            }
        }
        return null; // En caso de no encontrar devuelve null
    }

    /* Con esta función podemos filtrar los Avengers con poder especial y
     los ordena según su nivel de habilidad */

    public LinkedList<YoungAvenger> filtrarYOrdenar(String poderEspecial) {
        LinkedList<YoungAvenger> listaFiltrada = new LinkedList<>();
        //Filtramos los Avengers en caso de que el poder especial coincida
        for (YoungAvenger a : listaAvengers) {
            if (a.getPoderEspecial().equalsIgnoreCase(poderEspecial)) {
                listaFiltrada.add(a); // Si coinciden serán agregados
            }
        }

        // Usamos el método de burbuja para ordenar la lista que sacamos
        for (int i = 0; i < listaFiltrada.size() - 1; i++) {
            for (int j = 0; j < listaFiltrada.size() - i - 1; j++) {
                if (listaFiltrada.get(j).getNivelHabilidad() > listaFiltrada.get(j + 1).getNivelHabilidad()) {
                    YoungAvenger temp = listaFiltrada.get(j);
                    listaFiltrada.set(j, listaFiltrada.get(j + 1));
                    listaFiltrada.set(j + 1, temp);
                }
            }
        }
        return listaFiltrada; //Aquí nos devolverá la lista ya filtrada y también ordenada
    }


    // Aquí contamos cuantos Avengers tienen un poder especial
    public int contarMisionesPorPoder(String poderEspecial, int index) {

        //Si el index se pasa del tamaño de la lista que tenemos retornará 0
        if (index >= listaAvengers.size()) {
            return 0;
        }
        //Aquí nos va a devolver 1 en caso de coincidir o 0 en caso de que nó
        int count = listaAvengers.get(index).getPoderEspecial().equalsIgnoreCase(poderEspecial) ? 1 : 0;
        return count + contarMisionesPorPoder(poderEspecial, index + 1);
    }

    //En esta función vemos cuantos avengers están en una misión específica
    public int contarMisionesPorMision(String mision, int index) {

        /*Si index llega a ser igual o mayor que el tamaño de la
        lista, sabremos que ya no hay más elementos por revisar */

        if (index >= listaAvengers.size()) {
            return 0; //Retornará 0 cuando ya no haya más elementos
        }
        //Vemos si el elemento coincide con el poder y lo sumamos
        int count = listaAvengers.get(index).getMisionActiva().equalsIgnoreCase(mision) ? 1 : 0;
        //Sumamos count + lo que nos dé la llamada recursiva
        return count + contarMisionesPorMision(mision, index + 1); // Llamada recursiva
    }

    public LinkedList<YoungAvenger> getListaAvengers() {
        return listaAvengers; //Aquí retornamos la lista de Avengers
    }
}
