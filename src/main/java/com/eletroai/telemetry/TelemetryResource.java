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
    public Response receiveMetrics(@Valid TelemetryRecord payload) {
        telemetryEmitter.send(payload);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
