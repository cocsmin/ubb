package ui;

import domain.*;
import service.*;
import validator.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Console {

    private SocialNetwork socialNetwork;
    private SocialCommunities socialCommunities;

    public Console(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        this.socialCommunities = new SocialCommunities(socialNetwork);
    }

    void printMenu(){
        System.out.println("MENIU");
        System.out.println("1) Adauga user");
        System.out.println("2) Sterge user");
        System.out.println("3) Adauga prietenie");
        System.out.println("4) Sterge prietenie");
        System.out.println("5) Afiseaza userii");
        System.out.println("6) Afiseaza prieteniile");
        System.out.println("7) Afiseaza comunitati");
        System.out.println("8) Afiseaza cea mai sociala comunitate");

        System.out.println("0) EXIT");
    }

    void printUsers(){
        System.out.println("USERS");
        for (User u : socialNetwork.getUsers()){
            System.out.println("ID: " + u.getId() + " " + u.getNume() + " " + u.getPrenume());
        }
    }

    void addUser(){
        System.out.println("Adauga user");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Prenume: ");
        String prenume = scanner.nextLine();
        try{
            socialNetwork.addUser(new User(nume, prenume));
        }catch (ValidationException e){
            System.out.println("User invalid! ");
        }catch (IllegalArgumentException e){
            System.out.println("Argument invalid! ");
        }
    }

    void removeUser(){
        printUsers();
        System.out.println("Sterge user");
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        String var = scanner.nextLine();
        try {
            Long id = Long.parseLong(var);
            Optional<User> user = socialNetwork.findUser(id);
            if (!user.isPresent()){
                throw new ValidationException("Userul nu exista!");
            }
            socialNetwork.removeUser(id);
            System.out.println("Userul: " + user.get().getNume() + " " + user.get().getPrenume() + " a fost sters");
        }catch (IllegalArgumentException e){
            System.out.println("Id invalid! ");
        }
    }

    void printFriendships(){
        for (User u : socialNetwork.getUsers()){
            System.out.println("Prietenii userului: " + u.getNume() + " " + u.getPrenume() + "(Nr prieteni: " + socialNetwork.getFriends(u).size() + ")");
            socialNetwork.getFriends(u).forEach(f -> {
                    System.out.println(f.get().getId() + " " + f.get().getNume() + " " + f.get().getPrenume());
            });
        }
    }

    void addFriendship(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID ul primului user: ");
        String var1 = scanner.nextLine();
        System.out.println("ID ul pt al doilea user: ");
        String var2 = scanner.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            }catch (IllegalArgumentException e){
                System.out.println("Id invalid! ");
            }
            socialNetwork.addFriendship(new Friendship(id1, id2, LocalDateTime.now()));
        }catch (ValidationException e){
            System.out.println("Prietenie invalida! ");
        }catch (IllegalArgumentException e){
            System.out.println("Argumente invalide! ");
        }
    }

    public void removeFriendship(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID ul primului user: ");
        String var1 = scanner.nextLine();
        System.out.println("ID ul pt al doilea user: ");
        String var2 = scanner.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e) {
                System.out.println("Id invalid! ");
            }
            socialNetwork.removeFriendship(id1, id2);
        }catch (IllegalArgumentException e){
            System.out.println("Argumente invalide! ");
        }
    }

    private void printComCon(){
        System.out.println("Comunitati Sociale \n");
        int nrCom = socialCommunities.connectedCommunities();
        System.out.println("Numarul comunitatilor sociale: " + nrCom);
    }

    private void printTheMostSoc(){
        System.out.println("Cea mai sociala comunitate: ");
        List<Long> tmsc = socialCommunities.TheMostSocialCom();
        tmsc.forEach(System.out::println);
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        boolean ok = true;
        while (ok){
            printMenu();
            String command = scanner.nextLine();
            switch (command){
                case "1":
                    addUser();
                    break;
                case "2":
                    removeUser();
                    break;
                case "3":
                    addFriendship();
                    break;
                case "4":
                    removeFriendship();
                    break;
                case "5":
                    printUsers();
                    break;
                case "6":
                    printFriendships();
                    break;
                case "7":
                    printComCon();
                    break;
                case "8":
                    printTheMostSoc();
                    break;
                case "0":
                    System.out.println("exit...");
                    ok = false;
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }
}
