package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.dao.CollectorOmcpDao;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

import java.util.List;

/**
 * Collector interface that abstracts Collector DAO implementations
 * @see CollectorOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface CollectorRepository {
    CollectorSnTo getByNetworkId(String networkId, String collectorId) throws AbstractRequestException, AbstractClientRuntimeException;
    List<CollectorSnTo> getAllByNetworkId(String networkId) throws AbstractRequestException, AbstractClientRuntimeException;
}
