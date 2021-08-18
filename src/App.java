import java.util.ArrayList;
import java.util.Scanner;

import inventories.Stock;
import users.*;

public class App {
  public static ArrayList<User> users = new ArrayList<>();

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    int response;
    
    do {
      users.clear();
      getUsers();

      System.out.println("-- Bem vindo ao gerenciador de estoque! --");
      System.out.println("1- Cadastrar");
      System.out.println("2- Logar");
      System.out.println("3- Sair");
      System.out.println("-- O que desja fazer? ");

      response = scan.nextInt();
      scan.nextLine();

      if(response == 1) {
        Client client = new Client();
        client = client.register();

        menu(client);
      }

      if(response == 2) {
        System.out.println("----------- Login -----------");        
        System.out.println("Digite seu email: ");
        String email = scan.nextLine();
        System.out.println("Digite sua senha: ");
        String password = scan.nextLine();

        authenticate(email, password);
      }

    } while(response != 3);
    
    scan.close();
  }

  public static void getUsers() {
    User adm = (User) Admin.init();
    users.add(adm);
    
    ArrayList<Client> clients = Client.database;
    for(Client client : clients) {
      User user = (User) client;
      users.add(user);
    }
  }

  public static void authenticate(String email, String password) {
    for(User user : users) {
      String pass = user.getPassword();
      if(user.email.equals(email) && pass.equals(password)) {
        if(user instanceof Client) {
          Client logged = (Client) user;
          menu(logged);
        }
        if(user instanceof Admin) {
          Admin logged = (Admin) user;
          menu(logged);
        }
      }
    }
  }

  public static void menu(Client user) {
    Scanner scan = new Scanner(System.in);

    int res;

    System.out.println(" --- Bem vindo " + user.name + "! ---");

    do {
      System.out.println("---------------------------------------------------------------");
      System.out.println("1- Ver Estoque");
      System.out.println("2- Adicionar produto ");
      System.out.println("3- Remover produto");
      System.out.println("4- Ver perfil");
      System.out.println("5- Logout");
      System.out.println("---------------------------------------------------------------");

      res = scan.nextInt();

      if(res == 1) {
        Stock.getProducts(user);
      }
      if(res == 2) {
        Stock.addProducts(user);
      }
      if(res == 3) {
        Stock.removeProduct(user);
      }
      if(res == 4) {
        user.profile();
      }
    } while(res != 5);
  }

  public static void menu(Admin user) {
    Scanner scan = new Scanner(System.in);

    int res;

    System.out.println("Bem vindo " + user.name + "!");

    do {
      System.out.println("---------------------------------------------------------------");
      System.out.println("1- Listar usuário");
      System.out.println("2- Cadastrar inspetor");
      System.out.println("3- Listar estoques");
      System.out.println("4- Logout");
      System.out.println("---------------------------------------------------------------");
      res = scan.nextInt();

      if(res == 1) {
        user.getUsers(user);
      }
    } while(res != 4);
  }
}
