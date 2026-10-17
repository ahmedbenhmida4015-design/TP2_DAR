package ClientPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client2_1
{
    static void main(String[] args) throws IOException {
        System.out.println("je suis un client pas encore connecte");

        Socket socket = new Socket("localhost", 1234);

        System.out.println("je suis un client  connecte");

        Scanner sca = new Scanner(System.in);

        OutputStream os = socket.getOutputStream();

        DataOutputStream dos = new DataOutputStream(os);

        InputStream is = socket.getInputStream();

        DataInputStream dis = new DataInputStream(is);
        String ch;
        do {
            System.out.println("\n CALCULATRICE TCP ");
            System.out.println("1. Addition");
            System.out.println("2. Soustraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            ch = sca.nextLine();
            dos.writeUTF(ch);
            System.out.println("le chois envoyer au serveur ");
            if (ch.equals("0") ) {
                System.out.println("Fin de la comminication serveur et client");
                break;
            }
            String op;

            switch (ch) {
                case "1":
                    op = "+";
                    break;
                case "2":
                    op = "-";
                    break;
                case "3":
                    op = "*";
                    break;
                case "4":
                    op = "/";
                    break;
                default:
                    System.out.println("Choix invalide.");
                    continue;
            }

            System.out.print("Saisir le premier nombre : ");
            String a = sca.nextLine();

            System.out.print("Saisir le deuxième nombre : ");
            String b = sca.nextLine();

            // Envoyer les nombres au serveur
            dos.writeUTF(op);
            dos.writeUTF(a);
            dos.writeUTF(b);

            // Validation des nombres avant l'envoi
            try {
                Double.parseDouble(a);
                Double.parseDouble(b);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : saisissez des nombres valides.");
                continue;
            }

            System.out.println("Operation envoyee au serveur : "
                    + a + " " + op + " " + b);


            // Recevoir et afficher le resultat
            String resultat = dis.readUTF();

            System.out.println("Reponse du serveur : " + resultat);
        } while (true);

        System.out.println("la comminication de la serveur et client se terminer ");
        dos.close();
        dis.close();
        sca.close();
        socket.close();
    }
}
