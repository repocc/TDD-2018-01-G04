package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Project;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Vector;

public class ProjectService {

    private BaseService baseService;
    private Gson gson;

    public ProjectService() {
        this.baseService = new BaseService();
        this.gson = new Gson();
    }


    public String postProject(Project project) throws IOException {

        String json = this.gson.toJson(project);

        String response = this.baseService.post(Consts.URI_POST_PROJECT,json);

        return response;

    }

    public Vector<Project> getProjects() throws IOException {

        String projectsJson = this.baseService.get(Consts.URI_GET_PROJECTS);
        Type listType = new TypeToken<Vector<Project>>(){}.getType();
        Vector<Project> projects = gson.fromJson(projectsJson,listType);

        return projects;

    }

    public Project getProjectById(Project project) throws IOException {

        String ID = project.getID();
        String uri = Consts.URI_GET_PROJECT + "/" + ID;

        String response = this.baseService.get(uri);
        Project projectResponse = gson.fromJson(response,Project.class);

        return projectResponse;

    }

}
