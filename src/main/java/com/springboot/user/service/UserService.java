package com.springboot.user.service;

import com.google.common.collect.Lists;
import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.EmailUtil;
import com.springboot.common.util.ExcelUtil;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.common.util.StringUtil;
import com.springboot.user.dao.UserDao;
import com.springboot.user.entity.User;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import com.springboot.user.ws.dto.UserPoiDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
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


    @Value("${excel.save.position:}")
    private String excelPosition;

    @Value("${avatar.url:}")
    private String avatarUrl;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    public EmailUtil emailUtil;

   public ResponseDto register(RegisterDto registerDto){
       ResponseDto responseDto = new ResponseDto();
       User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
       if (nonNull(user)){
           responseDto.setSuccess(Boolean.FALSE);
           responseDto.setMessage("用户已存在！");
           return responseDto;
       }
       user = new User();
       user.setUsername(registerDto.getUsername());
       user.setPassword(registerDto.getPassword());
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



    public ResponseDto login(RegisterDto registerDto){
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户未注册！");
            return responseDto;
        }
        if (!user.getPassword().equals(registerDto.getPassword())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户名或密码错误！");
            return responseDto;
        }
        responseDto.setObj(new UserDto(user));
        return responseDto;
    }

    public ResponseDto recordLogin(String username, String password) {
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",username);
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户未注册！");
            return responseDto;
        }
        if (!user.getPassword().equals(password)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户名或密码错误！");
            return responseDto;
        }
        responseDto.setObj(formatUserDto(user));
        return responseDto;
    }


    public UserDto formatUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setNickName(user.getNickName());
        return userDto;
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
                user.setAvatarUrl(FastDFSUtil.saveImage(avatarFile.getInputStream()));
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
        userPoiDtoList.stream().forEach(p->register(new RegisterDto(p)));
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
        String excelPath = saveExcelFile(fileInputStream);
        List<String> listFilePath = Lists.newArrayList();
        listFilePath.add(excelPath);
        String subject = "大学生超级成长档案";
        String msg  = "hello";
        emailUtil.sendEmail(email,subject,msg,listFilePath);
    }

    public String saveExcelFile(InputStream fileInputStream) {
        String imgId = UUID.randomUUID().toString();
        String fileName = imgId + ".xls";
        String path = excelPosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, excelPosition);
        return path;
    }



}
