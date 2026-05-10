package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.Approval;
import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends Repository<Approval, String> {
    List<Approval> findByBookingId(String bookingId);
    List<Approval> findByAdminId(String adminId);
    Optional<Approval> findByApprovalId(String approvalId);
}
