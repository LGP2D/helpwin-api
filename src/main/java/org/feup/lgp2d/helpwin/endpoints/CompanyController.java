package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.CompanyRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.Company;
import org.feup.lgp2d.helpwin.models.Voucher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("company")
public class CompanyController {

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCompanies(){
        CompanyRepository companyRepository = new CompanyRepository();
        List<Company> companies = companyRepository.getAll();
        return Response.ok(companies).build();
    }


    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompany(Company company) {
        CompanyRepository companyRepository = new CompanyRepository();
        Company companyToRetrieve = companyRepository.create(company);
        return Response.ok(companyToRetrieve).build();
    }

    @DELETE
    @PermitAll
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompanyById(@PathParam("id")int id) {
        CompanyRepository companyRepository = new CompanyRepository();
        companyRepository.delete(id);
        return Response.ok().build();
    }
}
