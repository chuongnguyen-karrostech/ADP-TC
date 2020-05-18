package com.ADP.controller;

import com.ADP.service.MMLogsService;
import com.ADP.service.Athena.ivin.ImageService;
import com.ADP.service.Athena.ivin.InspectionService;
import com.ADP.service.Athena.ivin.TemplateService;
import com.ADP.service.Athena.ivin.ZoneService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/ivin")
@CrossOrigin(origins = "*")
@Api(value = "ivin")

public class IvinController {

    private final Log logger = LogFactory.getLog(this.getClass());
    ObjectMapper mapper = new ObjectMapper();
    ImageService imageservice;
    ZoneService zoneService;
    TemplateService templateService;
    InspectionService inspectionService;
    @Autowired
    MMLogsService mmlogService;

    public IvinController(ImageService imageservice, ZoneService zoneService, TemplateService templateService, InspectionService inspectionService) {
        this.imageservice = imageservice;
        this.zoneService = zoneService;
        this.templateService = templateService;
        this.inspectionService = inspectionService;
    }

    @GetMapping(value = "/zone")
    @ApiOperation(value = "Get zone list", response = Iterable.class)
    @ResponseBody
    public Object getZone() {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = zoneService.getZone();
            logger.info(Utilities.LogReturn(timeKey, "Zone", "zone", "Get zone list"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "Zone", "zone", "Get Zone list", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/type")
    @ApiOperation(value = "Get type list", response = Iterable.class)
    @ResponseBody
    public Object getType() {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = zoneService.getType();
            logger.info(Utilities.LogReturn(timeKey, "Type", "type", "Get type list"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "Type", "type", "Get type list", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/template/{id}")
    @ApiOperation(value = "Get template by id", response = Iterable.class)
    @ResponseBody
    public Object getTemplateById(@PathVariable("id") Integer id) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = templateService.getTemplateById(id);
            logger.info(Utilities.LogReturn(timeKey, "template", "template", "Get template by id"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "template", "template", "Get template by id", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/template/{typeid}/{status}")
    @ApiOperation(value = "Get template by id", response = Iterable.class)
    @ResponseBody
    public Object getTemplateActiveByStatus(@PathVariable("typeid") Integer typeid,
                                            @PathVariable("status") String status) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = templateService.getTemplateActiveByStatus(typeid, status);
            logger.info(Utilities.LogReturn(timeKey, "template", "template", "Get template by id"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "template", "template", "Get template by id", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }
    
    @GetMapping(value = "/inspection/{id}")
    @ApiOperation(value = "Get inspection by id", response = Iterable.class)
    @ResponseBody
    public Object getInspectionById(@PathVariable("id") Integer id) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = inspectionService.getInspectionById(id);
            logger.info(Utilities.LogReturn(timeKey, "inspection", "inspection", "Get inspection by id"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Get inspection by id", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/inspection/last")
    @ApiOperation(value = "Get last inspection by filter", response = Iterable.class)
    @ResponseBody
    public Object getLastInspection(@RequestBody Object filter) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = inspectionService.getLastInspection(filter);
            logger.info(Utilities.LogReturn(timeKey, "inspection", "inspection", "Get last inspection by filter"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Get last inspection by filter",
                    ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/inspection/driver")
    @ApiOperation(value = "Get driver list", response = Iterable.class)
    @ResponseBody
    public Object getDriverName() {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = inspectionService.getDriverName();
            logger.info(Utilities.LogReturn(timeKey, "inspection", "inspection", "Get driver list"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Get driver list", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/inspection/filter")
    @ApiOperation(value = "Filter oversight", response = Iterable.class)
    @ResponseBody
    public Object overSightInspection(@RequestBody Object filter) {
        long timeKey = DateFunction.TimeKey();
        try {
            Object obj = inspectionService.oversightInspection(filter);
            logger.info(Utilities.LogReturn(timeKey, "inspection", "inspection", "Filter oversight"));
            return obj;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Filter oversight", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/inspection")
    @ApiOperation(value = "Save inspection", response = Iterable.class)
    @ResponseBody
    public Object saveInspection(@RequestBody Object inspection) {
        long timeKey = DateFunction.TimeKey();
        try {
            try {
                Object obj = inspectionService.saveInspection(inspection);
                logger.info(Utilities.LogReturn(timeKey, "inspection", "inspection", mapper.writeValueAsString(obj)));
                return obj;
            } catch (Exception e) {
                logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Save inspection ",
                        mapper.writeValueAsString(inspection)));
                mmlogService.addContainer(inspection, "ivin");
                mmlogService.TimerResend("ivin");
                throw e;
            }
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "inspection", "inspection", "Save inspection ", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/inspection/report/{id}", produces = "application/text")
    @ApiOperation(value = "Get inspection report by id", response = Iterable.class)
    @ResponseBody
    public Object getReportByInspectionId(@PathVariable("id") Integer id) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = inspectionService.getReportByInspectionId(id);
            logger.info(Utilities.LogReturn(timeKey, "report", "report", "Get inspection report by id"));
            return objArr;
        } catch (Exception ex) {
            logger.info(
                    Utilities.LogError(timeKey, "report", "report", "Get inspection report by id", ex.getMessage()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @PostMapping(value = "/image")
    @ApiOperation(value = "Save image", response = Iterable.class)
    @ResponseBody
    public Object saveImage(@RequestParam("uploadfile") MultipartFile file) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = imageservice.saveImage(file);
            logger.info(Utilities.LogReturn(timeKey, "image", "image", "Save image "));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "image", "image", "Save image ", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @GetMapping(value = "/image/{id}")
    @ApiOperation(value = "Get image by id", response = Iterable.class)
    @ResponseBody
    public Object getImage(@PathVariable("id") Integer id) {
        long timeKey = DateFunction.TimeKey();

        try {
            Object objArr = imageservice.getImage(id);
            logger.info(Utilities.LogReturn(timeKey, "image", "image", "Get image by id"));
            return objArr;
        } catch (Exception ex) {
            logger.info(Utilities.LogError(timeKey, "image", "image", "Get image by id", ex.toString()));
            throw new IllegalArgumentException(ex.toString());
        }
    }

}
