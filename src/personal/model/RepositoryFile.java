package personal.model;

import personal.model.mapper.Mapper;
import personal.model.mapper.UserMapper;
import personal.model.mapper.UserMapperSplitByElement;

import java.util.ArrayList;
import java.util.List;

public class RepositoryFile implements Repository {
    private Mapper mapper;
    private FileOperation fileOperation;

    public RepositoryFile(FileOperation fileOperation, Mapper mapper) {
        this.fileOperation = fileOperation;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAllUsers() {
        List<String> lines = fileOperation.readAllLines();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.map(line));
        }
        return users;
    }

    @Override
    public String CreateUser(User user) {

        List<User> users = getAllUsers();
        int max = 0;
        for (User item : users) {
            int id = Integer.parseInt(item.getId());
            if (max < id){
                max = id;
            }
        }
        int newId = max + 1;
        String id = String.format("%d", newId);
        user.setId(id);
        users.add(user);
        List<String> lines = mapToString(users);
        fileOperation.saveAllLines(lines);
        return id;
    }

    private List<String> mapToString(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User item: users) {
            lines.add(mapper.map(item));
        }
        return lines;
    }

    @Override
    public User updateUser(User user) {
        List<User> users = getAllUsers();
        for (User currentUser: users) {
            if (currentUser.getId().equals(user.getId())){
                currentUser.setFirstName(user.getFirstName());
                currentUser.setLastName(user.getLastName());
                currentUser.setPhone(user.getPhone());
            }
        }
        fileOperation.saveAllLines(mapToString(users));
        return user;
    }

    @Override
    public User deleteUser(User user) {
        List<User> users = getAllUsers();
        users.remove(user);
        fileOperation.saveAllLines(mapToString(users));
        return user;
    }
}
