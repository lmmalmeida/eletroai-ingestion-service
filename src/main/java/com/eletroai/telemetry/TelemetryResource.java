package com.eletroai.telemetry;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/telemetry")               // 1. Define a rota base do "Controller"
@ApplicationScoped               // 2. Define o ciclo de vida no Quarkus
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelemetryResource {

    @Inject
    @Channel("eletroai-metrics")
    Emitter<TelemetryRecord> telemetryEmitter;

    @POST
    @Path("/metrics")
    @Operation(
            summary = "Ingerir métrica de energia",
            description = "Recebe o consumo em tempo real de um Shelly e encaminha-o de forma reativa para o broker Redpanda."
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "202",
                    description = "Métrica recebida com sucesso e colocada na calha de processamento"
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Payload inválido. Dados de métrica não respeitam as regras de validação"
            )
    })
    public Response receiveMetrics(@RequestBody(
            description = "Payload com os dados da telemetria do dispositivo",
            required = true,
            content = @org.eclipse.microprofile.openapi.annotations.media.Content(
                    schema = @Schema(implementation = TelemetryRecord.class)
            )
    ) @Valid TelemetryRecord payload) {
        telemetryEmitter.send(payload);
        return Response.accepted().build();
    }
}
