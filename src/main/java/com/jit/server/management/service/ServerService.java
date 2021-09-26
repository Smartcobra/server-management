package com.jit.server.management.service;

import com.jit.server.management.model.Server;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    List<Server> list();
    Server get(BigInteger id);
    Server update(Server server);
    Boolean delete(BigInteger id);
}
