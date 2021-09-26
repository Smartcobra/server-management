package com.jit.server.management.service.impl;

import com.jit.server.management.enumeration.Status;
import com.jit.server.management.model.Server;
import com.jit.server.management.repo.ServerRepo;
import com.jit.server.management.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.lang.Boolean.TRUE;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {
    @Autowired
    ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        server.setImageUrl(setServerImageURL());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        Server server= serverRepo.findByIpAddress(ipAddress);
        InetAddress address= InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? Status.SERVER_UP:Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public List<Server> list() {
        return serverRepo.findAll();

    }

    @Override
    public Server get(BigInteger id) {

        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(BigInteger id) {
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageURL() {
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

}
