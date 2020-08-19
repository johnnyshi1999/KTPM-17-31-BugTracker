package Presentation.ProjectWindow;

import DTO.ProjectDTO;

public class ProjectWindowThread implements Runnable{
    ProjectDTO dto;
    public ProjectWindowThread(ProjectDTO dto) {
        this.dto = dto;
        
    }
    @Override
    public void run() {

    }
}
