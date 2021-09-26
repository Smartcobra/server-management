package com.jit.server.management.resource;

import com.jit.server.management.enumeration.Status;
import com.jit.server.management.model.Response;
import com.jit.server.management.model.Server;
import com.jit.server.management.service.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/server")
public class ServerResource {
    private final ServerService serverService;

    public ServerResource(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServer() {
        Map<String, List<Server>> serverData = new HashMap<String, List<Server>>() {{
            put("servers", serverService.list());
        }};

        return ResponseEntity.ok(
                new Response.ResponseBuilder
                        (LocalDateTime.now(), OK.value(), OK, "Server retrieved", serverData)
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        Map<String, Server> serverData = new HashMap<String, Server>() {{
            put("servers", server);
        }};

        return ResponseEntity.ok(
                new Response.ResponseBuilder
                        (LocalDateTime.now(), OK.value(), OK, server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping failed", serverData)
                        .build()
        );

    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Validated Server server) throws IOException {
        Map<String, Server> serverData = new HashMap<String, Server>() {{
            put("server", serverService.create(server));
        }};

        return ResponseEntity.ok(
                new Response.ResponseBuilder
                        (LocalDateTime.now(), CREATED.value(), CREATED, "Server Created", serverData)
                        .build()
        );

    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") BigInteger id) throws IOException {
        Map<String, Server> serverData = new HashMap<String, Server>() {{
            put("servers", serverService.get(id));
        }};

        return ResponseEntity.ok(
                new Response.ResponseBuilder
                        (LocalDateTime.now(), OK.value(), OK, "Server retrieved", serverData)
                        .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") BigInteger id) throws IOException {
        Map<String, Boolean> serverData = new HashMap<String, Boolean>() {{
            put("servers", serverService.delete(id));
        }};

        return ResponseEntity.ok(
                new Response.ResponseBuilder
                        (LocalDateTime.now(), OK.value(), OK, "Server Deleted", serverData)
                        .build()
        );

    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
    }

}