package album;

import java.util.Random;


public class GeneradorCartas {

    private final Carta[] catalogo;
    private final int     totalCatalogo;
    private final Random  random;
    private final int[]   pesosAcumulados;
    private final int     pesoTotal;

    public GeneradorCartas(Carta[] catalogo) {
        this.catalogo      = catalogo;
        this.totalCatalogo = catalogo.length;
        this.random        = new Random();

        pesosAcumulados = new int[totalCatalogo];
        int acum = 0;
        for (int i = 0; i < totalCatalogo; i++) {
            acum += catalogo[i].getPesoRareza();
            pesosAcumulados[i] = acum;
        }
        pesoTotal = acum;
    }


    private Carta sortearUnaCarta() {
        int objetivo = random.nextInt(pesoTotal) + 1;
        for (int i = 0; i < totalCatalogo; i++) {
            if (objetivo <= pesosAcumulados[i]) return catalogo[i];
        }
        return catalogo[totalCatalogo - 1];
    }


    public ListaSimple<Carta> generarLote(int cantidad) {
        ListaSimple<Carta> lote = new ListaSimple<>();
        System.out.println("\n[SOBRE] Generando " + cantidad + " carta(s)...\n");
        for (int i = 0; i < cantidad; i++) {
            Carta c = sortearUnaCarta();
            lote.insertarAlFinal(c);
            System.out.printf("  %2d. %-8s | %-25s | %-12s | %s%n",
                    i + 1, c.getNoCarta(), c.getNombreJugador(),
                    c.getPosicion(), c.getRareza());
        }
        return lote;
    }

    
    public int aplicarLote(
            ListaSimple<Carta> lote,
            String usuario,
            CartaUsuario[] registros,
            int usados,
            MallaOrtogonal[] mallas,
            Pais[] paises) {

        int nuevas    = 0;
        int repetidas = 0;

        NodoSimple<Carta> nodo = lote.getCabeza();
        while (nodo != null) {
            Carta carta   = nodo.dato;
            String codigo = carta.getNoCarta();

            CartaUsuario existente = buscarRegistro(registros, usados, usuario, codigo);

            if (existente != null) {
                existente.incrementar(1);
                repetidas++;
                System.out.printf("  [repetida] %-8s — ahora tienes x%d%n",
                        codigo, existente.getCantidad());
            } else {

                CartaUsuario nueva = new CartaUsuario(usuario, codigo);
                Pais paisCarta = buscarPais(paises, carta.getCodigoPais());
                if (paisCarta != null) {
                    int pagina    = paisCarta.getPagina();
                    int filaMalla = paisCarta.getFilaPagina();
                    NodoMatriz celdaLibre =
                            mallas[pagina].primerVacioEnFila(filaMalla);

                    if (celdaLibre != null) {
                        celdaLibre.dato = carta;
                        nueva.pegar(pagina, filaMalla, celdaLibre.columna);
                        System.out.printf("  [nueva]    %-8s pegada en pag.%d fil.%d col.%d%n",
                                codigo, pagina, filaMalla, celdaLibre.columna);
                    } else {
                        System.out.printf("  [nueva]    %-8s — fila completa, queda en inventario%n",
                                codigo);
                    }
                }
                if (usados < registros.length) {
                    registros[usados++] = nueva;
                } else {
                    System.out.println("Limit maxmo");
                }
                nuevas++;
            }

            nodo = nodo.siguiente;
        }

        System.out.printf("%n  Resumen: %d nuevas, %d repetidas.%n",
                nuevas, repetidas);
        return usados;
    }

    private CartaUsuario buscarRegistro(CartaUsuario[] reg, int usados,
                                        String usuario, String noCarta) {
        for (int i = 0; i < usados; i++) {
            if (reg[i] != null
                    && reg[i].getUsuario().equals(usuario)
                    && reg[i].getNoCarta().equals(noCarta)) {
                return reg[i];
            }
        }
        return null;
    }

    private Pais buscarPais(Pais[] paises, String codigo) {
        for (int i = 0; i < paises.length; i++) {
            if (paises[i].getCodigo().equals(codigo)) return paises[i];
        }
        return null;
    }
}