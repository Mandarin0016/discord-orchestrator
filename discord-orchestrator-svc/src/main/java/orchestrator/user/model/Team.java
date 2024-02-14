package orchestrator.user.model;

import jakarta.persistence.*;
import orchestrator.worker.model.Worker;

import java.util.List;
import java.util.UUID;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private UUID userRelationId;
    @ManyToMany
    private List<User> users;
    @ManyToMany
    private List<Worker> workers;

}
