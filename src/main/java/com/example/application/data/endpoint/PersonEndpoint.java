package com.example.application.data.endpoint;

import com.example.application.data.entity.Person;
import com.example.application.data.service.PersonService;
import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.vaadin.artur.helpers.GridSorter;
import org.vaadin.artur.helpers.PagingUtil;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class PersonEndpoint {

    private PersonService service;

    public PersonEndpoint(@Autowired PersonService service) {
        this.service = service;
    }

    protected PersonService getService() {
        return service;
    }

    public List<Person> list(int offset, int limit, List<GridSorter> sortOrder) {
        Page<Person> page = getService()
                .list(PagingUtil.offsetLimitTypeScriptSortOrdersToPageable(offset, limit, sortOrder));
        return page.getContent();
    }

    public Optional<Person> get(Integer id) {
        return getService().get(id);
    }

    public Person update(Person entity) {
        return getService().update(entity);
    }

    public void delete(Integer id) {
        getService().delete(id);
    }

    public int count() {
        return getService().count();
    }

}
