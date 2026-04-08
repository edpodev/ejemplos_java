package album;

import java.util.Scanner;

public class AlbumMundial {

    private static final int PAISES_POR_PAGINA = 2;
    private static final int CARTAS_POR_PAIS = 5;
    private static final int MAX_REGISTROS = 500;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorArchivos gestor = new GestorArchivos();

        // Carga de informacion global
        Pais[] paises = gestor.cargarPaises();
        Carta[] catalogo = gestor.cargarCartas();

        // Inicializar datos del album en memoria
        int totalPaginas = (int) Math.ceil((double) paises.length / PAISES_POR_PAGINA);
        MallaOrtogonal[] mallas = new MallaOrtogonal[totalPaginas];
        for (int p = 0; p < totalPaginas; p++) {
            mallas[p] = new MallaOrtogonal(PAISES_POR_PAGINA, CARTAS_POR_PAIS, p);
        }
        for (int i = 0; i < paises.length; i++) {
            int pagina = paises[i].getPagina();
            int filaMalla = paises[i].getFilaPagina();
            mallas[pagina].asignarPais(filaMalla, paises[i]);
        }

        // Carga del inventario en sesion
        CartaUsuario[] registros = new CartaUsuario[MAX_REGISTROS];
        CartaUsuario[] cargados = gestor.cargarCartasUsuario();
        int usados = cargados.length;
        for (int i = 0; i < usados; i++) {
            registros[i] = cargados[i];
        }

        System.out.print("Ingresa tu nombre de usuario: ");
        String usuario = sc.nextLine().trim();
        if (usuario.isEmpty()) {
            usuario = "jugador1";
        }
        System.out.println("Bienvenido, " + usuario + "!\n");

        // Reconstruccion de album en sesion
        reconstruirMallas(mallas, registros, usados, catalogo, usuario);

        GeneradorCartas generador = new GeneradorCartas(catalogo);

