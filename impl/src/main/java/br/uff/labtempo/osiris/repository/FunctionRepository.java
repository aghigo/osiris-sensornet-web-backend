package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface FunctionRepository {
    InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException;
    URI createOnVirtualSensorNet(InterfaceFnTo interfaceFnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException;
    FunctionVsnTo getFromVirtualSensorNet(String functionName) throws AbstractClientRuntimeException, AbstractRequestException;
}
