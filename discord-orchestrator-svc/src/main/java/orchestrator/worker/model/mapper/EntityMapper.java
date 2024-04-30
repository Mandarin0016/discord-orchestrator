package orchestrator.worker.model.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.worker.command.output.PackageOutput;
import orchestrator.worker.model.Package;

@UtilityClass
public class EntityMapper {

    public static PackageOutput mapToPackageOutput(Package pack) {

        return PackageOutput.builder()
                .idempotencyKey(pack.getIdempotencyKey())
                .status(pack.getStatus())
                .workerResponse(pack.getWorkerResponse())
                .build();
    }
}
