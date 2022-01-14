package com.domanov.bonusservice.service;

import com.domanov.bonusservice.dto.BonusAccountDTO;
import com.domanov.bonusservice.dto.BonusReturnRequestDTO;
import com.domanov.bonusservice.dto.PrivilegeHistoryDTO;
import com.domanov.bonusservice.dto.UpdateBonusesDTO;
import com.domanov.bonusservice.model.Privilege;
import com.domanov.bonusservice.model.PrivilegeHistory;
import com.domanov.bonusservice.repository.PrivilegeHistoryRepository;
import com.domanov.bonusservice.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BonusService")
public class BonusService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PrivilegeHistoryRepository privilegeHistoryRepository;

    public BonusAccountDTO getBonusAccountPrivileges(String username) {
        Privilege privilege = privilegeRepository.findByUsername(username);
        BonusAccountDTO bonusAccountDTO = new BonusAccountDTO();
        bonusAccountDTO.setBalance(privilege.getBalance());
        bonusAccountDTO.setStatus(privilege.getStatus());

        List<PrivilegeHistory> privilegeHistory = privilegeHistoryRepository.findByPrivilegeId(privilege.getId());

        List<PrivilegeHistoryDTO> privilegeHistoryDTOList = new ArrayList<>();
        for (PrivilegeHistory ph : privilegeHistory) {
            PrivilegeHistoryDTO privilegeHistoryDTO = new PrivilegeHistoryDTO();
            privilegeHistoryDTO.setDate(ph.getDateTime());
            privilegeHistoryDTO.setTicketUid(ph.getTicket_uid());
            privilegeHistoryDTO.setBalanceDiff(ph.getBalanceDiff());
            privilegeHistoryDTO.setOperationType(ph.getOperationType());
            privilegeHistoryDTOList.add(privilegeHistoryDTO);
        }
        bonusAccountDTO.setHistory(privilegeHistoryDTOList);
        return bonusAccountDTO;
    }

    public BonusAccountDTO updateBonuses(String username, UpdateBonusesDTO updateBonusesDTO) {
        Privilege privilege = privilegeRepository.findByUsername(username);
        int balanceDiff = updateBonusesDTO.getPaidByMoney();
        if (updateBonusesDTO.getPaidFromBalance().equals(false)) {
            balanceDiff = (int) (updateBonusesDTO.getPaidByMoney() * 0.1);
            privilege.setBalance(privilege.getBalance() + balanceDiff);
        }
        PrivilegeHistory privilegeHistory = new PrivilegeHistory();
        privilegeHistory.setPrivilege(privilege);
        privilegeHistory.setTicket_uid(updateBonusesDTO.getTicketUid());
        privilegeHistory.setDateTime(updateBonusesDTO.getDate());
        privilegeHistory.setBalanceDiff(balanceDiff);
        privilegeHistory.setOperationType(updateBonusesDTO.getOperationType());
        privilegeHistoryRepository.save(privilegeHistory);
        privilegeRepository.save(privilege);

        return getBonusAccountPrivileges(username);
    }


    public BonusAccountDTO bonusReturn(String username, BonusReturnRequestDTO bonusReturnRequestDTO) {
        Privilege privilege = privilegeRepository.findByUsername(username);
        int currentBalance = privilege.getBalance();
        if (bonusReturnRequestDTO.getOperationType().equals("FILL_IN_BALANCE")) {
            privilege.setBalance(currentBalance - bonusReturnRequestDTO.getBalanceDiff());
        }
//        List<PrivilegeHistory> privilegeHistoryList = privilegeHistoryRepository.findByPrivilegeId(privilege.getId());
//        for (PrivilegeHistory ph : privilegeHistoryList) {
//            if (ph.getTicket_uid().equals(bonusReturnRequestDTO.getTicketUid())) {
//                privilegeHistoryRepository.delete(ph);
//            }
//        }
        privilegeRepository.save(privilege);
        return null;
    }
}
