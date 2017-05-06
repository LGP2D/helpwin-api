package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.RoleRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.Role;
import org.feup.lgp2d.helpwin.models.Voucher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("voucher")
public class VoucherController {

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVouchers() {
        VoucherRepository voucherRepository = new VoucherRepository();
        List<Voucher> vouchers = voucherRepository.getAll();
        if (!vouchers.isEmpty()) {
            return Response.ok().entity(vouchers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }
}
