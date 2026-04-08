package pilaconcolasauxiliares;

public class PilaConColasAuxiliares {

    public static void main(String[] args) {
        
        // Pila con colas auxiliares
        Pila pila = new Pila(5);
        pila.push(10);
        pila.mostrar();
        pila.push(20);
        pila.mostrar();
        pila.push(30);
        pila.mostrar();
        
        pila.pop();
        pila.mostrar();

        pila.pop();
        pila.mostrar();

        pila.push(40);
        pila.push(50);
        pila.push(60);
        pila.mostrar();

        while (!pila.pilaVacia()) {
            pila.pop();
        }
        pila.pop();
        pila.mostrar();
        
        // Metodo realizado
        decimalABinario(45);
    }
    
    
    static void decimalABinario(int numero) {
        Pila pila = new Pila(32);
        int n = numero;
        while (n > 0) {
            pila.push(n % 2);
            n = n / 2;
        }
        System.out.print("Binario de " + numero + " = ");
        while (!pila.pilaVacia())
            System.out.print(pila.pop());
        System.out.println();
    }
    
}
