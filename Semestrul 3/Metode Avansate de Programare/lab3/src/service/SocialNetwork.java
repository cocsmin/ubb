package service;

import domain.*;
import repository.database.FriendshipDBRepo;
import repository.database.UserDBRepo;
import validator.*;
import repository.InMemoryRepo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SocialNetwork {

    private final UserDBRepo userRepo;
    private final FriendshipDBRepo frRepo;

    public SocialNetwork(UserDBRepo userRepo, FriendshipDBRepo frRepo) {
        this.userRepo = userRepo;
        this.frRepo = frRepo;
    }

    public Iterable<User> getUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findUser(Long id) {
        return userRepo.findOne(id);
    }

    public Long getNewUserId() {
        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public void addUser(User user) {
        user.setId(getNewUserId());
        userRepo.save(user);
    }

    public Iterable<Friendship> getFriendships() {
        return frRepo.findAll();
    }

    public Optional<User> removeUser(Long id) {
        try {
            Optional<User> u = userRepo.findOne(id);
            if (u.isEmpty())
                throw new IllegalArgumentException("Userul nu exista!");

            List<Long> toRemove = StreamSupport.stream(frRepo.findAll().spliterator(), false)
                    .filter(f -> f.getIdUser1().equals(id) || f.getIdUser2().equals(id))
                    .map(Friendship::getId)
                    .collect(Collectors.toList());

            toRemove.forEach(frRepo::delete);

            Optional<User> user = userRepo.delete(id);

            u.ifPresent(userToRemove ->
                    userToRemove.getFriends().forEach(friend -> friend.removeFriend(u))
            );

            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("User invalid!");
        }
        return Optional.empty();
    }

    public Long getNewFriendshipId() {
        return StreamSupport.stream(frRepo.findAll().spliterator(), false)
                .map(Friendship::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public void addFriendship(Friendship friendship) {
        Optional<User> user1 = userRepo.findOne(friendship.getIdUser1());
        Optional<User> user2 = userRepo.findOne(friendship.getIdUser2());

        if (getFriendships() != null) {
            boolean friendshipExists = StreamSupport.stream(getFriendships().spliterator(), false)
                    .anyMatch(f -> (f.getIdUser1().equals(friendship.getIdUser1()) && f.getIdUser2().equals(friendship.getIdUser2()))
                            || (f.getIdUser1().equals(friendship.getIdUser2()) && f.getIdUser2().equals(friendship.getIdUser1())));

            if (friendshipExists)
                throw new IllegalArgumentException("Prietenia asta deja exista!");

            if (user1.isEmpty() || user2.isEmpty())
                throw new IllegalArgumentException("Userul nu exista!");

            if (friendship.getIdUser1().equals(friendship.getIdUser2()))
                throw new ValidationException("Id urile nu pot fi aceleasi!");
        }

        friendship.setId(getNewFriendshipId());
        frRepo.save(friendship);

        user1.ifPresent(u -> u.addFriend(user2));
        user2.ifPresent(u -> u.addFriend(user1));
    }

    public void removeFriendship(Long id1, Long id2) {
        Optional<User> user1 = userRepo.findOne(id1);
        Optional<User> user2 = userRepo.findOne(id2);

        Long friendshipId = StreamSupport.stream(frRepo.findAll().spliterator(), false)
                .filter(f -> (f.getIdUser1().equals(id1) && f.getIdUser2().equals(id2))
                        || (f.getIdUser1().equals(id2) && f.getIdUser2().equals(id1)))
                .map(Friendship::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Prietenia asta nu exista!"));

        frRepo.delete(friendshipId);

        user1.ifPresent(u -> u.removeFriend(user2));
        user2.ifPresent(u -> u.removeFriend(user1));
    }

    public List<Optional<User>> getFriends(User user) {
        List<Optional<User>> friends = new ArrayList<>();
        getFriendships().forEach(friendship -> {
            if (friendship.getIdUser1().equals(user.getId())) {
                friends.add(findUser(friendship.getIdUser2()));
            } else if (friendship.getIdUser2().equals(user.getId())) {
                friends.add(findUser(friendship.getIdUser1()));
            }
        });
        return friends;
    }
}
