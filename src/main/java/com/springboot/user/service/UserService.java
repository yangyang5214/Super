package com.springboot.user.service;

import com.google.common.collect.Lists;
import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.EmailUtil;
import com.springboot.common.util.ExcelUtil;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.common.util.StringUtil;
import com.springboot.find.dao.MovingDao;
import com.springboot.user.dao.UserDao;
import com.springboot.user.entity.User;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import com.springboot.user.ws.dto.UserFindData;
import com.springboot.user.ws.dto.UserPoiDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.word.WordExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by zl on 2017/3/9.
 */
@Service
@Transactional
public class UserService {

    @Value("${ip:}")
    private String ip;

    @Value("${excel.save.position:}")
    private String excelPosition;

    @Value("${avatar.url:}")
    private String avatarUrl;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MovingDao movingDao;


    @Autowired
    public EmailUtil emailUtil;

    @Autowired
    public FastDFSUtil fastDFSUtil;




   public ResponseDto register(String  username,String password){
       ResponseDto responseDto = new ResponseDto();
       User user = baseDao.find(User.class,"USERNAME",username);
       if (nonNull(user)){
           responseDto.setSuccess(Boolean.FALSE);
           responseDto.setMessage("用户已存在！");
           return responseDto;
       }
       user = new User();
       user.setUsername(username);
       user.setPassword(password);
       user.setAvatarUrl(avatarUrl);
       String nickName= RandomStringUtils.randomAlphanumeric(6);
       user.setNickName(nickName);
       baseDao.persist(user);
       UserDto userDto = new UserDto();
       userDto.setNickName(nickName);
       userDto.setAvatarUrl(avatarUrl);
       responseDto.setObj(userDto);
       return responseDto;
   }



    public ResponseDto login(String username,String password){
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",username);
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户不存在！");
            return responseDto;
        }
        if (!user.getPassword().equals(password)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户名或密码错误！");
            return responseDto;
        }
        responseDto.setObj(formatUser(user));
        return responseDto;
    }

    private UserDto formatUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAvatarUrl(ip + user.getAvatarUrl());
        userDto.setNickName(user.getNickName());
        return userDto;
    }
    public ResponseDto recordLogin(String username, String password) {
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",username);
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("学号输入有误！");
            return responseDto;
        }
        if (!user.getPassword().equals(password)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("学号或密码错误！");
            return responseDto;
        }
        responseDto.setObj(formatUser(user));
        return responseDto;
    }

    public ResponseDto updataUserInfo(MultipartFile avatarFile,UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        if (StringUtil.isEmpty(userDto.getId().toString())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户ID不存在！");
            return responseDto;
        }
        User user = baseDao.findById(User.class,userDto.getId());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("不存在此用户！");
            return responseDto;
        }
        if (null != avatarFile) {
            try {
                user.setAvatarUrl(fastDFSUtil.saveImage(avatarFile.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setUniversityName(userDto.getUniversityName());
        user.setNickName(userDto.getNickName());
        user.setSex(userDto.getSex());
        user.setArea(userDto.getArea());
        user.setSignature(userDto.getSignature());
        baseDao.persist(user);
        return  new ResponseDto();
    }

    public  ResponseDto uploadUserMessage(InputStream fileInputStream){
        List<UserPoiDto> userPoiDtoList = Lists.newArrayList();
        ResponseDto responseDto = new ResponseDto();
        Workbook workbook = ExcelUtil.getWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        ExcelUtil.ExcelHeadHelper excelHeadHelper = new ExcelUtil.ExcelHeadHelper();
        String errorExcelHead = excelHeadHelper.build(UserPoiDto.class).index(sheet.getRow(0));
        if (nonNull(errorExcelHead)) {
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage(errorExcelHead);
            return responseDto;
        }
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            if (nonNull(row)) {
                UserPoiDto userPoiDto = new UserPoiDto();
                excelHeadHelper.fill(row, userPoiDto);
                if (StringUtil.isNotEmpty(userPoiDto.getUsername()) && StringUtil.isNotEmpty(userPoiDto.getUsername())){
                    userPoiDtoList.add(userPoiDto);
                }
            }
        }
        userPoiDtoList.stream().forEach(p->register(p.getUsername(),p.getPassword()));
        return new ResponseDto();
    }


    public InputStream exportUserMessage(){
        TemplateExportParams params = new TemplateExportParams("export/用户信息导出格式.xls",true);
        params.setColForEach(true);
        Map<String, Object> maps = new HashMap<>();
        maps.put("user","APP");
        List<Object[]> listUserInfo = userDao.queryUserMessageForExport();
        List<Map<String,Object>> mapsUserInfo = Lists.newArrayList();
        for (Object[] object : listUserInfo) {
            Map<String, Object> map = new HashMap<>();
            map.put("username",object[0].toString());
            map.put("password",object[1].toString());
            mapsUserInfo.add(map);
        }
        maps.put("mapList",mapsUserInfo);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Workbook workbook = ExcelExportUtil.exportExcel(params, maps);
        try {
            workbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(output.toByteArray());
    }

    public void exportToMailbox(String email){
        InputStream fileInputStream = exportUserMessage();
        String excelPath = savefile(fileInputStream,"xls","");
        List<String> listFilePath = Lists.newArrayList();
        listFilePath.add(excelPath);
        String subject = "大学生超级成长档案";
        String msg  = "hello";
        emailUtil.sendEmail(email,subject,msg,listFilePath);
    }

    public ResponseDto registerForCode(String code,String email){
        String registerMsg = "验证码为" + code;
        String registerSubject = "八一农大App验证码";
        emailUtil.sendEmail(email,registerSubject,registerMsg,null);
        return  new ResponseDto();
    }

    public ResponseDto registerByEmail(String code,String email){
        String registerMsg = "验证码为" + code;
        String registerSubject = "八一农大App验证码";
        emailUtil.sendEmail(email,registerSubject,registerMsg,null);
        return  new ResponseDto();
    }

    public String savefile(InputStream fileInputStream,String fileType,String name) {
        String imgId = UUID.randomUUID().toString();
        String fileName = name + "—" + imgId.substring(0,10) + "." + fileType;
        String path = excelPosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, excelPosition);
        return path;
    }

    public void exportResume(){
        Map<String, Object> map = buildData();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            XWPFDocument document = WordExportUtil.exportWord07("export/resume/简历-计算机-001.docx", map);
            document.write(output);
        } catch (Exception e) {
            e.getMessage();
        }
        InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        String filePath =  savefile(inputStream,"docx","简历");
        List<String> listFilePath = Lists.newArrayList();
        listFilePath.add(filePath);
        String subject = "简历";
        String msg  = "hello";
        emailUtil.sendEmail("",subject,msg,listFilePath);
    }

    private Map<String, Object> buildData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","zhangzhiyuan");
        map.put("company","qwer");
        map.put("school","hlj");
        map.put("sex","men");
        return map;
    }


    public ResponseDto getUserFindData(long userId) {
        ResponseDto responseDto = new ResponseDto();
        UserFindData userFindData = new UserFindData();
        userFindData.setBeayty(movingDao.getBeaytyUserData(userId));
        userFindData.setMoving(movingDao.getMovingUserData(userId));
        userFindData.setMarket(movingDao.getMarketUserData(userId));
        responseDto.setObj(userFindData);
        return responseDto;
    }
}
