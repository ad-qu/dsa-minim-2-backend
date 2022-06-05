package edu.upc.eetac.dsa.dao.impl;

import edu.upc.eetac.dsa.dao.UserDAO;
import edu.upc.eetac.dsa.models.User;
import edu.upc.eetac.dsa.models.UserRanking;

import java.beans.IntrospectionException;
import java.util.*;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {

    static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    private static UserDAOImpl manager;
    private SessionImpl session;

    UserDAOImpl() {
        this.session = SessionImpl.getInstance();
    }

    public static UserDAOImpl getInstance() {

        if(manager == null) {

            manager = new UserDAOImpl();
        }
        return manager;
    }

    ///+++ PART 2 DEL MINIM 2 -> GET ranking by points +++///

    @Override
    public List<UserRanking> getRanking() {

        List<User> userList = this.session.getAll(User.class);
        List<UserRanking> userRank = new ArrayList<>();

        Collections.sort(userList, new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                if (o1.getPoints() >= o2.getPoints())
                    return 1;
                if (o1.getPoints() < o2.getPoints())
                    return -1;
                return 0;
            }
        });

        UserRanking u = new UserRanking();

        for (int i = 0; i < userList.size(); i ++) {

            String name = userList.get(i).getName();
            int points = userList.get(i).getPoints();

            u.setName(name); u.setPoints(points);
            userRank.add(u);
        }
        return userRank;
    }

    ////////////////////////////////////////////////////////

    // OK
    @Override
    public User addUser(String name, String password, String email) {
        User user = new User(name, password, email);
        logger.info("new user: " + user.toString());
        session.save(user);
        return user;
    }

    // NOT OK
    @Override
    public User updateUser(String oldUsername, String name, String password, String email) throws IntrospectionException {
        User user = (User) this.session.getByName(User.class, oldUsername);
        //String id = user.getId();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        this.session.update(user);
        return user;
    }

    // OK
    @Override
    public User getUserById(String id) {
        User user = (User) this.session.getById(User.class, id);
        if (user.getName() == null) return null;
        logger.info("user by id: " + user.toString());
        return user;
    }

    // OK
    @Override
    public User getUserByName(String name) {
        User user = (User) this.session.getByName(User.class, name);
        if (user.getName() == null) return null;
        logger.info("user by name: " + user.toString());

        return user;
    }

    // OK
    @Override
    public User getUserByEmail(String email) {
        List<User> userList = this.getAllUsers();
        for (User user : userList) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    // OK
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        session.getAll(User.class).forEach(u -> userList.add((User) u));
        return userList;
    }

    // NOT OK
    @Override
    public void deleteUser(String name) throws IntrospectionException {
        User u = (User) this.session.getByName(User.class, name);
        session.delete(u);
        logger.info("Deleted user: " + u.toString());
    }


}