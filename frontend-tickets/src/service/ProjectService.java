package service;

import com.google.gson.Gson;
import model.Project;

import java.io.IOException;

public class ProjectService {

    private HttpService httpService;
    private Gson gson;

    public ProjectService() {
        this.httpService = new HttpService();
        this.gson = new Gson();
    }


    public String postProject(Project project) throws IOException {

        String json = this.gson.toJson(project);

        String response = this.httpService.post(Consts.URI_POST_PROJECT,json);

        return response;

    }

    public String getProjet(Project project) throws IOException {

        String ID = project.getID();
        String uri = Consts.URI_GET_PROJECT + "/" + ID;

        String response = this.httpService.get(uri);

        return response;

    }

}
