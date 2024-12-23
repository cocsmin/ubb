package map.socialnetwork;

import map.socialnetwork.domain.*;
import map.socialnetwork.repository.*;
import map.socialnetwork.repository.database.FriendshipDBRepo;
import map.socialnetwork.repository.database.UserDBRepo;
import map.socialnetwork.service.*;
import map.socialnetwork.validator.*;
//import map.socialnetwork.ui.Console;

public class Main {
    public static void main(String[] args) {

        UserDBRepo userDBRepo = new UserDBRepo(new UserValidator(), "jdbc:postgresql://localhost:5432/postgres", "postgres", "parolasmechera");
        FriendshipDBRepo frDBRepo = new FriendshipDBRepo(new FriendshipValidator(userDBRepo), "jdbc:postgresql://localhost:5432/postgres", "postgres", "parolasmechera");

        SocialNetwork socialNetwork = new SocialNetwork(userDBRepo, frDBRepo);
        //Console console = new Console(socialNetwork);
        /*
        User u1 = new User("Secrier", "Cosmin");
        User u2 = new User("Racz", "Alexandra");
        User u3 = new User("Ungurean", "Catalina");
        User u4 = new User("Asandei", "Georgiana");
        User u5 = new User("Munteanu", "Ghita");
        User u6 = new User("Sabaceag", "Marius");
        User u7 = new User("Bota", "Robert");
        User u8 = new User("Fiscu", "Matei");

        socialNetwork.addUser(u1);
        socialNetwork.addUser(u2);
        socialNetwork.addUser(u3);
        socialNetwork.addUser(u4);
        socialNetwork.addUser(u5);
        socialNetwork.addUser(u6);
        socialNetwork.addUser(u7);
        socialNetwork.addUser(u8);
        */
        //console.run();
    }
}