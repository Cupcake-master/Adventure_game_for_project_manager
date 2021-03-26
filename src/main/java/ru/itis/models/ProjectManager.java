package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectManager {
    private Long reputation;
    private Long competence;
    private Long leadership;
    private Long communications;
    private Long time;
    private Long budget;

    public ProjectManager() {
        reputation = 50L;
        competence = 50L;
        leadership = 50L;
        communications = 50L;
        time = 50L;
        budget = 50L;
    }
}
