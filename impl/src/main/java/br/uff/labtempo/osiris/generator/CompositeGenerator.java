package br.uff.labtempo.osiris.generator;

import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;

public class CompositeGenerator {

    public CompositeVsnTo generateCompositeVsnTo() {
        CompositeVsnTo compositeVsnTo = new CompositeVsnTo(getId(), getLabel());
        compositeVsnTo.bindToField(getFieldId());
        return compositeVsnTo;
    }

    private long getId() {
        return -1;
    }

    private String getLabel() {
        return null;
    }

    private long getFieldId() {
        return -1;
    }
}
