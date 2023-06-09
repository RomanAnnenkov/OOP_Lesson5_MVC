package personal.model.mapper;

import personal.model.User;

public class UserMapperSplitByElement implements Mapper{
    private final String separator;

    public UserMapperSplitByElement() {
        this.separator = ",";
    }

    public UserMapperSplitByElement(String separator) {
        this.separator = separator;
    }

    @Override
    public String map(User user) {
        return user.getId() + separator +
                user.getFirstName() + separator +
                user.getLastName() + separator +
                user.getPhone();
    }

    @Override
    public User map(String line) {
        String[] lines = line.split(separator);
        return new User(lines[0], lines[1], lines[2], lines[3]);
    }
}
