package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.stereotype.Component;

import java.util.List;

public interface NetworkRepository {
    NetworkCoTo getById(String id);
    List<NetworkCoTo> getAll();
}
