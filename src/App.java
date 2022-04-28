import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        //============ Parallel List's ==================
        //1.- Clients
        String [] userList = new String [100]; //username
        String [] passList = new String [100];
        double [] balList = new double [100];
        String [] emailList = new String [100];
        //2.- Products
        String [] productList = new String [1000];
        double [] priceList = new double [1000];
        int [] availableUnitList = new int [1000];
        int [] boughtAmountsList = new int [1000];
        
        //======= Files ========
        File clientFile = new File ("Clientes.txt");
        File productFile = new File ("Productos.txt");
        File sellsFile = new File ("Ventas.txt");
        //======== Call read functions =========
        //Clients
        int clientsCount = readClients(clientFile, userList, passList, balList, emailList);

        //Products
        int productsCount = readProducts(productFile, productList, priceList, availableUnitList);

        //Sells
        readSells(sellsFile, boughtAmountsList, productList, productsCount);

        //=====INFORMATION=====
        //clients
        System.out.println("Cantidad de clientes: " + clientsCount);
        System.out.println("Usuarios  /  Contraseñas");
        for (int i = 0; i < clientsCount; i++) {
            System.out.println(userList[i] + "  " + passList[i]);
        }

        //========= LOGIN ===========
        int userPos = login(userList, passList, clientsCount);

        //========= MENU ============
        int clientMenuOption = clientMenu(userList[userPos]);

        //options
        if (clientMenuOption == 1) {

        } else if (clientMenuOption == 2) {
            //ChooseProduct
            chooseProduct(productsCount, productList, availableUnitList);

        } else if (clientMenuOption == 3) {

        } else if (clientMenuOption == 4) {

        } else if (clientMenuOption == 5) {

        } else if (clientMenuOption == 6) {

        } else if (clientMenuOption == 7) {

        }

    }

    //========================================== METHODS AND FUNCTIONS ==============================================

    public static int readClients (File clientFile, String [] clients, String [] pass, double [] bal, String [] email) throws IOException{

        Scanner read = new Scanner (clientFile);

        int pos = 0;
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String [] split = line.split(",");

            clients[pos] = split[0];
            pass[pos] = split[1];
            bal[pos] = Double.parseDouble(split[2]);
            email[pos] = split[3];

            pos++;
        }
        read.close();
        return pos;
    }

    public static int readProducts (File productsFile, String [] products, double [] prices, int [] availableUnits) throws IOException{
        int productsCount = 0;

        Scanner read = new Scanner (productsFile);

        int pos = 0;
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String [] split = line.split(",");

            products[pos] = split[0];
            prices[pos] = Double.parseDouble(split[1]);
            availableUnits[pos] = Integer.parseInt(split[2]);
        }

        return productsCount;
    }

    public static void readSells (File sellsFile, int [] boughtAmount, String [] products, int productsCount) throws IOException{
        
        Scanner read = new Scanner (sellsFile);

        while (read.hasNextLine()) {
            String line = read.nextLine();
            String [] split = line.split(",");

            for (int i = 0; i < productsCount; i++) {
                if (products[i].equals(split[0])) {
                    boughtAmount [i] = Integer.parseInt(split[1]);
                }
            }
            
        }
    }

    public static int login (String [] users, String [] passwords, int clientsCount) throws IOException{
        Scanner scan = new Scanner (System.in);

        boolean unsucessLogin = true;

        int pos = -1;

        while (unsucessLogin){
            System.out.println("======Iniciar Sesión======");
            System.out.print("Usuario: ");
            String user = scan.nextLine();
            System.out.print("Contraseña: ");
            String pass = scan.nextLine();

            if (user.equals("ADMIN") && pass.equals("NYAXIO")){ //Admin account
                pos = -2; // index = -2 represents ADMIN account
                unsucessLogin = false;
                System.out.println("Accediendo al menú de administrador ...");
            } else { //User account
                for (int i = 0; i < clientsCount; i++) {
                    if (users[i].equals(user) && passwords[i].equals(pass)) {
                        pos = i;
                        System.out.println("Accediendo a la cuenta de usuario ...");
                        unsucessLogin = false;
                    } else if (users[i].equals(user) && !passwords[i].equals(pass)) {
                        System.out.println("Contraseña incorrecta, porfavor vuelva a ingresar los datos.");
                    }
                }
            }
        }

        return pos;
        
    }

    public static int clientMenu (String user) {
        Scanner scan = new Scanner (System.in);
        System.out.println("");
        System.out.println("Bienvenido " + user);
        
        boolean incorrectOption = true;
        int option = 0;
        while (incorrectOption){
            System.out.println("=========== Menú Persona ==========");
            System.out.println("Qué desea realizar?");
            System.out.println("1.- Elegir un producto");
            System.out.println("2.- Cambiar contraseña");
            System.out.println("3.- Ver catálogo de producto");
            System.out.println("4.- Ver saldo");
            System.out.println("5.- Recargar saldo");
            System.out.println("6.- Ver carrito");
            System.out.println("7.- Quitar del carrito");
            System.out.println("8.- Pagar carrito");
            System.out.print("Seleccione una opción: ");
            option = scan.nextInt();

            if (option >= 1 && option <= 8){
                incorrectOption = false;
            } else {
                System.out.println("");
                System.out.println("Porfavor, selecciona una opción correcta (1 - 8)");
                System.out.println("");
            } 
            System.out.println("");
        }

        return option;
    }

    public static void chooseProduct (int productsCount, String [] products, int [] availableUnits) { //Return units bought. If 
        Scanner scan = new Scanner (System.in);

        System.out.println("");
        System.out.println("------------------");
        System.out.println("Elegir un producto");
        System.out.println("------------------");
        System.out.print("Ingrese el nombre del producto: ");
        String product = scan.nextLine();

        for (int i = 0; i < productsCount; i++) {
            if (products[i].equals(product)) { //Product exist
                if(availableUnits[i] > 0) { //available stock
                    boolean invalidUnits = true;
                    while(invalidUnits){
                        System.out.print("Cuántas unidades desea comprar?: ");
                        int units = scan.nextInt();
                        if (units <= 0) {
                            System.out.println("Porfavor, ingrese una unidad válida para comprar");
                        } else {
                            if ((availableUnits[i] - units) >= 0){ //can buy
                                //add to shopping cart
                                invalidUnits = false;
                            } else {
                                invalidUnits = false;
                                System.out.println("Lo sentimos, no hay suficientes unidades de " + product + " disponibles actualmente.");
                            }
                        }
                    }

                } else {
                    System.out.println("Lo sentimos, no hay stock disponible para este producto.");
                }
            } else {
                System.out.println("Lo sentimos, este producto no existe en nuestro catálogo.");
            }
        }
    }

} //Finish class
