package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface that abstract Blending DAO implementations
 * @see br.uff.labtempo.osiris.dao.omcp.BlendingOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface BlendingRepository {
    List<BlendingVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException;
    BlendingVsnTo getById(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException;
    URI create(BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException;
    void update(long blendingId, BlendingVsnTo blendingVsnTo) throws AbstractClientRuntimeException, AbstractRequestException;
    void delete(long blendingId) throws AbstractClientRuntimeException, AbstractRequestException;
}
