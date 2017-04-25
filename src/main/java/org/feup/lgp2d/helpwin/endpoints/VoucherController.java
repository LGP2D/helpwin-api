package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("voucher")
public class VoucherController {

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVouchers(){
        VoucherRepository voucherRepository = new VoucherRepository();
        List<Voucher> vouchers = voucherRepository.getAll();
        return Response.ok(vouchers).build();
    }


    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVoucher(Voucher voucher) {
        VoucherRepository voucherRepository = new VoucherRepository();
        Voucher voucherToRetrieve = voucherRepository.create(voucher);
        return Response.ok(voucherToRetrieve).build();
    }

    @DELETE
    @PermitAll
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVoucherById(@PathParam("id")int id) {
        VoucherRepository voucherRepository = new VoucherRepository();
        voucherRepository.delete(id);
        return Response.ok().build();
    }
}
