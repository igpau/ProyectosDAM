package dam.java;

import dam.java.classes.*;
import dam.java.dao.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrincipal {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuPrincipal();
    }

    // <editor-fold defaultstate="collapsed" desc="MENU">
    public static void menuPrincipal() {
        boolean sortir = false;

        while (!sortir) {
            System.out.println("Menú de Gestió Musical:");
            System.out.println("1. Gestió Artista");
            System.out.println("2. Gestió Àlbum");
            System.out.println("3. Gestió Cançó");
            System.out.println("4. Gestió Client");
            System.out.println("5. Fer venda (demanarà codi targeta i codi cançó)");
            System.out.println("6. Llistar Vendes per Artista");
            System.out.println("7. Llistar Compres per Client");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");

            String opcio = scanner.nextLine(); // neteja de l'entrada després d'un int

            switch (opcio) {
                case "1":
                    gestioArtista();
                    break;
                case "2":
                    gestioAlbum();
                    break;
                case "3":
                    gestioSong();
                    break;
                case "4":
                    gestioClient();
                    break;
                case "5":
                    ferVenda();
                    break;
                case "6":
                    llistarVendesPerArtista();
                    break;
                case "7":
                    llistarCompresPerClient();
                    break;
                case "0":
                    sortir = true;
                    System.out.println("Sortint del programa...");
                    break;
                default:
                    System.out.println("Opció invàlida. Torna-ho a provar.\n");
            }
        }
        System.out.println();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestio Client">
    private static void gestioClient() {
        while (true) {
            System.out.println("Menú Client:");
            System.out.println("1. Alta Client");
            System.out.println("2. Modificar Client");
            System.out.println("3. Eliminar Client");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            String opcio = scanner.nextLine();
            switch (opcio) {
                case "1":
                    altaClient();
                    break;
                case "2":
                    modificarClient();
                    break;
                case "3":
                    eliminarClient();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opció invàlida. Torna-ho a provar.");
                    break;
            }
            System.out.println();
        }
    }

    private static void altaClient() {
        try {
            System.out.println("Introdueix el codi del client:");
            int codi = Integer.parseInt(scanner.nextLine());
            while (Client_DAO.verificar_id_client(codi)) {
                System.out.println("Aquest codi ja està registrat.");
                System.out.println("Introdueix el codi del client:");
                codi = Integer.parseInt(scanner.nextLine());
            }


            System.out.println("Introdueix el nom del client:");
            String nom = scanner.nextLine();
            System.out.println("Introdueix el primer cognom del client:");
            String cognom1 = scanner.nextLine();
            System.out.println("Introdueix el segon cognom del client:");
            String cognom2 = scanner.nextLine();
            System.out.println("Introdueix la regió del client:");
            String regio = scanner.nextLine();

            Client client = new Client(codi, nom, cognom1, cognom2, regio);
            System.out.println("Introdueix el codi de la targeta:");
            String codiTargeta = scanner.nextLine();
            while (Client_DAO.comproba_id_targeta(codiTargeta)) {
                System.out.println("Aquesta targeta ja està registrada.");
                System.out.println("Introdueix el codi de la targeta:");
                codiTargeta = scanner.nextLine();
            }

            System.out.println("Introdueix el número de la targeta:");
            String numeroTargeta = scanner.nextLine();
            System.out.println("Introdueix la data d'expiració de la targeta:(YYYY-MM-DD)");
            String dataExpiracio = scanner.nextLine();
            System.out.println("Introdueix el CVV de la targeta:");
            String csv = scanner.nextLine();

            Card card = new Card(codiTargeta, numeroTargeta, csv, dataExpiracio, codi);
            Client_DAO.insertClientAndCard(client, card);
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }

    private static void modificarClient() {
        System.out.println("Introdueix el codi del client a modificar:");
        int codi = Integer.parseInt(scanner.nextLine());
        if (!Client_DAO.mostrarClient(codi)) {
            return;
        }
        System.out.println("Introdueix el nou nom del client:");
        String nouNom = scanner.nextLine();
        System.out.println("Introdueix el nou primer cognom del client:");
        String nouCognom1 = scanner.nextLine();
        System.out.println("Introdueix el nou segon cognom del client:");
        String nouCognom2 = scanner.nextLine();
        System.out.println("Introdueix la nova regió del client:");
        String novaRegio = scanner.nextLine();
        Client client = new Client(codi, nouNom, nouCognom1, nouCognom2, novaRegio);
        Client_DAO.modificarClient(client);
    }

    private static void eliminarClient() {
        System.out.println("Introdueix el codi del client a eliminar:");
        int codi = Integer.parseInt(scanner.nextLine());
        Client_DAO.eliminarClient(codi);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestio Artista">

    private static void gestioArtista() {
        while (true) {
            System.out.println("Menú Artista:");
            System.out.println("1. Alta Artista");
            System.out.println("2. Modificar Artista");
            System.out.println("3. Eliminar Artista");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            String opcio = scanner.nextLine();
            switch (opcio) {
                case "1":
                    altaArtista();
                    break;
                case "2":
                    modificarArtista();
                    break;
                case "3":
                    eliminarArtista();
                    break;
                case "0":
                    return;
            }
            System.out.println();
        }
    }

    private static void altaArtista() {

        try {
            // Demanem les dades de l'artista
            System.out.println("Introdueix el codi de l'artista:");
            String codi = scanner.nextLine();
            System.out.println("Introdueix el nom de l'artista:");
            String nomArtista = scanner.nextLine();
            // Crear instància de la classe Artist
            Artist artist = new Artist(codi, nomArtista);
            // Inserir artista a la base de dades
            Artista_DAO.insertarArtista(artist);
            // Informar de l'èxit de la inserció
            System.out.println("L'artista s'ha inserit correctament.");
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }

    private static void modificarArtista() {
        System.out.println("Introdueix el codi de l'artista a modificar:");
        String codi = scanner.nextLine();
        if (!Artista_DAO.mostrarArtista(codi)) {
            return;
        }
        System.out.println("Introdueix el nou nom de l'artista:");
        String nouNom = scanner.nextLine();
        Artista_DAO.modificarArtista(codi, nouNom);

    }

    private static void eliminarArtista() {
        System.out.println("Introdueix el codi de l'artista a eliminar:");
        String codi = scanner.nextLine();
        Artista_DAO.eliminarArtista(codi);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestio Album">
    private static void gestioAlbum() {
        while (true) {
            System.out.println("Menú Album:");
            System.out.println("1. Alta Album");
            System.out.println("2. Modificar Album");
            System.out.println("3. Eliminar Album");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            String opcio = scanner.nextLine();

            switch (opcio) {
                case "1":
                    altaAlbum();
                    break;
                case "2":
                    modificarAlbum();
                    break;
                case "3":
                    eliminarAlbum();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opció invàlida. Torna-ho a provar.");
                    break;

            }
            System.out.println();
        }
    }

    private static void altaAlbum() {
        try {
            System.out.println("Introdueix codi de l'album:");
            String codi = scanner.nextLine();
            System.out.println("Introdueix títol de l'album:");
            String titol = scanner.nextLine();
            System.out.println("Introdueix l'id del artista:");
            String artista = scanner.nextLine();
            String any = "";
            String yearRegex = "\\d{4}";
            while (!any.matches(yearRegex)) {
                System.out.println("Introdueix any de l'album: (YYYY)");
                any = scanner.nextLine();
                if (!any.matches(yearRegex)) {
                    System.out.println("Any incorrecte.");
                }
            }

            System.out.println("Introdueix el id del estil de l'album:");
            String estil = scanner.nextLine();

            Album album = new Album(codi, titol, artista, any, estil);
            Album_DAO.insertarAlbum(album);
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }


    }

    private static void modificarAlbum() {
        System.out.println("Introdueix el codi de l'album a modificar:");
        String codi = scanner.nextLine();
        if (Album_DAO.mostrarAlbum(codi) != null) {
            System.out.println("Introdueix el nou títol de l'album:");
            String nouTitol = scanner.nextLine();
            System.out.println("Introdueix el nou id de l'artista:");
            String nouArtista = scanner.nextLine();
            String nouAny = "";
            String yearRegex = "\\d{4}";
            while (!nouAny.matches(yearRegex)) {
                System.out.println("Introdueix any de l'album: (YYYY)");
                nouAny = scanner.nextLine();
                if (!nouAny.matches(yearRegex)) {
                    System.out.println("Any incorrecte.");
                }
            }
            System.out.println("Introdueix el nou id de l'estil:");
            String nouEstil = scanner.nextLine();
            Album album = new Album(codi, nouTitol, nouArtista, nouAny, nouEstil);
            Album_DAO.modificarAlbum(album);
        }

    }

    private static void eliminarAlbum() {
        System.out.println("Introdueix el codi de l'album a eliminar:");
        String codi = scanner.nextLine();
        Album_DAO.eliminarAlbum(codi);
    }


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestio Song">
    private static void gestioSong() {
        while (true) {
            System.out.println("Menú Cançó:");
            System.out.println("1. Alta Cançó");
            System.out.println("2. Modificar Cançó");
            System.out.println("3. Eliminar Cançó");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            String opcio = scanner.nextLine(); // neteja de l'entrada després d'un int
            switch (opcio) {
                case "1":
                    altaCanco();
                    break;
                case "2":
                    modificarCanco();
                    break;
                case "3":
                    eliminarCanco();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opció invàlida. Torna-ho a provar.");
                    break;
            }
        }
    }

    private static void altaCanco() {
        try {
            System.out.println("Introdueix el codi de la cançó:");
            String codi = scanner.nextLine();
            while (Song_DAO.verificar_id_song(codi)) {
                System.out.println("Introdueix el codi de la cançó:");
                codi = scanner.nextLine();
            }
            System.out.println("Introdueix títol de la cançó:");
            String titol = scanner.nextLine();
            System.out.println("Introdueix l'id de l'album:");
            String album = scanner.nextLine();
            System.out.println("Introdueix el número de la cançó:");
            String numero = scanner.nextLine();

            Song song = new Song(codi, titol, album, numero);
            if (Song_DAO.verificarAlbum(song)) {
                Song_DAO.insertarSong(song);
            }
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }

    private static void modificarCanco() {
        System.out.println("Introdueix el codi de la cançó a modificar:");
        String codi = scanner.nextLine();
        if (!Song_DAO.mostrarCanco(codi)) {
            return;
        }
        System.out.println("Introdueix el nou títol de la cançó:");
        String nouTitol = scanner.nextLine();
        System.out.println("Introdueix el nou id de l'album:");
        String nouAlbum = scanner.nextLine();
        System.out.println("Introdueix el nou número de la cançó:");
        String nouNumero = scanner.nextLine();
        Song song = new Song(codi, nouTitol, nouAlbum, nouNumero);
        if (Song_DAO.verificarAlbum(song)) {
            Song_DAO.modificarCanco(song);
        }

    }

    private static void eliminarCanco() {
        System.out.println("Introdueix el codi de la cançó a eliminar:");
        String codi = scanner.nextLine();
        Song_DAO.eliminarCanco(codi);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Altres">
    private static void ferVenda() {
        try {
            System.out.println("Introdueix el codi de la targeta:");
            String codiTargeta = scanner.nextLine();
            System.out.println("Introdueix el codi de la cançó:");
            String codiCanco = scanner.nextLine();

            // Obtenir la data actual
            LocalDate ara = LocalDate.now();

            // Convertir directament a java.sql.Date
            Date saleDate = Date.valueOf(ara);

            Sale sale = new Sale(codiCanco, codiTargeta, saleDate);
            Sale_DAO.insertarVenda(sale);
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }

    private static void llistarVendesPerArtista() {
        try {
            System.out.println("Nom de l'artista:");
            String nomArtista = scanner.nextLine();
            Sale_DAO.llistarVendesPerArtista(nomArtista);
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }

    private static void llistarCompresPerClient() {
        try {
            System.out.println("Nom del client:");
            String nomClient = scanner.nextLine();
            Client_DAO.llistarCompres(nomClient);
        } catch (Exception e) {
            System.out.println("S'ha produït un error inesperat: " + e.getMessage());
        }
    }
    // </editor-fold>
}
