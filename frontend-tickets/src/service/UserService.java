package service;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import model.Role;
import model.User;

import java.io.IOException;
import java.util.Vector;

public class UserService {

    private BaseService baseService;
    private Gson gson;

    public UserService() {
        this.baseService = new BaseService();
        this.gson = new Gson();
    }

    public Vector<User> getUsers() throws IOException {

        String usersJson = this.baseService.get(Consts.URI_GET_USERS);
        Type listType = new TypeToken<Vector<User>>(){}.getType();
        Vector<User> users = gson.fromJson(usersJson,listType);

        return users;

    }

    public Vector<Role> getRoles() throws IOException {

        String roleJson = this.baseService.get(Consts.URI_GET_ROLES);
        Type listType = new TypeToken<Vector<Role>>(){}.getType();
        Vector<Role> roles = gson.fromJson(roleJson,listType);

        return roles;

    }

    public String getProjects(User user) throws IOException {

        String ID = user.getID();
        String uri = Consts.URI_GET_PROJECTS_BY_USER + "/" + ID + "/state";

        return this.baseService.get(uri);

    }

}
