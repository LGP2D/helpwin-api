package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.RoleRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.Role;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.Voucher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VoucherRepository;
import org.feup.lgp2d.helpwin.models.Voucher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PermitAll
    @GET
    @Path("vouchers/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response getVoucherById(@PathParam("id")int id){
        VoucherRepository voucherRepository= new VoucherRepository();
        Voucher voucher=voucherRepository.getOne(id);
        if (voucher !=null){
            return Response.ok().entity(voucher).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVoucher(Voucher voucher) {
        VoucherRepository voucherRepository = new VoucherRepository();
        UserRepository userRepository = new UserRepository();
        User company = userRepository.getUserByUniqueID(voucher.getCompany().getUniqueId());
        if(company == null){
            return Response.serverError().entity("No user").build();
        }
        voucher.setCompany(company);
        voucher.generateUniqueId();
        Voucher voucherToRetrieve = voucherRepository.create(voucher);
        if(voucherToRetrieve == null){
            return Response.serverError().entity("Error voucher").build();
        }
        if (voucherToRetrieve != null) {
            return Response.ok().entity(voucherToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    @PermitAll
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVoucher(Voucher voucher) {
        VoucherRepository voucherRepository = new VoucherRepository();
        Voucher voucherToRetrieve = voucherRepository.create(voucher);
        if (voucherToRetrieve != null) {
            return Response.ok().entity(voucherToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to update your record on database").build();
        }
    }

    @PermitAll
    @DELETE
    @Path("vouchers/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteVoucherById(@PathParam("id")int id) {
        VoucherRepository voucherRepository = new VoucherRepository();
        voucherRepository.delete(id);
        return Response.ok("Action deleted.").build();
    }

    @PermitAll
    @PUT
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response validateVoucher(@HeaderParam(value = "Authorization") final String token, Voucher voucher){
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build(); }

        VoucherRepository voucherRepository = new VoucherRepository();
        Voucher voucherToValidate = voucherRepository.getOne(p -> p.getUniqueId().contentEquals(voucher.getUniqueId()));
        if (voucherToValidate == null) { return Response.status(Response.Status.NO_CONTENT).entity("Voucher not found").build(); }

        voucherToValidate.setValid(true);

        voucherRepository.update(voucherToValidate);

        return Response.ok("Voucher successfully validated").build();
    }

    @PermitAll
    @PUT
    @Path("/invalidate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response invalidateVoucher(@HeaderParam(value = "Authorization") final String token, Voucher voucher){
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build(); }

        VoucherRepository voucherRepository = new VoucherRepository();
        Voucher voucherToInvalidate = voucherRepository.getOne(p -> p.getUniqueId().contentEquals(voucher.getUniqueId()));
        if (voucherToInvalidate == null) { return Response.status(Response.Status.NO_CONTENT).entity("Voucher not found").build(); }

        voucherToInvalidate.setValid(false);

        voucherRepository.update(voucherToInvalidate);

        return Response.ok("Voucher successfully invalidated").build();
    }

    @PermitAll
    @Path("/companyVouchers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyVouchers(@HeaderParam("Authorization")String token) {
        VoucherRepository voucherRepository = new VoucherRepository();
        String email = TokenHelper.getEmail(token);
        List<Voucher> vouchers = voucherRepository.getAll();
        List<Voucher> finalVouchers = vouchers.stream().filter(p -> (p.getCompany().getEmail().equals(email)  && p.isValid()) ).collect(Collectors.toList());
        if (!finalVouchers.isEmpty()) {
            return Response.ok().entity(finalVouchers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    @PermitAll
    @POST
    @Path("/deleteCompanyVouchers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteCompanyVoucher(Voucher voucher) {
        try{
            VoucherRepository voucherRepository = new VoucherRepository();
            Voucher voucherdb = voucherRepository.getOne(p -> p.getUniqueId().equals(voucher.getUniqueId()));
            voucherdb.setValid(false);
            voucherRepository.update(voucherdb);
            return Response.ok().entity("OK").build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).entity("Error deleting.").build();
        }
    }

}
