package com.ADP.controller;

import com.ADP.service.Athena.edta.FilterCurrentTransactionDO;
import com.ADP.service.Athena.edta.TransactionDTO;
import com.ADP.service.MMLogsService;
import com.ADP.service.Athena.edta.EdtaAthenaService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/edta")
@CrossOrigin(origins = "*")
@Api(value = "edta")
public class EdtaController {
    private final Log logger = LogFactory.getLog(this.getClass());

    private EdtaAthenaService edtaAthenaService;
    @Autowired
    MMLogsService mmlogService;
    
    public EdtaController(EdtaAthenaService edtaAthenaService) {
        this.edtaAthenaService = edtaAthenaService;
    }

    @GetMapping(value = "/activity")
    @ResponseBody
    public Object getActivityCode(Pageable pageable) {
        long timeKey = DateFunction.TimeKey();
        try {
            return edtaAthenaService.getActivityCode();
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "get Activity code", "", "", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/billing")
    @ResponseBody
    public Object getBilling(Pageable pageable) {
        long timeKey = DateFunction.TimeKey();
        try {
            return edtaAthenaService.getBillingType();
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "get Billing", "", "", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/transaction/filter", produces = "application/json")
    @ResponseBody
    public Object getTransaction(@RequestBody FilterCurrentTransactionDO filterCurrentTransactionDO) {
        long timeKey = DateFunction.TimeKey();
        try {
            return edtaAthenaService.getTransaction(filterCurrentTransactionDO);
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "get transaction ", "", "", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/transaction/history", produces = "application/json")
    @ResponseBody
    public Object getTransactionHistory(@RequestBody TransactionDTO obj) {
        long timeKey = DateFunction.TimeKey();
        try {
            Long tid = obj.getId();
            return edtaAthenaService.getTransactionHistory(tid);
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "get transaction history ", "", "", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/transaction", produces = "application/json")
    @ResponseBody
    public Object addTransaction(@RequestBody TransactionDTO transactionDTO) {
        long timeKey = DateFunction.TimeKey();
        try {
        	transactionDTO.setCreatedBy(transactionDTO.getEmployeeId());
        	return edtaAthenaService.addTransaction(transactionDTO);        	
        } catch (Exception ex) {
        	mmlogService.addContainer(transactionDTO, "transaction");
        	mmlogService.TimerResend("transaction");
        	logger.info(Utilities.LogError(timeKey, "get transaction update ", "", "", ex.toString()));            
            throw new IllegalArgumentException(ex.toString());
        }
    }
}
