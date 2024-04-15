package orchestrator.api;

import orchestrator.api.dto.worker.PackageResult;
import orchestrator.api.dto.worker.IncomingPackage;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.worker.command.input.PackageInput;
import orchestrator.worker.command.output.PackageOutput;
import orchestrator.worker.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static orchestrator.api.Paths.BASE_PATH_V1;
import static orchestrator.api.mapper.DtoMapper.mapToPackageResult;
import static orchestrator.api.mapper.DtoMapper.mapToWorkerPackageInputCommand;

@RestController
@RequestMapping(BASE_PATH_V1 + "/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/{worker_id}")
    public ResponseEntity<PackageResult> createPackage(@PathVariable("worker_id") UUID workerId,
                                                       @Validated @RequestBody IncomingPackage incomingPackage,
                                                       @RequestHeader("Idempotency-Key") UUID idempotencyKey,
                                                       @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata) throws IllegalAccessException {

        UUID userId = UUID.fromString(authenticationMetadata.getUserId());
        PackageInput workerPackageInputCommand = mapToWorkerPackageInputCommand(incomingPackage, userId, workerId, idempotencyKey);
        PackageOutput workerPackageOutputCommand = workerService.savePackage(workerPackageInputCommand);

        PackageResult result = mapToPackageResult(workerPackageOutputCommand);

        return ResponseEntity.ok(result);
    }
}
