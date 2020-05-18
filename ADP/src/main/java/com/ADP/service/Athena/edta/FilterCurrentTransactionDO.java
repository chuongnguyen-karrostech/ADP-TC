package com.ADP.service.Athena.edta;

import java.util.ArrayList;
import java.util.List;

public class FilterCurrentTransactionDO {
    public String employeeid;    
    public String fromdate;
    public String todate;
    public List<String> listStatus = new ArrayList<>();
}
