package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.CompanyRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.Company;
import org.feup.lgp2d.helpwin.models.Voucher;
import org.jboss.logging.annotations.Param;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

@Path("company")
public class CompanyController {

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanies() {
        CompanyRepository companyRepository = new CompanyRepository();
        List<Company> companies = companyRepository.getAll();
        if (!companies.isEmpty()) {
            return Response.ok().entity(companies).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No companies were found!").build();
        }
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompany(Company company) {
        CompanyRepository companyRepository = new CompanyRepository();
        company.generateUniqueId();
        Company companyToRetrieve = companyRepository.create(company);
        if (companyToRetrieve != null) {
            return Response.ok().entity(companyToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    @PermitAll
    @PUT
    @Path("/insertVoucher")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertVoucher(Company company) {
        try{
            CompanyRepository companyRepository = new CompanyRepository();

            Company companyToRetrieve = companyRepository.getUserByUniqueID(company.getUniqueId());

            if (companyToRetrieve == null) {
                return Response.serverError().entity("Company not found.").build();
            }

            Iterator iter = company.getVouchers().iterator();

            Voucher voucher = (Voucher) iter.next();

            if(!companyToRetrieve.addVoucher(voucher)){
                return Response.serverError().entity("Error storing the voucher's data.").build();
            }

            companyRepository.updateCompany(companyToRetrieve);

            return Response.ok().entity(companyToRetrieve).build();

        } catch (Exception e) {
            return Response.serverError().entity("Internal error.").build();
        }
    }
}
