package com.example.demo.T4.checked;

import java.util.*;


class Server {
    static int refused;
    Set<Integer> connections;
    int freeSlots;

    public Server(int capacity) {
        this.connections = new HashSet<>(capacity);
        this.freeSlots = capacity;
    }

    public void connect(int id) {
        freeSlots--;
        connections.add(id);
    }

    public void disconnect(int id) {
        freeSlots++;
        connections.remove(id);
    }


    public static List<Server> initServers(String inputCapacity) {
        String[] numbers = inputCapacity.split(" ");
        return List.of(
                new Server(Integer.parseInt(numbers[0])),
                new Server(Integer.parseInt(numbers[1])),
                new Server(Integer.parseInt(numbers[2])));
    }

    public static Server getAvailablestServer(List<Server> inputList) throws RuntimeException {
        Server result = inputList.get(0);
        result = result.freeSlots < inputList.get(1).freeSlots ?
                inputList.get(1) : inputList.get(0);
        result = result.freeSlots < inputList.get(2).freeSlots ?
                inputList.get(2) : result;
        if (result.freeSlots == 0) {
            Server.refused++;
            throw new RuntimeException();
        }
        return result;
    }
}


class ConnectionModelling {
    static List<Server> servers;

    public static int processingInputLines(List<String> inputLines) {
        Iterator<String> iterator = inputLines.iterator();
        servers = Server.initServers(iterator.next());
        outer:
        while (iterator.hasNext()) {
            try {
                String[] string = iterator.next().split(":");
                if (string[1].equals("connect")) {
                    for (Server srv : servers) {
                        if (srv.connections.contains(Integer.parseInt(string[0]))) {
                            continue outer;
                        }
                    }
                    Server.getAvailablestServer(servers).connect(Integer.parseInt(string[0]));
                } else {
                    for (Server server : servers) {
                        if (server.connections.contains(Integer.parseInt(string[0]))) {
                            server.disconnect(Integer.parseInt(string[0]));
                            continue outer;
                        }
                    }
                }
            } catch (RuntimeException e) {
            }
        }
        return Server.refused;
    }
}



public class MMORPG {
    public static void main(String[] args) {
        /*ArrayList<String> strings = new ArrayList<>(List.of("2 2 2",
                "100001:connect",
                "100002:connect",
                "100003:connect",
                "100004:connect",
                "100005:connect",
                "100006:connect",
                "100007:connect"));*/
        ArrayList<String> strings = new ArrayList<>(List.of(
                /*"1 2 1",
                "100001:connect",
                "100002:connect",
                "100001:connect",
                "100003:connect",
                "100001:disconnect",
                "100004:connect",
                "100005:disconnect"*/
                /*"2 2 2",
                "900000:connect",
                "900001:connect",
                "900002:connect",
                "900003:connect",
                "900000:disconnect",
                "900004:connect"*/

                "1 1 1",
                "100001:connect",
                "100002:connect",
                "100003:connect",
                "100001:connect",
                "100002:connect",
                "100003:connect",
                "100004:connect",
                "100005:connect"
        ));
        System.out.println(ConnectionModelling.processingInputLines(strings));
    }
}