package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;

import java.net.URI;
import java.net.URISyntaxException;

public interface OmcpRepository<T> {
    T doGet(String uri) throws AbstractRequestException, AbstractClientRuntimeException;

    URI doPost(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException;

    void doPut(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException;

    void doDelete(String uri) throws AbstractRequestException, AbstractClientRuntimeException;

    void doNotify(String uri, T t) throws AbstractRequestException, AbstractClientRuntimeException;
}
