package com.xczhihui.message.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.support.domain.BasicEntity;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.message.body.MessageBody;
import com.xczhihui.message.dao.MessageDao;
import com.xczhihui.message.service.MessageService;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.dao.OnlineUserDao;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TimeUtil;

@Service
public class MessageServiceImpl extends OnlineBaseServiceImpl implements
        MessageService {

    private static final Object LOCK = new Object();
    @Autowired
    private MessageDao dao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private OnlineUserDao onlineUserDao;

    @Autowired
    private ICommonMessageService commonMessageService;

    public void setDao(MessageDao dao) {
        this.dao = dao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<Group> getAllGroup() {
        return null;
    }

    @Override
    public List<MessageVo> getOccupationTree() {
        return null;
    }

    @Override
    public Page findPageMessage(Groups groups, Page page) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        this.dao.deleteById(id);
    }

    @Override
    public void deleteBatch(String... ids) {
        List<String> list = new ArrayList<String>();
        for (String id : ids) {
            list.add(id);
        }
        dao.deleteLogics(Message.class, list);
    }

    @Override
    public Message findMessageById(String vid) {
        return null;
    }

    @Override
    public Page<MessageVo> findPageMessages(String vNameOrCreater,
                                            int pageNumber, int pageSize) throws InvocationTargetException,
            IllegalAccessException {
        Page<Message> page = this.dao.findPageMessages(vNameOrCreater,
                "createTime", pageNumber, pageSize);
        List<Message> items = page.getItems();
        List<MessageVo> itemsVo = new ArrayList<MessageVo>();
        for (Message q : items) {
            MessageVo dest = new MessageVo();
            BeanUtils.copyProperties(dest, q);
            User user = userDao.findOneEntitiyByProperty(User.class, "id",
                    q.getUserId());
            dest.setUserName(user.getName());
            itemsVo.add(dest);
        }
        Page<MessageVo> pageVo = new Page<MessageVo>(itemsVo,
                page.getTotalCount(), page.getPageSize(), page.getCurrentPage());
        return pageVo;
    }

    @Override
    public Page<MessageRecord> findPageMessages(MessageVo vo, int pageNumber,
                                                int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select * from oe_message_record where (is_delete is null or is_delete = 0)");

        if (!StringUtils.isEmpty(vo.getTime_start())) {
            sql.append(" and create_time >=:startTime");
            paramMap.put("startTime",
                    TimeUtil.parseDate(vo.getTime_start() + " 00:00:00"));
        }
        if (!StringUtils.isEmpty(vo.getTime_end())) {
            sql.append(" and create_time <=:endTime");
            paramMap.put("endTime",
                    TimeUtil.parseDate(vo.getTime_end() + " 23:59:59"));
        }
        sql.append(" order by create_time desc");
        Page<MessageRecord> ms = dao.findPageBySQL(sql.toString(), paramMap,
                MessageRecord.class, pageNumber, pageSize);
        return ms;
    }

    @Override
    public Page<MessageVo> findPageMessagesByUser(UserVo userVo,
                                                  String vNameOrCreater, int pageNumber, int pageSize) {
        Page<Message> page = this.dao.findPageMessagesByUser(userVo,
                vNameOrCreater, "createTime", pageNumber, pageSize);
        List<Message> items = page.getItems();
        List<MessageVo> itemsVo = new ArrayList<MessageVo>();
        User user = userDao.findOneEntitiyByProperty(User.class, "id",
                userVo.getId());
        for (Message q : items) {
            MessageVo dest = new MessageVo();
            try {
                BeanUtils.copyProperties(dest, q);
                dest.setUserName(user.getName());
            } catch (Exception e) {
                // logger.error(e.getMessage());
            }
            itemsVo.add(dest);
        }
        Page<MessageVo> pageVo = new Page<MessageVo>(itemsVo,
                page.getTotalCount(), page.getPageSize(), page.getCurrentPage());
        return pageVo;
    }

    @Override
    public void saveMessageRecord(MessageRecord messageRecord) {
        // TODO Auto-generated method stub
        dao.save(messageRecord);
    }

    @Override
    public void savePushMessageRecord(MessageBody messageBody) {
        String content = messageBody.getContent();
        String detailId = messageBody.getDetailId();
        String url = messageBody.getUrl();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date pushTime = null;
        try {
            pushTime = sdf.parse(messageBody.getPushTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
        int pushType = messageBody.getPushType();
        MultipartFile pushUserFile = messageBody.getPushUserFile();
        String routeType = messageBody.getRouteType();
        MessageRecord messageRecord = new MessageRecord();

        //部分用户推送
        if (pushType == 1) {
            if (pushUserFile == null) {
                throw new IllegalArgumentException("未获取到上传的文件");
            }
            messageRecord.setPushType(pushType);
            List<String> userMobiles = getUserMobiles(pushUserFile);
            messageRecord.setPushUserMobiles(
                    userMobiles.stream().collect(Collectors.joining(",")));
            messageRecord.setPushCount(userMobiles.size());
        } else if (pushType == 0) {
            messageRecord.setPushType(pushType);
            messageRecord.setPushCount(onlineUserDao.countUser());
        }

        if (routeType != null) {
            if (routeType.equals(RouteTypeEnum.H5.name())) {
                messageRecord.setUrl(url);
            } else {
                messageRecord.setDetailId(detailId);
            }
        }

        messageRecord.setContext(content);
        messageRecord.setRouteType(routeType != null ? routeType : RouteTypeEnum.NONE.name());
        messageRecord.setCreateTime(new Date());
        messageRecord.setCreatePerson(ManagerUserUtil.getId());
        messageRecord.setPushTime(pushTime);
        messageRecord.setStatus(0);
        messageRecord.setTitle(messageBody.getTitle());
        dao.save(messageRecord);
        cacheService.set(RedisCacheKey.MESSAGE_PUSH_KEY + messageRecord.getId(), messageRecord);
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void saveCronPushMessage() {
        synchronized (LOCK) {
            Set<String> keys = cacheService.getKeys(RedisCacheKey.MESSAGE_PUSH_KEY);
            for (String key : keys) {
                MessageRecord messageRecord = cacheService.get(key);
                Date pushTime = messageRecord.getPushTime();
                if (!pushTime.before(new Date())) {
                    continue;
                }
                updatePush(messageRecord);
            }
        }
    }

    private List<String> getUserMobiles(MultipartFile multipartFile) {
        XSSFWorkbook book;
        try {
            Sheet sheet = WorkbookFactory.create(multipartFile.getInputStream()).getSheetAt(0);
            int last = sheet.getLastRowNum();
            List<String> mobiles = new ArrayList<>(10);
            for (int i = 1; i < last + 1; i++) {
                Row row = sheet.getRow(i);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String mobile = row.getCell(1).getStringCellValue();
                mobiles.add(mobile);
            }
            if (mobiles.isEmpty()) {
                throw new IllegalArgumentException("excel文件内容为空");
            }
            return mobiles;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("excel文件解析失败, 请检查上传文件格式以及内容是否合法");
        } catch (InvalidFormatException e) {
            throw new IllegalArgumentException("excel文件解析失败, 请检查上传文件格式以及内容是否合法");
        }
    }

    private void updatePush(MessageRecord messageRecord) {
        cacheService.delete(RedisCacheKey.MESSAGE_PUSH_KEY + messageRecord.getId());
        messageRecord.setStatus(1);
        dao.update(messageRecord);
        if (messageRecord.getPushType() == 0) {
            int page = 1;
            int size = 100;
            while (true) {
                int offset = (page - 1) * size;
                List<OnlineUser> onlineUsers = onlineUserDao.findByPage(offset, size);
                if (!onlineUsers.isEmpty()) {
                    List<String> ids = onlineUsers.stream().map(BasicEntity::getId).collect(Collectors.toList());
                    commonMessageService.saveBatchMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                            .buildWeb(messageRecord.getContext())
                            .buildAppPush(messageRecord.getContext())
                            .link(messageRecord.getUrl())
                            .detailId(messageRecord.getDetailId())
                            .build(ids, RouteTypeEnum.valueOf(messageRecord.getRouteType()), messageRecord.getCreatePerson(), messageRecord.getTitle()));
                }
                page++;

                if (onlineUsers.size() < size) {
                    break;
                }
            }
        } else {
            String pushUserMobiles = messageRecord.getPushUserMobiles();
            String[] split = pushUserMobiles.split(",");
            List<String> list = Arrays.asList(split);
            List<OnlineUser> onlineUsers = onlineUserDao.findByUsername(list);
            if (!onlineUsers.isEmpty()) {
                List<String> ids = onlineUsers.stream().map(BasicEntity::getId).collect(Collectors.toList());
                commonMessageService.saveBatchMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                        .buildWeb(messageRecord.getContext())
                        .buildAppPush(messageRecord.getContext())
                        .link(messageRecord.getUrl())
                        .detailId(messageRecord.getDetailId())
                        .build(ids, RouteTypeEnum.valueOf(messageRecord.getRouteType()), messageRecord.getCreatePerson(), messageRecord.getTitle()));
            }
        }
    }

    @Override
    public void deleteMessageRecord(Integer id) {
        cacheService.delete(RedisCacheKey.MESSAGE_PUSH_KEY + id);
        this.dao.deleteMessageRecord(id);
    }

    @Override
    public void updateStatus(String id) {
        synchronized (LOCK) {
            MessageRecord messageRecord = this.cacheService.get(RedisCacheKey.MESSAGE_PUSH_KEY + id);
            if (messageRecord != null) {
                messageRecord.setPushTime(new Date());
                updatePush(messageRecord);
            }
        }
    }
}
