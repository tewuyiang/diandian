package com.diandian.service.impl;

import com.diandian.dao.ListsMapper;
import com.diandian.dao.RoomMapper;
import com.diandian.dao.UserMapper;
import com.diandian.dao.custom.ListsCustomMapper;
import com.diandian.dao.custom.RoomCustomMapper;
import com.diandian.dao.custom.UserCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Lists;
import com.diandian.model.Room;
import com.diandian.model.User;
import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RoomCustomMapper roomCustomMapper;
    @Autowired
    private ListsMapper listsMapper;
    @Autowired
    private ListsCustomMapper listsCustomMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;


    /**
     * 根据房间号查询房间内的所有用户
     *
     * @param roomID
     * @return
     */
    @Override
    public List<UserCustom> selectUsersInRoomByRoomId(Integer roomID) throws Exception {
        if (roomID == null) {
            throw new ParamException("信息获取异常");
        }

        // 判断房间是否存在或者被删除
        Room room = roomMapper.selectByPrimaryKey(roomID);
        if (room == null || room.getDel() == 0) {
            return null;
        }
        return listsCustomMapper.selectUsersInRoomByRoomId(roomID);
    }


    /**
     * 根据房间id查询房间信息
     *
     * @param roomId
     */
    @Override
    public Room selectRoomByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException("信息获取异常");
        }
        return roomCustomMapper.selectRoomById(roomId);
    }



    /**
     * 创建一个新的房间
     * @param room
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertRoom(Room room) throws Exception {
        if (room == null || room.getUserid() == null || room.getDistance() == null || room.getRname() == null
                || "".equals(room.getRname()) || room.getId() != null) {
            throw new ParamException();
        }

        // 判断创建房间的用户是否存在
        User user = userMapper.selectByPrimaryKey(room.getUserid());
        if (user == null) return 0;

        String roomNum;
        while (true) {
            // 随机生成房间号码(两位的随机数 + 乱序的时间戳)
            String str = new SimpleDateFormat("ssMMmmddHH").format(new Date());
            roomNum = str + new Integer((int)(10 + Math.random()*90));
            // 查询数据库中此房间号是否被占用
            RoomCustom r = roomCustomMapper.selectRoomByRoomNumber(roomNum);
            if (r == null) {
                break;
            }
        }
        room.setRoomnumber(roomNum);
        room.setPersoncount((short)0);
        room.setDel((short)1);

        return roomMapper.insertSelective(room);
    }


    /**
     * 修改房间信息
     * @param room
     * @return
     * @throws Exception
     */
    @Override
    public Room updateRoom(Room room) throws Exception {
        if (room == null || room.getId() == null) {
            throw new ParamException();
        }

        if ("".equals(room.getRname())) {
            room.setRname(null);
        }
        if ("".equals(room.getRoomnumber())) {
            room.setRoomnumber(null);
        }

        // 将更新后的房间信息返回
        if (roomMapper.updateByPrimaryKeySelective(room) > 0) {
            return roomMapper.selectByPrimaryKey(room.getId());
        }
        return null;
    }


    /**
     * 删除房间
     * @param roomId
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteRoomByRoomId(Integer roomId) throws Exception {
        if (roomId == null) {
            throw new ParamException();
        }
        return roomCustomMapper.deleteRoomByRoomId(roomId);
    }


    /**
     * 根据房间号码查询房间
     * @param roomNumber
     * @return
     * @throws Exception
     */
    @Override
    public RoomCustom selectRoomByRoomNumber(String roomNumber) throws Exception {
        if (roomNumber == null || "".equals(roomNumber)) {
            throw new ParamException();
        }
        return roomCustomMapper.selectRoomByRoomNumber(roomNumber);
    }


    /**
     * 加入考勤房间
     * @param lists
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertLists(Lists lists) throws Exception {
        if (lists == null || lists.getRoomid() == null || lists.getUserid() == null) {
            throw new ParamException();
        }

        // 查询用户信息
        User user = userMapper.selectByPrimaryKey(lists.getUserid());
        // 用户不存在
        if (user == null) return 2;

        // 查询用户想要加入的房间
        RoomCustom r = roomCustomMapper.selectRoomById(lists.getRoomid());
        // 房间不存在
        if (r == null) return 3;

        // 查询用户创建的所有房间
        List<RoomCustom> rooms1 = userCustomMapper.selectRoomsByUserId(lists.getUserid());
        // 用户无法加入自己创建的房间
        for (RoomCustom room : rooms1) {
            if (room.getId() == lists.getRoomid()) return 4;
        }

        // 查询用户已经加入的所有房间
        List<RoomCustom> rooms2 = userCustomMapper.selectJoinRoomsByUserId(lists.getUserid());
        // 用户无法加入已经加入过的房间
        for (RoomCustom room : rooms2) {
            if (room.getId() == lists.getRoomid()) return 5;
        }

        // 封装数据
        lists.setDel((short)1);
        if ("".equals(lists.getRemarkname())) {
            lists.setRemarkname(null);
        }
        Integer num;

        // 查询lists表中是否已经有这条记录（用户之前退出过此房间，现在重新加入）
        Lists l = listsCustomMapper.selectByRoomIdAndUserId(lists.getRoomid(), lists.getUserid());
        if (l == null) {
            // 数据库中无此记录，直接插入数据到数据库
            num = listsMapper.insertSelective(lists);
        } else {
            // 之前有此数据，则将之前删除的恢复（del变为1）
            l.setRemarkname(lists.getRemarkname());
            l.setDel((short)1);
            num = listsMapper.updateByPrimaryKey(l);
        }

        // 加入成功
        if (num > 0) {
            // 房间人数+1
            r.setPersoncount( (short)(r.getPersoncount()+1) );
            roomMapper.updateByPrimaryKeySelective(r);
        }
        return num;
    }


    /**
     * 用户退出考勤房间
     * @param roomId 房间号
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteUserToRoom(Integer roomId, Integer userId) throws Exception {
        if (roomId == null || userId == null) {
            throw new ParamException();
        }

        // 查询用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        // 用户不存在
        if (user == null) return 2;

        // 查询用户想要退出的房间
        RoomCustom r = roomCustomMapper.selectRoomById(roomId);
        // 房间不存在
        if (r == null) return 3;

        Integer num = listsCustomMapper.deleteLists(roomId, userId);
        // 退出成功
        if (num > 0) {
            // 更新房间人数

        }
        return num;
    }


}
