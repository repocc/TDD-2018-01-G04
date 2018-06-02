package service;

import com.google.gson.Gson;
import model.User;

import java.io.IOException;

public class UserService {

    private HttpService httpService;
    //private Gson gson;

    public UserService() {
        this.httpService = new HttpService();
        //this.gson = new Gson();
    }

    public String getUsers() throws IOException {

        return this.httpService.get(Consts.URI_GET_USERS);

    }

    public String getRoles() throws IOException {

        return this.httpService.get(Consts.URI_GET_ROLES);

    }

    public String getProjects(User user) throws IOException {

        String ID = user.getID();
        String uri = Consts.URI_GET_PROJECTS_BY_USER + "/" + ID + "/state";

        return this.httpService.get(uri);

    }

}
