package com.example.application.data.endpoint;

import com.example.application.data.entity.Person;
import com.example.application.data.service.PersonService;
import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.vaadin.artur.helpers.GridSorter;
import org.vaadin.artur.helpers.PagingUtil;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
// Exclude passwordHas property from all generated DTOs, regardless of class.
// This could also be written as "Person.passwordHash" to specifically apply this only to
// Person class. Furthermore, we allow listing multiple properties as "passwordHash,occupation".
// EndpointExclude annotation applied to Endpoint class applies to all calls in that class and
// generated TS class is still named as Person. If EndpointExclude would be applied to a method
// inside Endpoint class, a new TS class would be generated with the same logic as in getNames
// example below.
// TODO: @EndpointExclude("passwordHash")
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

    public Person update(Person entity) {
        return getService().update(entity);
    }

    public void delete(Integer id) {
        getService().delete(id);
    }

    public int count() {
        return getService().count();
    }

    // This is the right way of setting password, instead of passing that to passwordHash directly
    public void setPassword(String password) {
        // ..
    }

    // By default Entity references (@OneToOne, @OneToMany, @ManyToMany, ...) are included as
    // only sending over Ids. In this example, we generate friendIds property with Set of friend
    // ids by default instead of Set<Person>.
    public Optional<Person> get(Integer id) {
        return getService().get(id);
    }

    // Generate DTO that only includes the listed properties. It will be named after the first get method
    // that lists those properties. In this case, the corresponding TS class will be named as
    // PersonNames (concatenating the base class name with getter inferred name)
    // TODO: @EndpointInclude("firstName,lastName")
    public Optional<Person> getNames(Integer id) {

        // Note that we can return the full Person object here, filtering in done by Fusion
        return getService().get(id);
    }

    // A DTO can be compactly defined as static inner class. TS class name is generated without
    // "Endpoint." part. Thus this would become PersonContactInfo.
    @Data public static class ContactInfo {

        // Instead of including person property in the TS generated class, we include
        // email, phone and address directly in the TS generated class. They also retain
        // any validation annotations they had in Person class

        // TODO: @EndpointIncludeFlatten("email,phone,address")
        private final Person person;

        // Class can also include any other properties that will then be generated in
        // TS class as well
        boolean hasBeenUpdated;

        // As well as computed properties
        boolean isReachable() {
            return person.getEmail() != null || person.getPhone() != null || person.getAddress() != null;
        }
    }

    public Optional<ContactInfo> getContactInfo(Integer id) {
        return new Optional<ContactInfo>(getService().get(id).orElse(null));
    }

}
