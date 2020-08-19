package Business;

import DTO.ProjectDTO;
import Entities.Project;

public class ProjectManager {

    /**
     * Bind DTO to Project, add a notifier to project, that whenever project persists or update,
     * DTO will supdate
     * @param project
     * @param dto
     */
    public static void bindProjectToDTO(Project project, ProjectDTO dto) {
        dto.copyInfo(project);

        Notifier<Project> notifier = new Notifier<Project>() {
            @Override
            public void notifyChange(Project project) {
                dto.copyInfo(project);
            }
        };
        project.getNotifiers().add(notifier);
    }

}
