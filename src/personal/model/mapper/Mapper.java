package personal.model.mapper;

import personal.model.User;

public interface Mapper {
    String map(User user);
    User map(String line);
}
