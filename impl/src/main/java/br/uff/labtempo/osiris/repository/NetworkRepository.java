package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.osiris.to.collector.NetworkCoTo;

import java.util.List;

public interface NetworkRepository {
    NetworkCoTo selectById(String id);
    List<NetworkCoTo> selectAll();
    void insert(NetworkCoTo networkCoTo);
    void delete(String id);
    void update(String id, NetworkCoTo networkCoTo);
    void notify(NetworkCoTo networkCoTo);
}
