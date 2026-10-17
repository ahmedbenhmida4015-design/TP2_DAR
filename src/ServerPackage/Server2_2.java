
package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import commonPackage.Operation;

public class Server2_2 {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            System.out.println("Je suis un serveur en attente");

            try (Socket socket = serverSocket.accept()) {

                System.out.println("Un client est connecte");

                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.flush();

                ObjectInputStream ois =
                        new ObjectInputStream(socket.getInputStream());

                while (true) {

                    // Recevoir l'objet suivant
                    Object recu = ois.readObject();

                    // Verifier si le client veut quitter
                    if (recu instanceof String
                            && recu.equals("FIN")) {
                        System.out.println("Fin de la communication");
                        break;
                    }

                    // Verifier que l'objet recu est une Operation
                    if (recu instanceof Operation) {

                        Operation op = (Operation) recu;

                        double a = op.getOperande1();
                        char operateur = op.getOperateur();
                        double b = op.getOperande2();

                        System.out.println("Operation recue : "
                                + a + " " + operateur + " " + b);

                        String reponse =
                                calculer(a, operateur, b);

                        // Envoyer la reponse au client
                        oos.writeObject(reponse);
                        oos.flush();

                        System.out.println("Reponse envoyee : "
                                + reponse);

                    } else {
                        oos.writeObject("Erreur : objet inconnu");
                        oos.flush();
                    }
                }

                ois.close();
                oos.close();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Classe inconnue : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur cote serveur : " + e.getMessage());
        }
    }

    static String calculer(double a, char operateur, double b) {

        double r;

        switch (operateur) {

            case '+':
                r = a + b;
                break;

            case '-':
                r = a - b;
                break;

            case '*':
                r = a * b;
                break;

            case '/':
                if (b == 0) {
                    return "Erreur : division par zero impossible";
                }
                r = a / b;
                break;

            default:
                return "Erreur : operateur inconnu";
        }

        return formater(a) + " " + operateur + " "
                + formater(b) + " = " + formater(r);
    }

    static String formater(double v) {

        if (v == Math.rint(v) && !Double.isInfinite(v)) {
            return String.valueOf((long) v);
        }

        return String.valueOf(v);
    }
}
