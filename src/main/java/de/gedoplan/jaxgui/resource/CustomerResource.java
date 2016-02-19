package de.gedoplan.jaxgui.resource;

import com.fasterxml.jackson.annotation.JsonView;
import de.gedoplan.jaxgui.model.Customer;
import de.gedoplan.jaxgui.model.dto.CustomerOrderInformation;
import de.gedoplan.jaxgui.model.dto.QueryResult;
import de.gedoplan.jaxgui.model.dto.QuerySettings;
import de.gedoplan.jaxgui.resource.views.DetailView;
import de.gedoplan.jaxgui.resource.views.ListView;
import de.gedoplan.jaxgui.service.CustomerService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("customer")
@Consumes("application/json")
@Produces("application/json")
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    @GET
    @JsonView(ListView.class)
    public QueryResult getCustomer(
            @QueryParam("max") Integer max,
            @QueryParam("start") Integer start,
            @QueryParam("sortAttributes") String sortAttributes[],
            @QueryParam("sortDirections") String sortDirections[]) {

        QuerySettings settings = new QuerySettings(start, max, sortAttributes, sortDirections);
        return customerService.queryCustomer(settings);
    }

    @GET
    @Path("detail/{id}")
    @JsonView(DetailView.class)
    public Customer getCustomerById(@PathParam("id") String customerID) {
        return customerService.getCustomerById(customerID);
    }

    @GET
    @Path("discount/{id}")
    public CustomerOrderInformation getCustomerOrderInformation(@PathParam("id") String customerId) {
        return customerService.calculateCustomerDiscount(customerId);
    }

    @POST
    @JsonView(DetailView.class)
    public Customer updateCustomer(Customer customer) {
        return customerService.updateCustomer(customer);
    }

}
