package org.example;
/*
Titulo: Calculadora de potencial de reducción de un electrodo o el voltaje de una celda electroquímica
Materia: Química & Introduccion a los sistemas computacionales
Integrantes: Alejandro Osvaldo Ramirez Matias
Fecha: 24/11/2026
*/
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuimicaOrdinario {

    private static final String ROJO = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double AU = 1.498;
        double H2O = 1.229;
        double PT = 1.200;
        double AG = 0.799;
        double HG = 0.788;
        double FE2_FE3 = 0.771;
        double OH = 0.401;
        double CU = 0.337;
        double SN2_SN4 = 0.150;
        double H2 = 0.000;
        double PB = -0.126;
        double SN = -0.136;
        double NI = -0.250;
        double CO = -0.277;
        double CD = -0.403;
        double FE = -0.440;
        double CR = -0.744;
        double ZN = -0.763;
        double AL = -1.662;
        double MG = -2.363;
        double NA = -2.714;

        boolean continuar = true;
        while (continuar) {
            try {
                String respuesta = NoVacio(
                        sc,
                        "¿Su reacción está en condiciones estándar? (si/no): "
                ).toLowerCase();

                switch (respuesta) {
                    case "si":
                    case "sí": {
                        System.out.println("Su reacción está en condiciones estándar.");

                        String elem1 = leerElementoValido(
                                sc,
                                "Ingresa el primer elemento (ej. Au, Cu, Zn, Fe2_Fe3): ",
                                null
                        );
                        Double val1 = obtenerValor(elem1, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        System.out.println("Valor de " + elem1 + ": " + val1);

                        String elem2 = leerElementoValido(
                                sc,
                                "Ingresa el segundo elemento (diferente a " + elem1 + "): ",
                                elem1
                        );
                        Double val2 = obtenerValor(elem2, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        System.out.println("Valor de " + elem2 + ": " + val2);

                        double potCatodoStd;
                        double potAnodoStd;
                        String catodoStd;
                        String anodoStd;

                        if (val1 >= val2) {
                            potCatodoStd = val1;
                            potAnodoStd = val2;
                            catodoStd = elem1;
                            anodoStd = elem2;
                        } else {
                            potCatodoStd = val2;
                            potAnodoStd = val1;
                            catodoStd = elem2;
                            anodoStd = elem1;
                        }

                        double eCeldaStd = potCatodoStd - potAnodoStd;
                        System.out.println("Cátodo (" + catodoStd + "): " + potCatodoStd + " V");
                        System.out.println("Ánodo (" + anodoStd + "): " + potAnodoStd + " V");
                        System.out.println("E°celda: " + eCeldaStd + " V");
                        System.out.println("Se reduce: " + catodoStd);
                        System.out.println("Se oxida: " + anodoStd);
                        System.out.println("Reacción de oxidación (" + anodoStd + "): " + obtenerOxidacion(anodoStd));
                        System.out.println("Reacción de reducción (" + catodoStd + "): " + obtenerReduccion(catodoStd));
                        break;
                    }
                    case "no": {
                        System.out.println("Su reacción no está en condiciones estándar.");

                        String elem3 = leerElementoValido(
                                sc,
                                "Ingresa el primer electrodo (ej. Au, Cu, Zn, Fe2_Fe3): ",
                                null
                        );
                        Double val3 = obtenerValor(elem3, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        Integer elecBase3 = obtenerElectrones(elem3);

                        String elem4 = leerElementoValido(
                                sc,
                                "Ingresa el segundo electrodo (diferente a " + elem3 + "): ",
                                elem3
                        );
                        Double val4 = obtenerValor(elem4, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        Integer elecBase4 = obtenerElectrones(elem4);

                        if (val3 == null || elecBase3 == null || val4 == null || elecBase4 == null) {
                            imprimirRojo("Error: no se pudieron obtener los datos de los electrodos.");
                            break;
                        }

                        double conc3 = leerConcentracionPositiva(sc, elem3);
                        double conc4 = leerConcentracionPositiva(sc, elem4);

                        String catodo;
                        String anodo;
                        double eCatodoStd;
                        double eAnodoStd;
                        double concCatodo;
                        double concAnodo;
                        int elecCatodo;
                        int elecAnodo;

                        if (val3 >= val4) {
                            catodo = elem3;
                            anodo = elem4;
                            eCatodoStd = val3;
                            eAnodoStd = val4;
                            concCatodo = conc3;
                            concAnodo = conc4;
                            elecCatodo = elecBase3;
                            elecAnodo = elecBase4;
                        } else {
                            catodo = elem4;
                            anodo = elem3;
                            eCatodoStd = val4;
                            eAnodoStd = val3;
                            concCatodo = conc4;
                            concAnodo = conc3;
                            elecCatodo = elecBase4;
                            elecAnodo = elecBase3;
                        }
                        int electronesCompartidos = Mcm(elecCatodo, elecAnodo);
                        double eCeldaEstandar = eCatodoStd - eAnodoStd;
                        double eCatodoNoStd = eCatodoStd + (0.0592 / elecCatodo) * Math.log10(concCatodo);
                        double eAnodoNoStd  = (eAnodoStd  + (0.0592 / elecAnodo)  * Math.log10(concAnodo))*-1;
                        double eCeldaNoStd =eAnodoNoStd+ eCatodoNoStd;

                        System.out.println("------------------------ Resultados de la celda ---");
                        System.out.println("Ánodo: " + anodo + " Se Oxida");
                        System.out.println("Reacción de oxidación (" + anodo + "): " + obtenerOxidacion(anodo));
                        System.out.println("E° ánodo (" + anodo + "): " + eAnodoStd + " V");
                        System.out.println("Potencial no estándar ánodo (" + anodo + "): " + eAnodoNoStd + " V");
                        System.out.println("Cátodo: " + catodo + " Se Reduce");
                        System.out.println("Reacción de reducción (" + catodo + "): " + obtenerReduccion(catodo));
                        System.out.println("E° cátodo (" + catodo + "): " + eCatodoStd + " V");
                        System.out.println("Potencial no estándar cátodo (" + catodo + "): " + eCatodoNoStd + " V");
                        System.out.println("Número de electrones compartidos (n): " + electronesCompartidos);
                        System.out.println("E°celda: " + eCeldaEstandar + " V");
                        System.out.println("Ecelda (no estándar): " + eCeldaNoStd + " V");
                        break;
                    }
                    default:
                        imprimirRojo("Respuesta no válida. Escribe 'si' o 'no'.");
                        break;
                }

                String seguir = NoVacio(sc, "¿Desea realizar otro cálculo? (si/no): ").toLowerCase();
                if (!seguir.equals("si") && !seguir.equals("sí")) {
                    continuar = false;
                    System.out.println("Programa finalizado.....");
                }
            } catch (Exception e) {
                imprimirRojo("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void imprimirRojo(String mensaje) {
        System.out.println(ROJO + mensaje + RESET);
    }

    private static String NoVacio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            imprimirRojo("Error: no puedes ingresar datos en blanco.");
        }
    }

    private static String leerElementoValido(Scanner sc, String prompt, String elementoProhibido) {
        while (true) {
            String entrada = NoVacio(sc, prompt);
            String simbolo = normalizarSimbolo(entrada);

            if (simbolo == null) {
                imprimirRojo("Error: '" + entrada + "' no es un símbolo químico válido en la tabla.");
                continue;
            }

            if (elementoProhibido != null && simbolo.equalsIgnoreCase(elementoProhibido)) {
                imprimirRojo("Error: no puedes elegir dos veces el mismo elemento (" + simbolo + ").");
                continue;
            }

            return simbolo;
        }
    }

    private static double leerConcentracionPositiva(Scanner sc, String elemento) {
        while (true) {
            String texto = NoVacio(sc, "Ingrese la concentración en M para " + elemento + ": ");
            try {
                double conc = Double.parseDouble(texto.replace(",", "."));
                if (Double.isNaN(conc) || Double.isInfinite(conc) || conc <= 0) {
                    imprimirRojo("Error: la concentración para " + elemento + " debe ser un número mayor que 0.");
                    continue;
                }
                return conc;
            } catch (NumberFormatException | InputMismatchException e) {
                imprimirRojo("Error: ingresaste una concentración no válida para " + elemento + ".");
            }
        }
    }

    private static String normalizarSimbolo(String entrada) {
        if (entrada == null) {
            return null;
        }
        switch (entrada.trim().toUpperCase()) {
            case "AU":
                return "Au";
            case "H2O":
                return "H2O";
            case "PT":
                return "Pt";
            case "AG":
                return "Ag";
            case "HG":
                return "Hg";
            case "FE2_FE3":
                return "Fe2_Fe3";
            case "OH":
                return "OH";
            case "CU":
                return "Cu";
            case "SN2_SN4":
                return "Sn2_Sn4";
            case "H2":
                return "H2";
            case "PB":
                return "Pb";
            case "SN":
                return "Sn";
            case "NI":
                return "Ni";
            case "CO":
                return "Co";
            case "CD":
                return "Cd";
            case "FE":
                return "Fe";
            case "CR":
                return "Cr";
            case "ZN":
                return "Zn";
            case "AL":
                return "Al";
            case "MG":
                return "Mg";
            case "NA":
                return "Na";
            default:
                return null;
        }
    }

    private static int Mcm(int a, int b) {
        int x = Math.abs(a);
        int y = Math.abs(b);
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        int mcd = x;
        return (Math.abs(a) / mcd) * Math.abs(b);
    }

    private static Integer obtenerElectrones(String nombre) {
        String sim = normalizarSimbolo(nombre);
        if (sim == null) {
            return null;
        }
        switch (sim) {
            case "Au":
                return 3;
            case "H2O":
                return 4;
            case "Pt":
                return 2;
            case "Ag":
                return 1;
            case "Hg":
                return 2;
            case "Fe2_Fe3":
                return 1;
            case "OH":
                return 4;
            case "Cu":
                return 2;
            case "Sn2_Sn4":
                return 2;
            case "H2":
                return 2;
            case "Pb":
                return 2;
            case "Sn":
                return 2;
            case "Ni":
                return 2;
            case "Co":
                return 2;
            case "Cd":
                return 2;
            case "Fe":
                return 2;
            case "Cr":
                return 3;
            case "Zn":
                return 2;
            case "Al":
                return 3;
            case "Mg":
                return 2;
            case "Na":
                return 1;
            default:
                return null;
        }
    }

    private static Double obtenerValor(String nombre,
                                       double AU, double H2O, double PT, double AG, double HG,
                                       double FE2_FE3, double OH, double CU, double SN2_SN4, double H2,
                                       double PB, double SN, double NI, double CO, double CD,
                                       double FE, double CR, double ZN, double AL, double MG, double NA) {
        String sim = normalizarSimbolo(nombre);
        if (sim == null) {
            return null;
        }
        switch (sim) {
            case "Au":
                return AU;
            case "H2O":
                return H2O;
            case "Pt":
                return PT;
            case "Ag":
                return AG;
            case "Hg":
                return HG;
            case "Fe2_Fe3":
                return FE2_FE3;
            case "OH":
                return OH;
            case "Cu":
                return CU;
            case "Sn2_Sn4":
                return SN2_SN4;
            case "H2":
                return H2;
            case "Pb":
                return PB;
            case "Sn":
                return SN;
            case "Ni":
                return NI;
            case "Co":
                return CO;
            case "Cd":
                return CD;
            case "Fe":
                return FE;
            case "Cr":
                return CR;
            case "Zn":
                return ZN;
            case "Al":
                return AL;
            case "Mg":
                return MG;
            case "Na":
                return NA;
            default:
                return null;
        }
    }

    private static String obtenerOxidacion(String nombre) {
        String sim = normalizarSimbolo(nombre);
        if (sim == null) {
            return ROJO + "Reacción no disponible" + RESET;
        }
        switch (sim) {
            case "Au":
                return "Au -> Au3+ + 3e-";
            case "H2O":
                return "2H2O -> O2 + 4H+ + 4e-";
            case "Pt":
                return "Pt -> Pt2+ + 2e-";
            case "Ag":
                return "Ag -> Ag+ + e-";
            case "Hg":
                return "2Hg -> Hg2^2+ + 2e-";
            case "Fe2_Fe3":
                return "Fe2+ -> Fe3+ + e-";
            case "OH":
                return "4(OH)- -> O2 + 2H2O + 4e-";
            case "Cu":
                return "Cu -> Cu2+ + 2e-";
            case "Sn2_Sn4":
                return "Sn2+ -> Sn4+ + 2e-";
            case "H2":
                return "H2 -> 2H+ + 2e-";
            case "Pb":
                return "Pb -> Pb2+ + 2e-";
            case "Sn":
                return "Sn -> Sn2+ + 2e-";
            case "Ni":
                return "Ni -> Ni2+ + 2e-";
            case "Co":
                return "Co -> Co2+ + 2e-";
            case "Cd":
                return "Cd -> Cd2+ + 2e-";
            case "Fe":
                return "Fe -> Fe2+ + 2e-";
            case "Cr":
                return "Cr -> Cr3+ + 3e-";
            case "Zn":
                return "Zn -> Zn2+ + 2e-";
            case "Al":
                return "Al -> Al3+ + 3e-";
            case "Mg":
                return "Mg -> Mg2+ + 2e-";
            case "Na":
                return "Na -> Na+ + e-";
            default:
                return ROJO + "Reacción no disponible" + RESET;
        }
    }

    private static String obtenerReduccion(String nombre) {
        String sim = normalizarSimbolo(nombre);
        if (sim == null) {
            return ROJO + "Reacción no disponible" + RESET;
        }
        switch (sim) {
            case "Au":
                return "Au3+ + 3e- -> Au";
            case "H2O":
                return "O2 + 4H+ + 4e- -> 2H2O";
            case "Pt":
                return "Pt2+ + 2e- -> Pt";
            case "Ag":
                return "Ag+ + e- -> Ag";
            case "Hg":
                return "Hg2^2+ + 2e- -> 2Hg";
            case "Fe2_Fe3":
                return "Fe3+ + e- -> Fe2+";
            case "OH":
                return "O2 + 2H2O + 4e- -> 4(OH)-";
            case "Cu":
                return "Cu2+ + 2e- -> Cu";
            case "Sn2_Sn4":
                return "Sn4+ + 2e- -> Sn2+";
            case "H2":
                return "2H+ + 2e- -> H2";
            case "Pb":
                return "Pb2+ + 2e- -> Pb";
            case "Sn":
                return "Sn2+ + 2e- -> Sn";
            case "Ni":
                return "Ni2+ + 2e- -> Ni";
            case "Co":
                return "Co2+ + 2e- -> Co";
            case "Cd":
                return "Cd2+ + 2e- -> Cd";
            case "Fe":
                return "Fe2+ + 2e- -> Fe";
            case "Cr":
                return "Cr3+ + 3e- -> Cr";
            case "Zn":
                return "Zn2+ + 2e- -> Zn";
            case "Al":
                return "Al3+ + 3e- -> Al";
            case "Mg":
                return "Mg2+ + 2e- -> Mg";
            case "Na":
                return "Na+ + e- -> Na";
            default:
                return ROJO + "Reacción no disponible" + RESET;
        }
    }
}