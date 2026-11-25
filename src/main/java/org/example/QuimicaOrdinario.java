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
                System.out.println("¿Su reacción está en condiciones estándar? (si/no)");
                String respuesta = sc.nextLine().trim().toLowerCase();
                switch (respuesta) {
                    case "si":
                    case "sí":
                        System.out.println("Su reaccion esta en condiciones estandar");
                        System.out.println("Ingresa el primer elemento (ej. Au, Cu, Zn, Fe2_Fe3): ");
                        String elem1 = sc.nextLine().trim();
                        Double val1 = obtenerValor(elem1, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        if (val1 == null) {
                            System.out.println("Elemento no encontrado.");
                            break;
                        }
                        System.out.println("Valor de " + elem1 + ": " + val1);
                        System.out.println("Ingresa el segundo elemento: ");
                        String elem2 = sc.nextLine().trim();
                        Double val2 = obtenerValor(elem2, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        if (val2 == null) {
                            System.out.println("Elemento no encontrado.");
                            break;
                        }
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
                    case "no":
                        System.out.println("Su reaccion no esta en condiciones estandar");
                        System.out.println("Ingresa el primer electrodo (ej. Au, Cu, Zn, Fe2_Fe3): ");
                        String elem3 = sc.nextLine().trim();
                        Double val3 = obtenerValor(elem3, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        Integer elecBase3 = obtenerElectrones(elem3);
                        if (val3 == null || elecBase3 == null) {
                            System.out.println("Elemento no encontrado.");
                            break;
                        }
                        System.out.println("Ingresa el segundo electrodo: ");
                        String elem4 = sc.nextLine().trim();
                        Double val4 = obtenerValor(elem4, AU, H2O, PT, AG, HG, FE2_FE3, OH, CU, SN2_SN4, H2,
                                PB, SN, NI, CO, CD, FE, CR, ZN, AL, MG, NA);
                        Integer elecBase4 = obtenerElectrones(elem4);
                        if (val4 == null || elecBase4 == null) {
                            System.out.println("Elemento no encontrado.");
                            break;
                        }
                        System.out.println("Ingrese la concentración en M para " + elem3 + ": ");
                        double conc3 = sc.nextDouble();
                        if (conc3 <= 0) {
                            System.out.println("Error: la concentración para " + elem3 + " debe ser mayor que 0.");
                            sc.nextLine();
                            break;
                        }
                        System.out.println("Ingrese la concentración en M para " + elem4 + ": ");
                        double conc4 = sc.nextDouble();
                        if (conc4 <= 0) {
                            System.out.println("Error: la concentración para " + elem4 + " debe ser mayor que 0.");
                            sc.nextLine();
                            break;
                        }
                        sc.nextLine();
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
                        int electronesCompartidos = calcularMcm(elecCatodo, elecAnodo);
                        double eCatodoNoStd = eCatodoStd - (0.0592 / elecCatodo) * Math.log10(1.0 / concCatodo);
                        double eAnodoNoStd = (eAnodoStd - (0.0592 / elecAnodo) * Math.log10(concAnodo))*-1;
                        double eCeldaEstandar = eCatodoStd - eAnodoStd;
                        double eCeldaNoStd = eCatodoNoStd+eAnodoNoStd;
                        System.out.println("------------------------ Resultados de la celda ---");
                        System.out.println("Ánodo: " + anodo + " Se Oxida");
                        System.out.println("Cátodo: " + catodo + "Se Reduce");
                        System.out.println("Reacción de oxidación (" + anodo + "): " + obtenerOxidacion(anodo));
                        System.out.println("Reacción de reducción (" + catodo + "): " + obtenerReduccion(catodo));
                        System.out.println("E° ánodo (" + anodo + "): " + eAnodoStd + " V");
                        System.out.println("E° cátodo (" + catodo + "): " + eCatodoStd + " V");
                        System.out.println("Potencial no estándar ánodo (" + anodo + "): " + eAnodoNoStd + " V");
                        System.out.println("Potencial no estándar cátodo (" + catodo + "): " + eCatodoNoStd + " V");
                        System.out.println("Número de electrones compartidos (n): " + electronesCompartidos);
                        System.out.println("E°celda: " + eCeldaEstandar + " V");
                        System.out.println("Ecelda (no estándar): " + eCeldaNoStd + " V");
                        break;
                    default:
                        System.out.println("Respuesta no válida. Escribe 'si' o 'no'.");
                        break;
                }
                System.out.println("¿Desea realizar otro cálculo? (si/no): ");
                String seguir = sc.nextLine().trim().toLowerCase();
                if (!seguir.equals("si") && !seguir.equals("sí")) {
                    continuar = false;
                    System.out.println("Programa finalizado.....");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: ingresaste un dato numérico no válido. Intenta de nuevo.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        sc.close();
    }
    private static int calcularMcm(int a, int b) {
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
        if (nombre == null) {
            return null;
        }
        switch (nombre.trim()) {
            case "AU":
                return 3;
            case "H2O":
                return 4;
            case "PT":
                return 2;
            case "AG":
                return 1;
            case "HG":
                return 2;
            case "FE2_FE3":
                return 1;
            case "OH":
                return 4;
            case "CU":
                return 2;
            case "SN2_SN4":
                return 2;
            case "H2":
                return 2;
            case "PB":
                return 2;
            case "SN":
                return 2;
            case "NI":
                return 2;
            case "CO":
                return 2;
            case "CD":
                return 2;
            case "FE":
                return 2;
            case "CR":
                return 3;
            case "ZN":
                return 2;
            case "AL":
                return 3;
            case "MG":
                return 2;
            case "NA":
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
        if (nombre == null) {
            return null;
        }
        switch (nombre.trim()) {
            case "AU":
                return AU;
            case "H2O":
                return H2O;
            case "PT":
                return PT;
            case "AG":
                return AG;
            case "HG":
                return HG;
            case "FE2_FE3":
                return FE2_FE3;
            case "OH":
                return OH;
            case "CU":
                return CU;
            case "SN2_SN4":
                return SN2_SN4;
            case "H2":
                return H2;
            case "PB":
                return PB;
            case "SN":
                return SN;
            case "NI":
                return NI;
            case "CO":
                return CO;
            case "CD":
                return CD;
            case "FE":
                return FE;
            case "CR":
                return CR;
            case "ZN":
                return ZN;
            case "AL":
                return AL;
            case "MG":
                return MG;
            case "NA":
                return NA;
            default:
                return null;
        }
    }
    private static String obtenerOxidacion(String nombre) {
        if (nombre == null) {
            return "Reacción no disponible";
        }
        switch (nombre.trim()) {
            case "AU":
                return "Au -> Au3+ + 3e-";
            case "H2O":
                return "2H2O -> O2 + 4H+ + 4e-";
            case "PT":
                return "Pt -> Pt2+ + 2e-";
            case "AG":
                return "Ag -> Ag+ + e-";
            case "HG":
                return "2Hg -> Hg2^2+ + 2e-";
            case "FE2_FE3":
                return "Fe2+ -> Fe3+ + e-";
            case "OH":
                return "4(OH)- -> O2 + 2H2O + 4e-";
            case "CU":
                return "Cu -> Cu2+ + 2e-";
            case "SN2_SN4":
                return "Sn2+ -> Sn4+ + 2e-";
            case "H2":
                return "H2 -> 2H+ + 2e-";
            case "PB":
                return "Pb -> Pb2+ + 2e-";
            case "SN":
                return "Sn -> Sn2+ + 2e-";
            case "NI":
                return "Ni -> Ni2+ + 2e-";
            case "CO":
                return "Co -> Co2+ + 2e-";
            case "CD":
                return "Cd -> Cd2+ + 2e-";
            case "FE":
                return "Fe -> Fe2+ + 2e-";
            case "CR":
                return "Cr -> Cr3+ + 3e-";
            case "ZN":
                return "Zn -> Zn2+ + 2e-";
            case "AL":
                return "Al -> Al3+ + 3e-";
            case "MG":
                return "Mg -> Mg2+ + 2e-";
            case "NA":
                return "Na -> Na+ + e-";
            default:
                return "Reacción no disponible";
        }
    }
    private static String obtenerReduccion(String nombre) {
        if (nombre == null) {
            return "Reacción no disponible";
        }
        switch (nombre.trim()) {
            case "AU":
                return "Au3+ + 3e- -> Au";
            case "H2O":
                return "O2 + 4H+ + 4e- -> 2H2O";
            case "PT":
                return "Pt2+ + 2e- -> Pt";
            case "AG":
                return "Ag+ + e- -> Ag";
            case "HG":
                return "Hg2^2+ + 2e- -> 2Hg";
            case "FE2_FE3":
                return "Fe3+ + e- -> Fe2+";
            case "OH":
                return "O2 + 2H2O + 4e- -> 4(OH)-";
            case "CU":
                return "Cu2+ + 2e- -> Cu";
            case "SN2_SN4":
                return "Sn4+ + 2e- -> Sn2+";
            case "H2":
                return "2H+ + 2e- -> H2";
            case "PB":
                return "Pb2+ + 2e- -> Pb";
            case "SN":
                return "Sn2+ + 2e- -> Sn";
            case "NI":
                return "Ni2+ + 2e- -> Ni";
            case "CO":
                return "Co2+ + 2e- -> Co";
            case "CD":
                return "Cd2+ + 2e- -> Cd";
            case "FE":
                return "Fe2+ + 2e- -> Fe";
            case "CR":
                return "Cr3+ + 3e- -> Cr";
            case "ZN":
                return "Zn2+ + 2e- -> Zn";
            case "AL":
                return "Al3+ + 3e- -> Al";
            case "MG":
                return "Mg2+ + 2e- -> Mg";
            case "NA":
                return "Na+ + e- -> Na";
            default:
                return "Reacción no disponible";
        }
    }
}