package com.jit.server.management.repo;

import com.jit.server.management.model.Server;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface ServerRepo  extends MongoRepository<Server, BigInteger> {

    Server findByIpAddress(String ipAddress);
}
