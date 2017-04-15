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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompositeGenerator {

    @Autowired
    private LinkRepository linkRepository;

    public CompositeVsnTo generateCompositeVsnTo() throws AbstractRequestException {
        long id = getId();
        String label = getLabel(id);
        CompositeVsnTo compositeVsnTo = new CompositeVsnTo(id, label);
        for(Long fieldId : getFieldIdList()) {
            compositeVsnTo.bindToField(fieldId);
        }
        return compositeVsnTo;
    }

    private long getId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    private String getLabel(long id) {
        return "compositeId-" + id;
    }

    private List<Long> getFieldIdList() throws AbstractRequestException {
        List<Long> fieldIdList = new ArrayList<>();
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        if(linkVsnToList.isEmpty()) {
            throw new InternalServerErrorException("Failed to mock Composite sensor: could not found any valid Link sensor for field binding.");
        }
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            for(FieldTo fieldTo : linkVsnTo.getFields()) {
                fieldIdList.add(fieldTo.getId());
            }
        }
        int min = (int) (Math.random() * linkVsnToList.size());
        int max = min + (int) (Math.random() * (linkVsnToList.size() - min));
        return fieldIdList.subList(min, max);
    }
}
