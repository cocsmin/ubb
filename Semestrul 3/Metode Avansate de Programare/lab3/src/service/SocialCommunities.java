package service;

import domain.Friendship;
import domain.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SocialCommunities {

    SocialNetwork socialNetwork;
    HashMap<Long, List<Long>> adjList;

    public SocialCommunities(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    void DFS(Long v, HashMap<Long, Boolean> vizitat) {
        vizitat.put(v, true);
        System.out.println(v + " " + socialNetwork.findUser(v).get().getNume() + " " + socialNetwork.findUser(v).get().getPrenume());

        Optional.ofNullable(adjList.get(v)).ifPresent(friends ->
                friends.stream()
                        .filter(u -> !vizitat.containsKey(u))
                        .forEach(u -> DFS(u, vizitat))
        );
    }

    public int connectedCommunities() {
        adjList = new HashMap<>();

        StreamSupport.stream(socialNetwork.getUsers().spliterator(), false)
                .forEach(user -> {
                    List<Long> friends = StreamSupport.stream(socialNetwork.getFriendships().spliterator(), false)
                            .filter(friendship -> friendship.getIdUser1().equals(user.getId()) || friendship.getIdUser2().equals(user.getId()))
                            .map(friendship -> friendship.getIdUser1().equals(user.getId()) ? friendship.getIdUser2() : friendship.getIdUser1())
                            .collect(Collectors.toList());

                    if (!friends.isEmpty()) {
                        adjList.put(user.getId(), friends);
                    }
                });

        List<Long> ids = StreamSupport.stream(socialNetwork.getUsers().spliterator(), false)
                .map(User::getId)
                .collect(Collectors.toList());

        HashMap<Long, Boolean> vizitat = new HashMap<>();
        int nrCom = 0;

        for (Long v : ids) {
            if (!vizitat.containsKey(v)) {
                DFS(v, vizitat);
                nrCom++;
                System.out.println();
            }
        }
        return nrCom;
    }

    public List<Long> TheMostSocialCom() {
        adjList = new HashMap<>();
        List<Long> max = new ArrayList<>();

        StreamSupport.stream(socialNetwork.getUsers().spliterator(), false)
                .forEach(user -> {
                    List<Long> friends = StreamSupport.stream(socialNetwork.getFriendships().spliterator(), false)
                            .filter(friendship -> friendship.getIdUser1().equals(user.getId()) || friendship.getIdUser2().equals(user.getId()))
                            .map(friendship -> friendship.getIdUser1().equals(user.getId()) ? friendship.getIdUser2() : friendship.getIdUser1())
                            .collect(Collectors.toList());

                    if (!friends.isEmpty()) {
                        adjList.put(user.getId(), friends);
                        if (max.size() < friends.size() + 1) {
                            max.clear();
                            max.addAll(friends);
                            max.add(user.getId());
                        }
                    }
                });

        return max;
    }
}
