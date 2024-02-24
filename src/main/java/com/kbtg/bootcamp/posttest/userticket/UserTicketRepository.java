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
    @Transactional
    @Modifying
    @Query("UPDATE UserTicket ut SET ut.statusId = :statusInActive WHERE ut.userId = :userId AND ut.ticketId = :ticketId AND ut.statusId = :statusActive")
    int updateStatusId(@Param("userId") String userId, @Param("ticketId") String ticketId, @Param("statusActive") String statusActive, @Param("statusInActive") String statusInActive);}