package com.sdu.edu.bigdata.csdn.controller;

import com.sdu.edu.bigdata.csdn.dao.MarkDAO;
import com.sdu.edu.bigdata.csdn.domain.Mark;
import com.sdu.edu.bigdata.csdn.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * HomeController
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Controller
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private LabelService labelService;
    @Autowired
    private MarkDAO markDAO;

    @RequestMapping("/bz")
    public String home(@RequestParam("name") String name, Map<String, Object> model,
                       @RequestParam(value = "sn", required = false) Integer sn) {
        if (sn != null) {
            Mark mark = markDAO.findBySnAndUser(sn, name);
            if (mark != null) {
                model.put("mark", mark);
            } else {
                model.put("item", labelService.findOne(name));
            }
        } else {
            model.put("item", labelService.findOne(name));
        }
        model.put("name", name);
        return "home";
    }

    @RequestMapping("/")
    public String listName(Map model) {
        model.put("nameList", labelService.listName());
        model.put("workMap", labelService.findWorkInfos());
        return "list";
    }

    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public String mark(@RequestParam("username") String name,
                       @RequestParam("sn") Integer sn,
                       @RequestParam("ztc1") String ztc1,
                       @RequestParam("ztc2") String ztc2,
                       @RequestParam("ztc3") String ztc3) throws UnsupportedEncodingException {
        LOG.info("username={},sn={},主题词1={},主题词2={},主题词3={}", name, sn, ztc1, ztc2, ztc3);
        if (!StringUtils.isEmpty(name)) {
            if (markDAO.findBySnAndUser(sn, name) == null) {
                Mark mark = new Mark(name, sn, ztc1, ztc2, ztc3);
                markDAO.save(mark);
            } else {
                markDAO.updateMark(sn, name, ztc1, ztc2, ztc3);
            }
        }
        return "redirect:/bz?name=" + new String(name.getBytes("utf-8"), "ISO8859-1");
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public void importMarks(@RequestParam(value = "upfile", required = true) MultipartFile file) throws IOException {
        labelService.importFile(file);
    }


    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public void countItems(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String filename = "统计结果";
        response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1") + ".xls");
        labelService.countItem(response.getOutputStream());
    }

}
