
package ServerPackage;


import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2_1 {
    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(1234);

        System.out.println("Je suis un serveur en attente");

        Socket socket = serverSocket.accept();

        System.out.println("Un client est connecté");

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        InputStream is=socket.getInputStream();
        DataInputStream dis=new DataInputStream(is);
        while(true) {

            String ch = dis.readUTF();
            if (ch.equals("0")) {
                System.out.println("le client a mis 0 donc on va Quitter");
                break;
            }
            String op = dis.readUTF();

            String aString = dis.readUTF();

            String bString = dis.readUTF();

            System.out.println("Premier nombre recu : " + aString);
            System.out.println("Operateur recu : " + op);
            System.out.println("Deuxieme nombre recu : " + bString);


            try {
                double a = Double.parseDouble(aString);
                double b = Double.parseDouble(bString);
                double r = 0;

                switch (op) {
                    case "+":
                        r = a + b;
                        break;
                    case "-":
                        r = a - b;
                        break;
                    case "*":
                        r = a * b;
                        break;
                    case "/":
                        if (b == 0) {
                            dos.writeUTF(
                                    "Erreur : division par zero"
                            );
                            continue;
                        } else {
                            r = a / b;
                        }
                        break;
                    default:
                        dos.writeUTF("Erreur : operateur invalide");
                        continue;
                }

                dos.writeUTF(String.valueOf(r));
                System.out.println("Résultat envoyé : " + r);
            }catch (NumberFormatException e) {
                dos.writeUTF("Erreur : nombres invalides");
            }
            System.out.println("Nombres reçus : " + aString + op + bString);





        }
        dis.close();
        dos.close();
        socket.close();
        serverSocket.close();
    }

}
