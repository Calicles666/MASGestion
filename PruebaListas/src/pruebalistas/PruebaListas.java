/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebalistas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Abraham Garrido
 */
public class PruebaListas {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) {
 
        List<Persona> listaPrincipal = new ArrayList<Persona>(); // El tipo es List y lo implementamos con ArrayList
        List<Persona> listaB = new ArrayList<Persona>();
        List<Persona> listaC = new ArrayList<Persona>();

        Persona p1 = new Persona("Abraham", "Garrido Rosillo", 38);
        Persona p2 = new Persona("Elena", "Gutierrez", 42);
        Persona p3 = new Persona("Jose", "Garrido Gutierrez", 11);
        // TODO code application logic here
        listaPrincipal.add(p1);
        listaPrincipal.add(p2);
        listaPrincipal.add(p3);
        
        listaB.add(p1);
        listaB.add(p2);
        
        listaC.add(p3);
        
        listaPrincipal.retainAll(listaB);
        listaPrincipal.retainAll(listaC);

        
        Persona temp = null;
        Iterator<Persona> it = listaPrincipal.iterator();

        while (it.hasNext()) {

            temp = it.next();

            System.out.println(temp);

        }
        
        it = listaB.iterator();

        while (it.hasNext()) {

            temp = it.next();

            //System.out.println(temp);

        }
    }

}
