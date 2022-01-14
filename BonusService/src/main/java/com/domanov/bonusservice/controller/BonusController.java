package com.domanov.bonusservice.controller;

import com.domanov.bonusservice.dto.BonusAccountDTO;
import com.domanov.bonusservice.dto.BonusReturnRequestDTO;
import com.domanov.bonusservice.dto.UpdateBonusesDTO;
import com.domanov.bonusservice.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://lab2-bonus-service.herokuapp.com/api/v1/privilege")
@RequestMapping("api/v1/privilege")
public class BonusController {

    @Autowired
    BonusService bonusService;

    @GetMapping()
    @CrossOrigin(origins = "*")
    public BonusAccountDTO getBonusAccountPrivileges(@RequestHeader("X-User-Name") String username) {
        return bonusService.getBonusAccountPrivileges(username);
    }

    @PutMapping()
    @CrossOrigin(origins = "*")
    public BonusAccountDTO updateBonuses(@RequestHeader("X-User-Name") String username, @RequestBody UpdateBonusesDTO updateBonusesDTO) {
        return bonusService.updateBonuses(username, updateBonusesDTO);
    }

    @PutMapping("/return")
    @CrossOrigin(origins = "*")
    public BonusAccountDTO returnBonuses(@RequestHeader("X-User-Name") String username, @RequestBody BonusReturnRequestDTO bonusReturnRequestDTO) {
        return bonusService.bonusReturn(username, bonusReturnRequestDTO);
    }
}
