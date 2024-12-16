package principal;

import DTOs.ConversorDTO;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Conversor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private String monedaBase;
    private String monedaDestino;
    private Double cantidad;
    private Double resultado;

    public void menu(){
        var opcion=1;
        do{
            System.out.println("****************************************************************");
            System.out.println("*                                                              *");
            System.out.println("*                 CONVERSOR DE MONEDAS                         *");
            System.out.println("*                                                              *");
            System.out.println("****************************************************************");
            System.out.println("Sea Bienvenido/a al modelos.Conversor de Monedas\n");
            System.out.println("1. Dólar ==> Peso Argentino");
            System.out.println("2. Peso Argentino ==> Dólar");
            System.out.println("3. Dólar ==> Real Brasileño");
            System.out.println("4. Real Brasileño ==> Dólar");
            System.out.println("5. Dólar ==> Peso Colombiano");
            System.out.println("6. Peso Colombiano ==> Dólar");
            System.out.println("7. Salir");
            System.out.println("****************************************************************");

            opcion = obtenerOpcionUsuario();

            switch (opcion){
                case 1:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="USD";
                    monedaDestino="ARS";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]\n");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="ARS";
                    monedaDestino="USD";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="USD";
                    monedaDestino="BRL";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="BRL";
                    monedaDestino="USD";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="USD";
                    monedaDestino="COP";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 6:
                    cantidad= obtenerCantidadUsuario();
                    monedaBase="COP";
                    monedaDestino="USD";
                    try {
                        resultado = convertirMoneda(monedaBase,monedaDestino,cantidad);
                        System.out.println("\nResultado: "+cantidad+" ["+monedaBase+"] corresponde al valor final de =>>> "+resultado+" ["+monedaDestino+"]");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 7:
                    System.out.println("\nFinalizando Programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }while(opcion != 7);
    }

    private int obtenerOpcionUsuario(){
        Scanner scanner = new Scanner(System.in);
        boolean opcionValida;
        int opcion;
        do{
            opcion=0;
            System.out.print("Elija una opción válida: ");
            try {
                opcion = scanner.nextInt();
                opcionValida= true;
            } catch (Exception e) {
                System.out.println("La opción ingresada no es válida. Debe ingresar un número.\n");
                opcionValida=false;
                scanner.nextLine();
            }
        }while(!opcionValida);

        return opcion;
    }

    private Double obtenerCantidadUsuario(){
        Scanner scanner = new Scanner(System.in);
        boolean opcionValida;
        double cantidad;
        do{
            cantidad=0.0;
            System.out.print("Ingrese el valor que desea convertir: ");
            try {
                cantidad = scanner.nextDouble();
                if(cantidad<=0){
                    System.out.println("No se admiten valores negativos\n");
                    opcionValida = false;
                }else{
                    opcionValida= true;
                }
            } catch (Exception e) {
                System.out.println("La opción ingresada no es válida. Debe ingresar un número.\n");
                opcionValida=false;
                scanner.nextLine();
            }
        }while(!opcionValida);

        return cantidad;
    }

    public Conversor obtenerConversorDeJson(String monedaBase, String monedaDestino) throws IOException, InterruptedException {
        GestionApi gestionApi = new GestionApi();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        ConversorDTO conversorDTO = gson.fromJson(gestionApi.getJson(monedaBase) , ConversorDTO.class);


        Conversor conversor = new Conversor();
        conversor.setMonedaLocal(conversorDTO.baseCode());
        Map<String,Double> listaConversionRates = new HashMap<>();
        listaConversionRates = conversorDTO.conversionRates();
        conversor.setValorMonedaDestino(listaConversionRates.get(monedaDestino));

        return conversor;
    }

    public Double convertirMoneda(String monedaBase, String monedaDestino, Double cantidad) throws IOException, InterruptedException {
        Conversor conversor = obtenerConversorDeJson(monedaBase, monedaDestino);
        Double cambio = conversor.getValorMonedaDestino();

        return cantidad*cambio;
    }

}
