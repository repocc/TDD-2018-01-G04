package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Project;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Vector;

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

    public Vector<Project> getProjects() throws IOException {

        String projectsJson = this.httpService.get(Consts.URI_GET_PROJECTS);
        Type listType = new TypeToken<Vector<Project>>(){}.getType();
        Vector<Project> projects = gson.fromJson(projectsJson,listType);

        return projects;

    }

    public Project getProjet(Project project) throws IOException {

        String ID = project.getID();
        String uri = Consts.URI_GET_PROJECT + "/" + ID;

        String response = this.httpService.get(uri);
        Project projectResponse = gson.fromJson(response,Project.class);

        return projectResponse;

    }

}