        int opcion = -1;
        do {
            imprimirMenu();
            System.out.print("Opcion: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 ->
                    verPaises(paises);
                case 2 ->
                    verAlbumCompleto(mallas, totalPaginas, usuario);
                case 3 ->
                    verPaginaAlbum(sc, mallas, totalPaginas, usuario);
                case 4 ->
                    verInventario(registros, usados, catalogo, usuario);
                case 5 ->
                    usados = abrirSobre(sc, generador, gestor,
                            registros, usados, mallas, paises, catalogo, usuario);
                case 6 ->
                    buscarCarta(sc, catalogo, mallas, registros, usados, usuario);
                case 7 ->
                    verEstadisticas(registros, usados, catalogo, mallas, usuario, totalPaginas);
                case 0 -> {
                    System.out.println("\nGuardando...");
                    gestor.guardarCartasUsuario(registros, usados);
                }
                default ->
                    System.out.println("Invalido :v");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static void imprimirMenu() {
        System.out.println();
        System.out.println("MENU PRINCIPAL");
        System.out.println("  1. Ver paises del album         ");
        System.out.println("  2. Ver album completo (malla)   ");
        System.out.println("  3. Ver pagina especifica        ");
        System.out.println("  4. Ver mi inventario            ");
        System.out.println("  5. Abrir sobre (generar cartas) ");
        System.out.println("  6. Buscar carta                 ");
        System.out.println("  7. Estadisticas                 ");
        System.out.println("  0. Guardar y salir              ");

    }

    private static void verPaises(Pais[] paises) {
        for (int i = 0; i < paises.length; i++) {
            System.out.printf("  %2d. %s%n", i + 1, paises[i]);
        }
    }

    private static void verAlbumCompleto(MallaOrtogonal[] mallas, int total, String usuario) {
        for (int p = 0; p < total; p++) {
            System.out.println();
            mallas[p].imprimir();
        }
    }

    private static void verPaginaAlbum(Scanner sc, MallaOrtogonal[] mallas,
            int total, String usuario) {
        System.out.print("\nNumero de pagina (0-" + (total - 1) + "): ");
        try {
            int p = Integer.parseInt(sc.nextLine().trim());
            if (p < 0 || p >= total) {
                System.out.println("Pag invalida");
            } else {
                System.out.println("\nAlbum de " + usuario + ":");
                mallas[p].imprimir();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalido");
        }
    }

    private static void verInventario(CartaUsuario[] registros, int usados,
            Carta[] catalogo, String usuario) {
        int distintas = 0;
        for (int i = 0; i < usados; i++) {
            CartaUsuario cu = registros[i];
            if (cu == null || !cu.getUsuario().equals(usuario)) {
                continue;
            }
            Carta info = buscarEnCatalogo(catalogo, cu.getNoCarta());
            String estado = cu.isPegada()
                    ? "[ X pag." + cu.getPagina() + " fil." + cu.getFila()
                    + " col." + cu.getColumna() + "]"
                    : "[O]";
            String nombre = (info != null) ? info.getNombreJugador() : "?";
            System.out.printf("  x%-2d %-8s %-25s %s%n",
                    cu.getCantidad(), cu.getNoCarta(), nombre, estado);
            distintas++;
        }
        if (distintas == 0) {
            System.out.println("Todavia no tienes cartas.");
        } else {
            System.out.println("  Total: " + distintas + " cartas distintas.");
        }
    }

    private static int abrirSobre(Scanner sc, GeneradorCartas generador, GestorArchivos gestor, CartaUsuario[] registros, int usados, MallaOrtogonal[] mallas, Pais[] paises, Carta[] catalogo, String usuario) {
        System.out.print("\nCuantas cartas? (1-10): ");
        int cantidad = 5;
        try {
            cantidad = Integer.parseInt(sc.nextLine().trim());
            if (cantidad < 1) {
                cantidad = 1;
            }
            if (cantidad > 10) {
                cantidad = 10;
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor no permitido.");
        }

        ListaSimple<Carta> lote = generador.generarLote(cantidad);

        System.out.println("\n--- Procesando cartas ---");
        usados = generador.aplicarLote(lote, usuario, registros, usados, mallas, paises);

        gestor.guardarCartasUsuario(registros, usados);
        return usados;
    }

    private static void buscarCarta(Scanner sc, Carta[] catalogo,
            MallaOrtogonal[] mallas,
            CartaUsuario[] registros, int usados,
            String usuario) {
        System.out.print("\nIngresa codigo, nombre o pais: ");
        String crit = sc.nextLine().trim().toLowerCase();

        int encontradas = 0;
        for (int i = 0; i < catalogo.length; i++) {
            Carta c = catalogo[i];
            if (c.getNoCarta().toLowerCase().contains(crit)
                    || c.getNombreJugador().toLowerCase().contains(crit)
                    || c.getCodigoPais().toLowerCase().contains(crit)
                    || c.getPosicion().toLowerCase().contains(crit)) {

                // Ver si el usuario la tiene
                CartaUsuario cu = buscarRegistroUsuario(registros, usados, usuario, c.getNoCarta());
                String tienela = (cu != null)
                        ? "(tienes x" + cu.getCantidad()
                        + (cu.isPegada() ? ", X)" : ", O)")
                        : "(-)";
                System.out.printf("  %s  %s%n", c, tienela);
                encontradas++;
            }
        }
        if (encontradas == 0) {
            System.out.println("  Sin resultados para: \"" + crit + "\"");
        }
    }

    private static void verEstadisticas(CartaUsuario[] registros, int usados,
            Carta[] catalogo,
            MallaOrtogonal[] mallas,
            String usuario, int totalPaginas) {
        int distintas = 0, pegadas = 0, repetidas = 0, legendarias = 0;
        for (int i = 0; i < usados; i++) {
            CartaUsuario cu = registros[i];
            if (cu == null || !cu.getUsuario().equals(usuario)) {
                continue;
            }
            distintas++;
            if (cu.isPegada()) {
                pegadas++;
            }
            if (cu.getCantidad() > 1) {
                repetidas++;
            }
            Carta info = buscarEnCatalogo(catalogo, cu.getNoCarta());
            if (info != null && "Legendaria".equals(info.getRareza())) {
                legendarias++;
            }
        }

        int total = catalogo.length;
        System.out.printf("  Cartas distintas:  %d / %d  %s%n",
                distintas, total, barraProgreso(distintas, total));
        System.out.printf("  Pegadas en album:  %d%n", pegadas);
        System.out.printf("  Con repetidas:     %d%n", repetidas);
        System.out.printf("  Legendarias:       %d%n", legendarias);

        System.out.println("\n  Progreso por pagina:");
        for (int p = 0; p < totalPaginas; p++) {
            int pegadasPag = mallas[p].contarPegadas();
            int totalPag = mallas[p].getFilas() * mallas[p].getColumnas();
            System.out.printf("    Pag.%d: %d/%d  %s%n",
                    p, pegadasPag, totalPag, barraProgreso(pegadasPag, totalPag));
        }
    }

    private static Carta buscarEnCatalogo(Carta[] catalogo, String noCarta) {
        for (int i = 0; i < catalogo.length; i++) {
            if (catalogo[i].getNoCarta().equals(noCarta)) {
                return catalogo[i];
            }
        }
        return null;
    }

    private static CartaUsuario buscarRegistroUsuario(CartaUsuario[] reg,
            int usados,
            String usuario,
            String noCarta) {
        for (int i = 0; i < usados; i++) {
            if (reg[i] != null
                    && reg[i].getUsuario().equals(usuario)
                    && reg[i].getNoCarta().equals(noCarta)) {
                return reg[i];
            }
        }
        return null;
    }

    private static String barraProgreso(int actual, int total) {
        if (total == 0) {
            return "[--------------------] 0%";
        }
        int llenos = (int) Math.round(20.0 * actual / total);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            sb.append(i < llenos ? '#' : '-');
        }
        sb.append(String.format("] %d%%", actual * 100 / total));
        return sb.toString();
    }

    private static void reconstruirMallas(MallaOrtogonal[] mallas,
            CartaUsuario[] registros,
            int usados,
            Carta[] catalogo,
            String usuario) {
        for (int p = 0; p < mallas.length; p++) {
            NodoMatriz filaNode = mallas[p].getOrigen();
            while (filaNode != null) {
                NodoMatriz nodo = filaNode;
                while (nodo != null) {
                    nodo.dato = null;
                    nodo = nodo.derecha;
                }
                filaNode = filaNode.abajo;
            }
        }

        int restauradas = 0;
        for (int i = 0; i < usados; i++) {
            CartaUsuario cu = registros[i];
            if (cu == null) {
                continue;
            }
            if (!cu.getUsuario().equals(usuario)) {
                continue;
            }
            if (!cu.isPegada()) {
                continue;
            }

            Carta carta = buscarEnCatalogo(catalogo, cu.getNoCarta());
            if (carta != null) {
                mallas[cu.getPagina()].pegarCarta(
                        cu.getFila(), cu.getColumna(), carta);
                restauradas++;
            }
        }
    }
}
