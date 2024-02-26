package com.kbtg.bootcamp.posttest.userticket;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    List<UserTicket> findByUserIdAndTicketIdAndStatusId(String userId, String ticketId, String statusActive);

    @Modifying
    @Query(value = "select ut.ut_ticket_id as ticketId,l.lt_price as price from user_ticket ut left join lottery l on l.lt_ticket_id =ut.ut_ticket_id  where ut.ut_user_id =:userId and ut.ut_status_id = :statusActive", nativeQuery = true)
    List<UserTicketTransaction> getUserTicketTransactionByUserIdAndStatusId(String userId, String statusActive);

    @Transactional
    @Modifying
    @Query("UPDATE UserTicket ut SET ut.statusId = :statusInActive WHERE ut.userId = :userId AND ut.ticketId = :ticketId AND ut.statusId = :statusActive")
    int updateStatusId(@Param("userId") String userId, @Param("ticketId") String ticketId, @Param("statusActive") String statusActive, @Param("statusInActive") String statusInActive);
}