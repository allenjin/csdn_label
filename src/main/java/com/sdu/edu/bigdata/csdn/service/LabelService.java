package com.sdu.edu.bigdata.csdn.service;

import com.sdu.edu.bigdata.csdn.bean.WorkInfo;
import com.sdu.edu.bigdata.csdn.dao.GroupDAO;
import com.sdu.edu.bigdata.csdn.dao.ItemDAO;
import com.sdu.edu.bigdata.csdn.dao.MarkDAO;
import com.sdu.edu.bigdata.csdn.domain.Group;
import com.sdu.edu.bigdata.csdn.domain.Item;
import com.sdu.edu.bigdata.csdn.domain.Mark;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * LabelService
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Service
public class LabelService {
    private static final Logger LOG = LoggerFactory.getLogger(LabelService.class);

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private MarkDAO markDAO;

    @Autowired
    private GroupDAO groupDAO;

    public Item findOne(String name) {
        List<Group> groupList = groupDAO.findByName(name);
        if (groupList != null) {
            for (Group group : groupList) {
                int start = group.getStart();
                int end = group.getEnd();
                for (Integer i = start; i <= end; i++) {
                    if (markDAO.findBySnAndUser(i, name) == null) {
                        return itemDAO.findOne(i);
                    }
                }
            }
        }
        return null;
    }

    public Set<String> listName() {
        List<Group> list = groupDAO.findAll();
        Set<String> names = new HashSet<>();
        for (Group group : list) {
            names.add(group.getName());
        }
        return names;
    }

    public Map<String, WorkInfo> findWorkInfos() {
        Map<String, WorkInfo> result = new HashMap<>();
        Set<String> users = listName();
        for (String user : users) {
            List<Group> groupList = groupDAO.findByName(user);
            int total = 0;
            for (Group group : groupList) {
                total += group.getEnd() - group.getStart() + 1;
            }
            WorkInfo workInfo = new WorkInfo();
            workInfo.setName(user);
            workInfo.setTotal(total);
            List<Mark> escapeItemList = markDAO.queryEscapeItem(user);
            List<Mark> finishedItemList = markDAO.queryFinishItem(user);
            int escape = (escapeItemList != null) ? escapeItemList.size() : 0;
            int finish = (finishedItemList != null) ? finishedItemList.size() : 0;
            workInfo.setEscape(escape);
            workInfo.setFinished(finish);
            result.put(user, workInfo);
        }
        return result;
    }

    public void importFile(MultipartFile file) throws IOException {
        boolean isSuffixCorrect = (file != null) && (file.getOriginalFilename().endsWith(".xls"));
        if (isSuffixCorrect) {
            HSSFWorkbook workbook = null;
            workbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String idStr = cellToString(row.getCell(0));
                if (!StringUtils.isEmpty(idStr)) {
                    Integer sn = Integer.valueOf(idStr);
                    String key1 = cellToString(row.getCell(1));
                    String key2 = cellToString(row.getCell(2));
                    String key3 = cellToString(row.getCell(3));
                    String user = cellToString(row.getCell(4));
//                    LOG.info("user = {}", user);
                    if (!StringUtils.isEmpty(key1)
                            && !StringUtils.isEmpty(key2)
                            && !StringUtils.isEmpty(key3)) {
                        Mark mark = markDAO.findBySnAndUser(sn, user);
                        if (mark != null) {
                            LOG.info("sql before mark = {}", mark);
                            if (StringUtils.isEmpty(mark.getKey1())) {
                                mark.setKey1(key1);
                                mark.setKey2(key2);
                                mark.setKey3(key3);
                            }
                        } else {
                            mark = new Mark(user, sn, key1, key2, key3);
                        }
                        Mark sm = markDAO.save(mark);
                        LOG.info("sql after mark = {}", sm);
                    }
                }
            }
        }

    }

    private String cellToString(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("#");
                return df.format(cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
        }
        return null;
    }

    public void countItem(OutputStream outputStream) throws IOException {
        List<Item> itemList = itemDAO.findAll();
        Integer total = 0;
        Map<Integer, Map<String, Integer>> countResult = new HashMap<>();
        Map<Integer, Item> itemMap = new HashMap<>();
        for (Item item : itemList) {
            Integer sn = item.getId();
            if (sn > 3079 || sn < 1801) {
                continue;
            }
            itemMap.put(sn, item);
            List<Mark> marks = markDAO.findBySn(sn);
            Map<String, Integer> itemCount = new HashMap<>();
            if (marks != null) {
                for (Mark mark : marks) {
                    if (!StringUtils.isEmpty(mark.getKey1())) {
                        if (itemCount.get(mark.getKey1()) != null) {
                            itemCount.put(mark.getKey1(), itemCount.get(mark.getKey1()) + 1);
                        } else {
                            itemCount.put(mark.getKey1(), 1);
                        }
                        total++;
                    }
                    if (!StringUtils.isEmpty(mark.getKey2())) {
                        if (itemCount.get(mark.getKey2()) != null) {
                            itemCount.put(mark.getKey2(), itemCount.get(mark.getKey2()) + 1);
                        } else {
                            itemCount.put(mark.getKey2(), 1);
                        }
                        total++;
                    }
                    if (!StringUtils.isEmpty(mark.getKey3())) {
                        if (itemCount.get(mark.getKey3()) != null) {
                            itemCount.put(mark.getKey3(), itemCount.get(mark.getKey3()) + 1);
                        } else {
                            itemCount.put(mark.getKey3(), 1);
                        }
                        total++;
                    }
                }
                countResult.put(sn, itemCount);
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("统计结果");

        int rowNum = 0;
        for (Map.Entry<Integer, Map<String, Integer>> entry : countResult.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            Item item = itemMap.get(entry.getKey());
            Cell snCell = row.createCell(cellNum++);
            snCell.setCellValue(item.getId());
            Cell addressCell = row.createCell(cellNum++);
            addressCell.setCellValue(item.getAddress());
            Map<String, Integer> countMap = entry.getValue();
            List<Map.Entry<String, Integer>> list = new ArrayList<>(countMap.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });
            for (Map.Entry<String, Integer> itemEntry : list) {
                Cell keyCell = row.createCell(cellNum++);
                keyCell.setCellValue(itemEntry.getKey());
                Cell valCell = row.createCell(cellNum++);
                valCell.setCellValue(itemEntry.getValue());
            }
        }
        workbook.write(outputStream);
    }


}
