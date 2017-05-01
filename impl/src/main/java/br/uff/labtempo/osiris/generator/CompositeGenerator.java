package br.uff.labtempo.osiris.generator;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible to generate randomly Composite sensor mock objects for VirtualSensorNet module
 * @see CompositeVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
public class CompositeGenerator {

    @Autowired
    private LinkRepository linkRepository;

    /**
     * Generate a randomly Composite sensor mock object
     * @see CompositeVsnTo
     * @return CompositeVsnTo
     * @throws AbstractRequestException
     */
    public CompositeVsnTo generateCompositeVsnTo() throws AbstractRequestException {
        long id = getId();
        String label = getLabel(id);
        CompositeVsnTo compositeVsnTo = new CompositeVsnTo(id, label);
        for(FieldTo fieldTo : getFieldIdList()) {
            compositeVsnTo.bindToField(fieldTo);
        }
        return compositeVsnTo;
    }

    /**
     * Generate a long random unique Id for Composite sensor
     * @see UUID
     * @see Long
     * @return long value that represents a unique Composite sensor id
     */
    private long getId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    /**
     * Generates a unique Composite sensor label based on its long id
     * pattern: "compositeId-" + id
     * @param id
     * @return String with the composite label
     */
    private String getLabel(long id) {
        return "compositeId-" + id;
    }

    /**
     * Get a random list of FieldTos
     * @see FieldTo
     * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
     * @return List of FieldTo
     * @throws AbstractRequestException
     */
    private List<FieldTo> getFieldIdList() throws AbstractRequestException {
        List<FieldTo> fieldToList = new ArrayList<>();
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        if(linkVsnToList.isEmpty()) {
            throw new InternalServerErrorException("Failed to mock Composite sensor: could not found any valid Link sensor for field binding.");
        }
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            for(FieldTo fieldTo : linkVsnTo.getFields()) {
                fieldToList.add(fieldTo);
            }
        }
        return fieldToList;
    }
}
